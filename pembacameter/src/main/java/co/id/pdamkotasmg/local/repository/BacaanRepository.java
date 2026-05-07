package co.id.pdamkotasmg.local.repository;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.File;

import co.id.pdamkotasmg.local.LocalPhotoStorage;
import co.id.pdamkotasmg.local.db.AppDatabase;
import co.id.pdamkotasmg.local.db.entity.CachedBendelEntity;
import co.id.pdamkotasmg.local.db.entity.PendingBacaanEntity;
import co.id.pdamkotasmg.local.db.entity.PendingFotoEntity;

/**
 * Repository untuk handle bacaan — simpan offline + update cache.
 *
 * Dipakai oleh Activity input (BendelPembacaKhusus, dll) saat user tekan Simpan
 * dengan Mode Offline ON.
 *
 * Flow:
 *   1. Activity panggil saveOffline(...) dengan data bacaan + path foto
 *   2. Repository:
 *      - Copy foto dari sourceFile ke filesDir/pending_photos/
 *      - Insert PendingBacaanEntity → dapat generated ID
 *      - Insert PendingFotoEntity (linked dengan ID di atas)
 *      - Update cache: pelanggan tsb mark kodeStatus=1
 *        → akan hilang dari list bendel saat user balik
 *   3. Activity dapat callback sukses/gagal → tampilkan toast & navigate
 *
 * UPDATE CACHE setelah ONLINE save sukses:
 *   Activity yang pakai online flow (langsung kirim ke API) WAJIB juga panggil
 *   {@link #markPelangganAsRead(String, String, MarkCallback)} setelah API sukses,
 *   supaya cache konsisten dengan server. Tanpa ini, cache akan stale dan
 *   pelanggan tetap muncul di list "belum dibaca" saat user offline.
 */
public class BacaanRepository {

    private static final String TAG = "BacaanRepository";

    private final Context appContext;
    private final AppDatabase db;
    private final LocalPhotoStorage photoStorage;
    private final Handler mainHandler;

    public BacaanRepository(Context context) {
        this.appContext = context.getApplicationContext();
        this.db = AppDatabase.getInstance(appContext);
        this.photoStorage = new LocalPhotoStorage(appContext);
        this.mainHandler = new Handler(Looper.getMainLooper());
    }

    public interface SaveCallback {
        void onSaved(long pendingBacaanId);
        void onError(String message, Throwable t);
    }

    public interface MarkCallback {
        void onDone(boolean success);
    }

    /**
     * Simpan bacaan ke local — untuk dipakai saat Mode Offline ON.
     *
     * @param entity        pending bacaan dengan field-field yang sudah di-fill
     *                      kecuali id, syncStatus, retryCount, syncedAt
     * @param fotoMeterFile foto meter yang sudah di-compress (boleh null kalau tidak ada)
     * @param fotoManometerFile foto manometer (boleh null)
     * @param bendelId      composite ID bendel — untuk update cache markAsRead.
     *                      null kalau bukan dari flow bendel.
     * @param callback      dipanggil di main thread
     */
    public void saveOffline(
            PendingBacaanEntity entity,
            File fotoMeterFile,
            File fotoManometerFile,
            String bendelId,
            SaveCallback callback) {

        AppDatabase.databaseExecutor.execute(() -> {
            try {
                // Set field default
                entity.createdAt = System.currentTimeMillis();
                entity.syncStatus = PendingBacaanEntity.STATUS_PENDING;
                entity.retryCount = 0;

                // 1. Copy foto-foto ke private storage DULU sebelum insert DB.
                //    Kalau copy gagal, kita tidak insert apa-apa (atomic).
                File savedFotoMeter = null;
                File savedFotoManometer = null;

                if (fotoMeterFile != null) {
                    savedFotoMeter = photoStorage.copyToPendingStorage(
                            fotoMeterFile, entity.nolangg, PendingFotoEntity.JENIS_FOTO_METER);
                    if (savedFotoMeter == null) {
                        deliverError(callback, "Gagal menyimpan foto meter ke storage", null);
                        return;
                    }
                }

                if (fotoManometerFile != null) {
                    savedFotoManometer = photoStorage.copyToPendingStorage(
                            fotoManometerFile, entity.nolangg, PendingFotoEntity.JENIS_FOTO_MANOMETER);
                    if (savedFotoManometer == null) {
                        // Cleanup foto meter yang sudah ke-save
                        if (savedFotoMeter != null) {
                            photoStorage.deletePending(savedFotoMeter.getAbsolutePath());
                        }
                        deliverError(callback, "Gagal menyimpan foto manometer ke storage", null);
                        return;
                    }
                }

                // 2. Insert ke DB dalam 1 transaction
                final File finalFotoMeter = savedFotoMeter;
                final File finalFotoManometer = savedFotoManometer;
                final long[] pendingBacaanId = {0};

                db.runInTransaction(() -> {
                    pendingBacaanId[0] = db.pendingBacaanDao().insert(entity);

                    if (finalFotoMeter != null) {
                        PendingFotoEntity foto = new PendingFotoEntity();
                        foto.pendingBacaanId = pendingBacaanId[0];
                        foto.jenis = PendingFotoEntity.JENIS_FOTO_METER;
                        foto.localFilePath = finalFotoMeter.getAbsolutePath();
                        foto.createdAt = System.currentTimeMillis();
                        foto.uploadStatus = PendingFotoEntity.STATUS_PENDING;
                        db.pendingFotoDao().insert(foto);
                    }

                    if (finalFotoManometer != null) {
                        PendingFotoEntity foto = new PendingFotoEntity();
                        foto.pendingBacaanId = pendingBacaanId[0];
                        foto.jenis = PendingFotoEntity.JENIS_FOTO_MANOMETER;
                        foto.localFilePath = finalFotoManometer.getAbsolutePath();
                        foto.createdAt = System.currentTimeMillis();
                        foto.uploadStatus = PendingFotoEntity.STATUS_PENDING;
                        db.pendingFotoDao().insert(foto);
                    }

                    // 3. Update cache: pelanggan mark sudah dibaca (kodeStatus=1)
                    //    Ini bikin pelanggan hilang dari list "belum dibaca".
                    if (bendelId != null && entity.nolangg != null) {
                        db.cachedPelangganBendelDao().markAsRead(bendelId, entity.nolangg);
                    }
                });

                Log.d(TAG, "Offline save sukses, pendingBacaanId=" + pendingBacaanId[0]);
                final long savedId = pendingBacaanId[0];
                mainHandler.post(() -> callback.onSaved(savedId));

            } catch (Exception e) {
                Log.e(TAG, "saveOffline error", e);
                deliverError(callback, "Gagal menyimpan offline: " + e.getMessage(), e);
            }
        });
    }

    /**
     * Tandai pelanggan sebagai sudah-dibaca di cache.
     *
     * WAJIB dipanggil setelah online save sukses (oleh Activity yang pakai
     * flow online), supaya cache konsisten dan list bendel update.
     *
     * @param codeBendel kode bendel
     * @param nolangg    ID pelanggan
     * @param callback   optional, dipanggil di main thread setelah selesai
     */
    public void markPelangganAsRead(String codeBendel, String nolangg, MarkCallback callback) {
        AppDatabase.databaseExecutor.execute(() -> {
            try {
                String periode = appContext.getSharedPreferences(
                                com.pdamkotasmg.goodday.utils.Config.SHARED_PREF_NAME,
                                Context.MODE_PRIVATE)
                        .getString(com.pdamkotasmg.goodday.utils.Config.SHARED_PERIODE, "");
                String cabang = appContext.getSharedPreferences(
                                com.pdamkotasmg.goodday.utils.Config.SHARED_PREF_NAME,
                                Context.MODE_PRIVATE)
                        .getString(com.pdamkotasmg.goodday.utils.Config.SHARED_CABANG, "");

                String bendelId = CachedBendelEntity.buildId(codeBendel, periode, cabang);
                db.cachedPelangganBendelDao().markAsRead(bendelId, nolangg);

                Log.d(TAG, "Marked pelanggan " + nolangg + " sebagai sudah-dibaca di cache");
                if (callback != null) {
                    mainHandler.post(() -> callback.onDone(true));
                }
            } catch (Exception e) {
                Log.e(TAG, "markPelangganAsRead error", e);
                if (callback != null) {
                    mainHandler.post(() -> callback.onDone(false));
                }
            }
        });
    }

    /**
     * Cek apakah pelanggan ini sudah punya pending bacaan offline.
     * Untuk Activity input — bisa disable tombol Simpan kalau sudah ada pending.
     */
    public void hasPendingForNolangg(String nolangg, java.util.function.Consumer<Boolean> callback) {
        AppDatabase.databaseExecutor.execute(() -> {
            boolean has = !db.pendingBacaanDao().getByNolangg(nolangg).isEmpty();
            mainHandler.post(() -> callback.accept(has));
        });
    }

    private void deliverError(SaveCallback callback, String message, Throwable t) {
        mainHandler.post(() -> callback.onError(message, t));
    }
}