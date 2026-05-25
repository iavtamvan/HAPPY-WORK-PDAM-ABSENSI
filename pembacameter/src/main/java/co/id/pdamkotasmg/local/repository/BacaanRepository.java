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
 * Fase 6 enhancement (untuk fix bug "data masih tampil setelah disimpan"):
 *   - markPelangganAsRead return jumlah row affected (via DAO)
 *   - Logging detail untuk debug
 *   - Fallback: kalau markAsRead dengan bendelId tidak hit, coba markAsReadByNolangg
 *   - Decrement totalUnread di header cached_bendel
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
     * Simpan bacaan offline + update cache markAsRead.
     */
    public void saveOffline(
            PendingBacaanEntity entity,
            File fotoMeterFile,
            File fotoManometerFile,
            String bendelId,
            SaveCallback callback) {

        AppDatabase.databaseExecutor.execute(() -> {
            try {
                entity.createdAt = System.currentTimeMillis();
                entity.syncStatus = PendingBacaanEntity.STATUS_PENDING;
                entity.retryCount = 0;

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
                        if (savedFotoMeter != null) {
                            photoStorage.deletePending(savedFotoMeter.getAbsolutePath());
                        }
                        deliverError(callback, "Gagal menyimpan foto manometer ke storage", null);
                        return;
                    }
                }

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

                    if (bendelId != null && entity.nolangg != null) {
                        int rowsAffected = db.cachedPelangganBendelDao().markAsRead(bendelId, entity.nolangg);
                        Log.d(TAG, "saveOffline markAsRead bendelId=" + bendelId
                                + " nolangg=" + entity.nolangg
                                + " rowsAffected=" + rowsAffected);

                        if (rowsAffected == 0) {
                            // Fallback: coba mark by nolangg saja
                            int fallback = db.cachedPelangganBendelDao().markAsReadByNolangg(entity.nolangg);
                            Log.d(TAG, "Fallback markAsReadByNolangg rowsAffected=" + fallback);
                        } else {
                            // Decrement counter di header bendel
                            db.cachedBendelDao().decrementUnreadCount(bendelId);
                        }
                    } else {
                        // Fallback: bendelId null, tetap coba mark by nolangg
                        if (entity.nolangg != null) {
                            int fallback = db.cachedPelangganBendelDao().markAsReadByNolangg(entity.nolangg);
                            Log.d(TAG, "saveOffline bendelId null, fallback markAsReadByNolangg rowsAffected=" + fallback);
                        }
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
     * WAJIB dipanggil setelah online save sukses.
     *
     * Behavior:
     *   1. Build bendelId dari codeBendel + periode + cabang
     *   2. markAsRead(bendelId, nolangg) — kalau hit (rowsAffected > 0), done
     *   3. Kalau miss (rowsAffected = 0), fallback: markAsReadByNolangg (mark di SEMUA bendel)
     *   4. Decrement totalUnread di header bendel kalau hit
     *
     * @param codeBendel kode bendel (boleh null/empty — akan langsung pakai fallback)
     * @param nolangg    ID pelanggan
     * @param callback   optional, dipanggil di main thread
     */
    public void markPelangganAsRead(String codeBendel, String nolangg, MarkCallback callback) {
        AppDatabase.databaseExecutor.execute(() -> {
            try {
                if (nolangg == null || nolangg.isEmpty()) {
                    Log.w(TAG, "markPelangganAsRead: nolangg empty, skip");
                    if (callback != null) mainHandler.post(() -> callback.onDone(false));
                    return;
                }

                String periode = appContext.getSharedPreferences(
                                com.pdamkotasmg.goodday.utils.Config.SHARED_PREF_NAME,
                                Context.MODE_PRIVATE)
                        .getString(com.pdamkotasmg.goodday.utils.Config.SHARED_PERIODE, "");
                String cabang = appContext.getSharedPreferences(
                                com.pdamkotasmg.goodday.utils.Config.SHARED_PREF_NAME,
                                Context.MODE_PRIVATE)
                        .getString(com.pdamkotasmg.goodday.utils.Config.SHARED_CABANG, "");

                int totalAffected = 0;

                // Step 1: kalau ada codeBendel, coba mark spesifik
                if (codeBendel != null && !codeBendel.isEmpty()) {
                    String bendelId = CachedBendelEntity.buildId(codeBendel, periode, cabang);
                    int rowsAffected = db.cachedPelangganBendelDao().markAsRead(bendelId, nolangg);
                    Log.d(TAG, "markPelangganAsRead spesifik bendelId=" + bendelId
                            + " nolangg=" + nolangg
                            + " rowsAffected=" + rowsAffected);

                    if (rowsAffected > 0) {
                        // Decrement counter di header
                        db.cachedBendelDao().decrementUnreadCount(bendelId);
                        totalAffected = rowsAffected;
                    }
                }

                // Step 2: kalau spesifik tidak hit, fallback by nolangg (mark di SEMUA bendel)
                if (totalAffected == 0) {
                    int fallback = db.cachedPelangganBendelDao().markAsReadByNolangg(nolangg);
                    Log.d(TAG, "markPelangganAsRead fallback by nolangg=" + nolangg
                            + " rowsAffected=" + fallback);
                    totalAffected = fallback;
                }

                final boolean success = totalAffected > 0;
                Log.d(TAG, "markPelangganAsRead DONE: totalAffected=" + totalAffected);

                if (callback != null) {
                    mainHandler.post(() -> callback.onDone(success));
                }
            } catch (Exception e) {
                Log.e(TAG, "markPelangganAsRead error", e);
                if (callback != null) {
                    mainHandler.post(() -> callback.onDone(false));
                }
            }
        });
    }

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