package co.id.pdamkotasmg.local.sync;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.pdamkotasmg.goodday.utils.Config;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import co.id.pdamkotasmg.api.ApiConfig;
import co.id.pdamkotasmg.api.ApiService;
import co.id.pdamkotasmg.local.LocalPhotoStorage;
import co.id.pdamkotasmg.local.NetworkUtil;
import co.id.pdamkotasmg.local.SettingsManager;
import co.id.pdamkotasmg.local.db.AppDatabase;
import co.id.pdamkotasmg.local.db.entity.PendingBacaanEntity;
import co.id.pdamkotasmg.local.db.entity.PendingFotoEntity;
import co.id.pdamkotasmg.model.fileHandler.PostFotoUploadRootModel;
import co.id.pdamkotasmg.model.updatePembacaMeter.UpdatePembacaMeterRootModel;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Orkestrator sync — kirim semua pending bacaan ke server.
 *
 * Strategi: SEQUENTIAL (1 by 1) dengan SKIP-AND-CONTINUE saat gagal.
 *
 * Behavior:
 *   - Item gagal → mark FAILED + lastError, lanjut ke item berikutnya
 *   - Item sukses → mark SUCCESS + delete foto local + emit ITEM_SUCCESS
 *   - Foto upload sukses → update PendingFotoEntity (serverPath, serverUrl)
 *   - Setelah ALL_DONE → emit summary { successCount, failedCount }
 *
 * Singleton (instance dipakai bersama supaya prevent double-trigger):
 *   SyncManager.getInstance(context).startSyncAll(callback)
 *
 * Threading:
 *   - startSyncAll() dipanggil dari main thread
 *   - Sync berjalan di syncExecutor (1 thread, sequential)
 *   - Callback `onProgress()` selalu dipanggil di main thread
 */
public class SyncManager {

    private static final String TAG = "SyncManager";

    public interface ProgressCallback {
        void onProgress(SyncProgressEvent event);
    }

    private static volatile SyncManager INSTANCE;

    private final Context appContext;
    private final AppDatabase db;
    private final LocalPhotoStorage photoStorage;
    private final SettingsManager settings;
    private final Handler mainHandler;
    private final ExecutorService syncExecutor;

    private final AtomicBoolean isSyncing = new AtomicBoolean(false);
    private final AtomicBoolean cancelRequested = new AtomicBoolean(false);

    private SyncManager(Context appContext) {
        this.appContext = appContext.getApplicationContext();
        this.db = AppDatabase.getInstance(this.appContext);
        this.photoStorage = new LocalPhotoStorage(this.appContext);
        this.settings = SettingsManager.getInstance(this.appContext);
        this.mainHandler = new Handler(Looper.getMainLooper());
        // Single thread executor → sequential by design
        this.syncExecutor = Executors.newSingleThreadExecutor();
    }

    public static SyncManager getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SyncManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SyncManager(context.getApplicationContext());
                }
            }
        }
        return INSTANCE;
    }

    public boolean isSyncing() {
        return isSyncing.get();
    }

    public void requestCancel() {
        cancelRequested.set(true);
    }

    /**
     * Sync semua pending bacaan (syncStatus != SUCCESS).
     *
     * @param token    access token dari SP
     * @param callback dipanggil di main thread untuk update UI
     */
    public void startSyncAll(String token, ProgressCallback callback) {
        if (!isSyncing.compareAndSet(false, true)) {
            Log.w(TAG, "Sync already in progress, ignoring duplicate trigger");
            return;
        }
        cancelRequested.set(false);

        syncExecutor.execute(() -> {
            try {
                if (!NetworkUtil.isOnline(appContext)) {
                    deliverProgress(callback, SyncProgressEvent.itemFailed(
                            0, 0, null, 0, 0,
                            "Tidak ada koneksi internet"));
                    deliverProgress(callback, SyncProgressEvent.allDone(0, 0, 0));
                    return;
                }

                List<PendingBacaanEntity> pendingList = db.pendingBacaanDao().getAllUnsynced();
                int total = pendingList.size();

                if (total == 0) {
                    deliverProgress(callback, SyncProgressEvent.allDone(0, 0, 0));
                    return;
                }

                deliverProgress(callback, SyncProgressEvent.started(total));

                int successCount = 0;
                int failedCount = 0;

                for (int i = 0; i < pendingList.size(); i++) {
                    if (cancelRequested.get()) {
                        Log.i(TAG, "Sync cancelled by user");
                        break;
                    }

                    PendingBacaanEntity pending = pendingList.get(i);
                    int current = i + 1;

                    boolean success = syncOne(token, pending, current, total, successCount, failedCount, callback);

                    if (success) {
                        successCount++;
                        deliverProgress(callback, SyncProgressEvent.itemSuccess(
                                current, total, pending.nolangg, successCount, failedCount));
                    } else {
                        failedCount++;
                        // itemFailed event sudah di-emit di syncOne dengan error message
                    }
                }

                deliverProgress(callback, SyncProgressEvent.allDone(total, successCount, failedCount));

                if (successCount > 0) {
                    settings.markSyncSuccessNow();
                    // Fase 6: Cleanup bendel-bendel yang sudah kosong setelah sync sukses
                    co.id.pdamkotasmg.local.repository.BendelRepository
                            .cleanupEmptyBendelsStatic(appContext);
                }
            } catch (Exception e) {
                Log.e(TAG, "Sync fatal error", e);
                deliverProgress(callback, SyncProgressEvent.itemFailed(
                        0, 0, null, 0, 0,
                        "Sync error: " + e.getMessage()));
                deliverProgress(callback, SyncProgressEvent.allDone(0, 0, 0));
            } finally {
                isSyncing.set(false);
                cancelRequested.set(false);
            }
        });
    }

    /**
     * Retry 1 pending item — dipakai dari per-row "Retry" button.
     */
    public void startSyncOne(String token, long pendingId, ProgressCallback callback) {
        if (!isSyncing.compareAndSet(false, true)) {
            Log.w(TAG, "Sync already in progress");
            return;
        }
        cancelRequested.set(false);

        syncExecutor.execute(() -> {
            try {
                if (!NetworkUtil.isOnline(appContext)) {
                    deliverProgress(callback, SyncProgressEvent.itemFailed(
                            1, 1, null, 0, 1,
                            "Tidak ada koneksi internet"));
                    deliverProgress(callback, SyncProgressEvent.allDone(1, 0, 1));
                    return;
                }

                PendingBacaanEntity pending = db.pendingBacaanDao().getById(pendingId);
                if (pending == null) {
                    deliverProgress(callback, SyncProgressEvent.allDone(0, 0, 0));
                    return;
                }

                deliverProgress(callback, SyncProgressEvent.started(1));

                boolean success = syncOne(token, pending, 1, 1, 0, 0, callback);

                if (success) {
                    deliverProgress(callback, SyncProgressEvent.itemSuccess(
                            1, 1, pending.nolangg, 1, 0));
                    deliverProgress(callback, SyncProgressEvent.allDone(1, 1, 0));
                    settings.markSyncSuccessNow();
                    // Fase 6: Cleanup bendel-bendel yang sudah kosong setelah sync sukses
                    co.id.pdamkotasmg.local.repository.BendelRepository
                            .cleanupEmptyBendelsStatic(appContext);
                } else {
                    deliverProgress(callback, SyncProgressEvent.allDone(1, 0, 1));
                }
            } catch (Exception e) {
                Log.e(TAG, "syncOne error", e);
                deliverProgress(callback, SyncProgressEvent.allDone(0, 0, 0));
            } finally {
                isSyncing.set(false);
                cancelRequested.set(false);
            }
        });
    }

    // ============== CORE: SYNC 1 ITEM ==============

    /**
     * Sync 1 pending bacaan: upload foto-fotonya → POST data bacaan → mark status.
     *
     * @return true kalau sukses, false kalau gagal (error sudah di-emit ke callback)
     */
    private boolean syncOne(String token, PendingBacaanEntity pending,
                            int current, int total, int successCount, int failedCount,
                            ProgressCallback callback) {

        // Mark IN_FLIGHT supaya kalau app crash di tengah, bisa recover.
        db.pendingBacaanDao().updateSyncStatus(pending.id, PendingBacaanEntity.STATUS_IN_FLIGHT);

        try {
            // ============== STEP 1: Upload foto-foto ==============
            List<PendingFotoEntity> fotos = db.pendingFotoDao().getByPendingBacaan(pending.id);

            String fotoMeterServerPath = null;
            String fotoManometerServerPath = null;

            for (PendingFotoEntity foto : fotos) {
                deliverProgress(callback, SyncProgressEvent.uploadingPhoto(
                        current, total, pending.nolangg, successCount, failedCount));

                String serverPath = uploadOneFoto(token, foto, pending);
                if (serverPath == null) {
                    String err = "Upload foto gagal: " + foto.lastError;
                    db.pendingBacaanDao().markFailed(pending.id,
                            PendingBacaanEntity.STATUS_FAILED, err);
                    deliverProgress(callback, SyncProgressEvent.itemFailed(
                            current, total, pending.nolangg, successCount, failedCount + 1, err));
                    return false;
                }

                if (PendingFotoEntity.JENIS_FOTO_METER.equals(foto.jenis)) {
                    fotoMeterServerPath = serverPath;
                } else if (PendingFotoEntity.JENIS_FOTO_MANOMETER.equals(foto.jenis)) {
                    fotoManometerServerPath = serverPath;
                }
            }

            // ============== STEP 2: POST update-pembaca-meter ==============
            deliverProgress(callback, SyncProgressEvent.postingData(
                    current, total, pending.nolangg, successCount, failedCount));

            String menuName = mapJenisToMenuName(pending.jenis);
            String filePath = fotoMeterServerPath != null ? fotoMeterServerPath : "";
            String filePathManometer = fotoManometerServerPath != null ? fotoManometerServerPath : "";

            ApiService api = ApiConfig.getApiServiceGWAPI(appContext);
            Call<UpdatePembacaMeterRootModel> call = api.postUpdatePembacaMeter(
                    token,
                    nullSafe(pending.nolangg),
                    nullSafe(pending.kini),
                    filePath,
                    menuName,
                    nullSafe(pending.kodeStatusMeter),
                    nullSafe(pending.keterangan),
                    nullSafe(pending.actionCode),
                    pending.latitude == null ? "0" : String.valueOf(pending.latitude),
                    pending.longitude == null ? "0" : String.valueOf(pending.longitude),
                    nullSafe(pending.addressGps),
                    nullSafe(pending.manometer),
                    filePathManometer,
                    nullSafe(pending.versionInfo)
            );

            Response<UpdatePembacaMeterRootModel> response = call.execute();

            if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                // ============== STEP 3: Mark SUCCESS + cleanup foto ==============
                db.pendingBacaanDao().markSuccess(pending.id, System.currentTimeMillis());

                // Hapus file foto local — sudah berhasil di-upload, tidak perlu disimpan lagi
                for (PendingFotoEntity foto : fotos) {
                    photoStorage.deletePending(foto.localFilePath);
                }

                Log.d(TAG, "Sync sukses untuk pending #" + pending.id + " (nolangg=" + pending.nolangg + ")");
                return true;
            } else {
                String errMsg = "POST gagal: HTTP " + response.code();
                if (response.errorBody() != null) {
                    try {
                        errMsg += " - " + response.errorBody().string();
                    } catch (Exception ignored) {}
                }
                db.pendingBacaanDao().markFailed(pending.id,
                        PendingBacaanEntity.STATUS_FAILED, errMsg);
                deliverProgress(callback, SyncProgressEvent.itemFailed(
                        current, total, pending.nolangg, successCount, failedCount + 1, errMsg));
                return false;
            }

        } catch (Exception e) {
            String errMsg = "Sync error: " + e.getMessage();
            Log.e(TAG, "syncOne exception for pending #" + pending.id, e);
            db.pendingBacaanDao().markFailed(pending.id,
                    PendingBacaanEntity.STATUS_FAILED, errMsg);
            deliverProgress(callback, SyncProgressEvent.itemFailed(
                    current, total, pending.nolangg, successCount, failedCount + 1, errMsg));
            return false;
        }
    }

    /**
     * Upload 1 foto ke server.
     *
     * @return serverPath kalau sukses, null kalau gagal
     */
    private String uploadOneFoto(String token, PendingFotoEntity foto, PendingBacaanEntity pending) {
        File file = new File(foto.localFilePath);
        if (!file.exists()) {
            db.pendingFotoDao().markFailed(foto.id,
                    PendingFotoEntity.STATUS_FAILED,
                    "File local tidak ditemukan: " + foto.localFilePath);
            return null;
        }

        try {
            db.pendingFotoDao().markFailed(foto.id, PendingFotoEntity.STATUS_IN_FLIGHT, null);

            String year = new SimpleDateFormat("y", Locale.getDefault()).format(new Date());
            String month = new SimpleDateFormat("MM", Locale.getDefault()).format(new Date());
            String timestamp = String.valueOf(Calendar.getInstance().getTime().getTime());
            String randomUUID = UUID.randomUUID().toString();

            String prefix = PendingFotoEntity.JENIS_FOTO_MANOMETER.equals(foto.jenis)
                    ? "pembaca-meter-manometer-"
                    : "pembaca-meter-";

            RequestBody pathBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    "/pembaca-meter/foto-pembaca-meter/" + year + "/" + month);
            RequestBody fileNameBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    prefix + randomUUID + "-" + nullSafe(pending.nolangg)
                            + "-" + nullSafe(pending.npp)
                            + "-" + year + month + "-" + timestamp);

            RequestBody fileRequest = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("photo", file.getName(), fileRequest);

            ApiService api = ApiConfig.getApiServiceGWAPI(appContext);
            Response<PostFotoUploadRootModel> response = api.postUploadFoto(token, pathBody, fileNameBody, filePart).execute();

            if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                String serverPath = response.body().getData().getFilepath();
                String serverUrl = response.body().getData().getFileurl();
                db.pendingFotoDao().markUploaded(foto.id,
                        PendingFotoEntity.STATUS_SUCCESS, serverPath, serverUrl);
                return serverPath;
            } else {
                String err = "Upload HTTP " + response.code();
                db.pendingFotoDao().markFailed(foto.id, PendingFotoEntity.STATUS_FAILED, err);
                return null;
            }
        } catch (Exception e) {
            String err = e.getMessage();
            db.pendingFotoDao().markFailed(foto.id, PendingFotoEntity.STATUS_FAILED, err);
            return null;
        }
    }

    // ============== HELPERS ==============

    private static String mapJenisToMenuName(String jenis) {
        if (jenis == null) return "khusus-bendel";
        switch (jenis) {
            case PendingBacaanEntity.JENIS_KHUSUS_BENDEL:
                return "khusus-bendel";
            case PendingBacaanEntity.JENIS_PER_PELANGGAN:
                return "per-pelanggan";
            case PendingBacaanEntity.JENIS_PER_FOTO_METER:
                return "per-foto-meter";
            case PendingBacaanEntity.JENIS_BACA_ULANG:
                return "baca-ulang";
            default:
                return "khusus-bendel";
        }
    }

    private static String nullSafe(String s) {
        return s == null ? "" : s;
    }

    private void deliverProgress(ProgressCallback callback, SyncProgressEvent event) {
        if (callback == null) return;
        mainHandler.post(() -> callback.onProgress(event));
    }
}