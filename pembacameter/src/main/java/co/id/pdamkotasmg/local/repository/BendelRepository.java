package co.id.pdamkotasmg.local.repository;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.pdamkotasmg.goodday.utils.Config;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import co.id.pdamkotasmg.api.ApiConfig;
import co.id.pdamkotasmg.api.ApiService;
import co.id.pdamkotasmg.local.NetworkUtil;
import co.id.pdamkotasmg.local.SettingsManager;
import co.id.pdamkotasmg.local.db.AppDatabase;
import co.id.pdamkotasmg.local.db.entity.CachedBendelEntity;
import co.id.pdamkotasmg.local.db.entity.CachedPelangganBendelEntity;
import co.id.pdamkotasmg.model.bendel.BendelRootModel;
import co.id.pdamkotasmg.model.bendel.DataItem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Repository untuk data bendel.
 *
 * STRATEGI v3 (Fase 6) — CACHE-FIRST DENGAN MANUAL SYNC:
 *
 *   getBendel(forceRefresh=false):
 *     - Cache ada → tampilkan dari cache (no API hit)
 *     - Cache kosong → auto fetch API
 *     - No koneksi & cache kosong → error
 *
 *   getBendel(forceRefresh=true):
 *     - SELALU hit API (dipakai FAB Sync atau pull-to-refresh)
 *     - Update cache + cleanup bendel kosong (kalau auto-cleanup ON)
 *     - No koneksi → error
 */
public class BendelRepository {

    private static final String TAG = "BendelRepository";

    private final Context appContext;
    private final AppDatabase db;
    private final SettingsManager settings;
    private final Handler mainHandler;

    public BendelRepository(Context context) {
        this.appContext = context.getApplicationContext();
        this.db = AppDatabase.getInstance(appContext);
        this.settings = SettingsManager.getInstance(appContext);
        this.mainHandler = new Handler(Looper.getMainLooper());
    }

    public interface ResultCallback {
        void onResult(DataResult result);
    }

    public interface Cancellable {
        void cancel();
        boolean isCancelled();
    }

    /**
     * @param forceRefresh TRUE = paksa hit API (FAB Sync / pull-to-refresh),
     *                     FALSE = cache-first
     */
    public Cancellable getBendel(String token, String codeBendel, String nolanggFilter,
                                 boolean forceRefresh, ResultCallback callback) {
        String periode = appContext.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE)
                .getString(Config.SHARED_PERIODE, "");
        String cabang = appContext.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE)
                .getString(Config.SHARED_CABANG, "");

        String bendelId = CachedBendelEntity.buildId(codeBendel, periode, cabang);

        AtomicBoolean cancelled = new AtomicBoolean(false);
        Call<BendelRootModel>[] currentCall = new Call[1];

        if (forceRefresh) {
            // ==== MODE 1: Force refresh (FAB Sync) ====
            if (!NetworkUtil.isOnline(appContext)) {
                deliverOnMain(callback, DataResult.error("Tidak ada koneksi internet", null));
            } else {
                Log.d(TAG, "Force refresh → hit API for " + bendelId);
                Call<BendelRootModel> call = fetchFromNetwork(token, codeBendel, nolanggFilter,
                        bendelId, periode, cabang, cancelled, callback);
                currentCall[0] = call;
            }
        } else {
            // ==== MODE 2: Cache-first ====
            AppDatabase.databaseExecutor.execute(() -> {
                if (cancelled.get()) return;

                boolean hasCachedHeader = db.cachedBendelDao().exists(bendelId);

                if (hasCachedHeader) {
                    Log.d(TAG, "Cache HIT for " + bendelId + " → load from cache");
                    loadFromCacheSync(bendelId, nolanggFilter, callback);
                } else {
                    if (!NetworkUtil.isOnline(appContext)) {
                        Log.w(TAG, "Cache empty + no connection");
                        deliverOnMain(callback, DataResult.error(
                                "Tidak ada koneksi & belum ada data tersimpan untuk bendel ini.\n" +
                                        "Coba lagi saat ada internet.",
                                null));
                        return;
                    }
                    Log.d(TAG, "Cache empty → auto fetch from API for " + bendelId);
                    mainHandler.post(() -> {
                        if (cancelled.get()) return;
                        Call<BendelRootModel> call = fetchFromNetwork(token, codeBendel, nolanggFilter,
                                bendelId, periode, cabang, cancelled, callback);
                        currentCall[0] = call;
                    });
                }
            });
        }

        return new Cancellable() {
            @Override
            public void cancel() {
                cancelled.set(true);
                if (currentCall[0] != null && !currentCall[0].isCanceled()) {
                    currentCall[0].cancel();
                }
            }

            @Override
            public boolean isCancelled() {
                return cancelled.get();
            }
        };
    }

    /**
     * Backward compatibility — default cache-first.
     */
    public Cancellable getBendel(String token, String codeBendel, String nolanggFilter,
                                 ResultCallback callback) {
        return getBendel(token, codeBendel, nolanggFilter, false, callback);
    }

    // ============== NETWORK PATH ==============

    private Call<BendelRootModel> fetchFromNetwork(String token, String codeBendel, String nolanggFilter,
                                                   String bendelId, String periode, String cabang,
                                                   AtomicBoolean cancelled, ResultCallback callback) {
        ApiService api = ApiConfig.getApiServiceGWAPI(appContext);
        Call<BendelRootModel> call = api.getBendel(token, codeBendel, nolanggFilter == null ? "" : nolanggFilter);

        call.enqueue(new Callback<BendelRootModel>() {
            @Override
            public void onResponse(Call<BendelRootModel> c, Response<BendelRootModel> response) {
                if (cancelled.get()) return;

                if (response.isSuccessful() && response.body() != null) {
                    final List<DataItem> items = response.body().getData();

                    if (items == null || items.isEmpty()) {
                        saveToCache(items, bendelId, codeBendel, periode, cabang);
                        DataResult result = DataResult.fromNetwork(items);
                        result.lastSyncAt = System.currentTimeMillis();
                        deliverOnMain(callback, result);
                        return;
                    }

                    saveToCache(items, bendelId, codeBendel, periode, cabang);
                    DataResult result = DataResult.fromNetwork(items);
                    result.lastSyncAt = System.currentTimeMillis();
                    deliverOnMain(callback, result);

                } else {
                    Log.w(TAG, "API non-success " + response.code() + " → fallback to cache");
                    loadFromCacheAsync(bendelId, nolanggFilter, cancelled, callback);
                }
            }

            @Override
            public void onFailure(Call<BendelRootModel> c, Throwable t) {
                if (cancelled.get()) return;
                if (c.isCanceled()) return;

                Log.w(TAG, "API onFailure → fallback to cache", t);
                loadFromCacheAsync(bendelId, nolanggFilter, cancelled, callback);
            }
        });

        return call;
    }

    // ============== CACHE PATH ==============

    private void loadFromCacheSync(String bendelId, String nolanggFilter, ResultCallback callback) {
        try {
            CachedBendelEntity header = db.cachedBendelDao().getById(bendelId);
            long lastSyncAt = header != null ? header.lastSyncAt : 0;

            List<CachedPelangganBendelEntity> entities;
            if (nolanggFilter == null || nolanggFilter.trim().isEmpty()) {
                entities = db.cachedPelangganBendelDao().getUnreadByBendel(bendelId);
            } else {
                CachedPelangganBendelEntity hit = db.cachedPelangganBendelDao()
                        .findInBendel(bendelId, nolanggFilter.trim());
                entities = hit == null
                        ? new java.util.ArrayList<>()
                        : java.util.Collections.singletonList(hit);
            }

            List<DataItem> data = BendelMapper.fromEntityList(entities);
            DataResult result = DataResult.fromCache(data);
            result.lastSyncAt = lastSyncAt;
            deliverOnMain(callback, result);

        } catch (Exception e) {
            Log.e(TAG, "loadFromCacheSync error", e);
            deliverOnMain(callback, DataResult.error("Gagal baca cache lokal", e));
        }
    }

    private void loadFromCacheAsync(String bendelId, String nolanggFilter,
                                    AtomicBoolean cancelled, ResultCallback callback) {
        AppDatabase.databaseExecutor.execute(() -> {
            if (cancelled.get()) return;
            loadFromCacheSync(bendelId, nolanggFilter, callback);
        });
    }

    private void saveToCache(List<DataItem> items, String bendelId, String codeBendel, String periode, String cabang) {
        AppDatabase.databaseExecutor.execute(() -> {
            try {
                int unreadCount = countUnread(items);
                CachedBendelEntity bendelHeader = BendelMapper.buildBendelEntity(
                        codeBendel, periode, cabang, unreadCount);

                List<CachedPelangganBendelEntity> entities = items == null
                        ? new java.util.ArrayList<>()
                        : BendelMapper.toEntityList(items, bendelId);

                db.runInTransaction(() -> {
                    db.cachedBendelDao().insertOrReplace(bendelHeader);
                    db.cachedPelangganBendelDao().deleteByBendel(bendelId);
                    if (!entities.isEmpty()) {
                        db.cachedPelangganBendelDao().insertAll(entities);
                    }
                });

                Log.d(TAG, "Cached " + entities.size() + " pelanggan untuk bendel " + bendelId);

                cleanupEmptyBendelsIfEnabled();

            } catch (Exception e) {
                Log.e(TAG, "saveToCache error (non-fatal)", e);
            }
        });
    }

    private int countUnread(List<DataItem> items) {
        if (items == null) return 0;
        int count = 0;
        for (DataItem item : items) {
            if (!BendelMapper.isAlreadyRead(item)) {
                count++;
            }
        }
        return count;
    }

    // ============== CLEANUP ==============

    /**
     * Hapus bendel-bendel di cache yang sudah kosong (totalUnread=0).
     * Dipanggil saat user tap FAB Sync atau auto-sync selesai.
     */
    public static void cleanupEmptyBendelsStatic(Context context) {
        Context appContext = context.getApplicationContext();
        AppDatabase database = AppDatabase.getInstance(appContext);
        SettingsManager settingsManager = SettingsManager.getInstance(appContext);

        if (!settingsManager.isAutoCleanupCacheEnabled()) {
            Log.d(TAG, "Auto-cleanup disabled, skip");
            return;
        }

        AppDatabase.databaseExecutor.execute(() -> {
            try {
                List<CachedBendelEntity> empty = database.cachedBendelDao().getEmptyBendels();
                if (empty.isEmpty()) return;

                int deletedCount = 0;
                for (CachedBendelEntity bendel : empty) {
                    database.runInTransaction(() -> {
                        database.cachedPelangganBendelDao().deleteByBendel(bendel.id);
                        database.cachedBendelDao().deleteById(bendel.id);
                    });
                    deletedCount++;
                }
                Log.d(TAG, "Cleanup: deleted " + deletedCount + " empty bendels");
            } catch (Exception e) {
                Log.e(TAG, "Cleanup error", e);
            }
        });
    }

    public void cleanupEmptyBendelsIfEnabled() {
        cleanupEmptyBendelsStatic(appContext);
    }

    // ============== HELPERS ==============

    private void deliverOnMain(ResultCallback callback, DataResult result) {
        mainHandler.post(() -> callback.onResult(result));
    }
}