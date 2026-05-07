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
 * STRATEGI BARU (Fase 2 patch):
 *   List bendel SKIP toggle Mode Offline. Logikanya:
 *
 *     ada koneksi  → SELALU hit API (data segar, banyak angka, sering berubah)
 *                    → cache di-overwrite dengan response terbaru
 *     no koneksi   → fallback ke cache + tampilkan banner "no koneksi"
 *
 *   Toggle Mode Offline TIDAK pengaruhi pembacaan list — toggle itu nanti
 *   khusus untuk INPUT bacaan (Fase 3+).
 *
 *   Alasan: petugas yang ada di kantor dan toggle Offline tetap mau lihat
 *   list yang akurat. Cache cuma untuk "darurat no signal", bukan default.
 */
public class BendelRepository {

    private static final String TAG = "BendelRepository";

    private final Context appContext;
    private final AppDatabase db;
    private final Handler mainHandler;

    public BendelRepository(Context context) {
        this.appContext = context.getApplicationContext();
        this.db = AppDatabase.getInstance(appContext);
        this.mainHandler = new Handler(Looper.getMainLooper());
    }

    public interface ResultCallback {
        /**
         * Dipanggil sekali saat hasil siap. Selalu di main thread.
         */
        void onResult(DataResult result);
    }

    public interface Cancellable {
        void cancel();
        boolean isCancelled();
    }

    /**
     * Ambil daftar pelanggan dalam satu bendel.
     *
     * @param token         access token
     * @param codeBendel    kode bendel
     * @param nolanggFilter optional filter nolangg
     * @param callback      dipanggil sekali — di main thread
     * @return Cancellable — Activity wajib cancel di onDestroy
     */
    public Cancellable getBendel(String token, String codeBendel, String nolanggFilter, ResultCallback callback) {
        String periode = appContext.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE)
                .getString(Config.SHARED_PERIODE, "");
        String cabang = appContext.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE)
                .getString(Config.SHARED_CABANG, "");

        String bendelId = CachedBendelEntity.buildId(codeBendel, periode, cabang);

        AtomicBoolean cancelled = new AtomicBoolean(false);
        Call<BendelRootModel>[] currentCall = new Call[1];

        // PATCH: list bendel skip toggle. Cek koneksi — kalau ada, ALWAYS hit API.
        if (NetworkUtil.isOnline(appContext)) {
            Log.d(TAG, "Online → hit API");
            Call<BendelRootModel> call = fetchFromNetwork(token, codeBendel, nolanggFilter,
                    bendelId, periode, cabang, cancelled, callback);
            currentCall[0] = call;
        } else {
            Log.d(TAG, "No connection → fallback to cache");
            loadFromCacheAsync(bendelId, nolanggFilter, cancelled, callback);
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
                        deliverOnMain(callback, DataResult.fromNetwork(items));
                        return;
                    }

                    saveToCache(items, bendelId, codeBendel, periode, cabang);
                    deliverOnMain(callback, DataResult.fromNetwork(items));

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

    private void loadFromCacheAsync(String bendelId, String nolanggFilter,
                                    AtomicBoolean cancelled, ResultCallback callback) {
        AppDatabase.databaseExecutor.execute(() -> {
            if (cancelled.get()) return;
            try {
                List<CachedPelangganBendelEntity> entities;
                if (nolanggFilter == null || nolanggFilter.trim().isEmpty()) {
                    // Hanya tampilkan yang BELUM dibaca (kodeStatus=0)
                    entities = db.cachedPelangganBendelDao().getUnreadByBendel(bendelId);
                } else {
                    CachedPelangganBendelEntity hit = db.cachedPelangganBendelDao()
                            .findInBendel(bendelId, nolanggFilter.trim());
                    entities = hit == null
                            ? new java.util.ArrayList<>()
                            : java.util.Collections.singletonList(hit);
                }

                List<DataItem> data = BendelMapper.fromEntityList(entities);
                deliverOnMain(callback, DataResult.fromCache(data));

            } catch (Exception e) {
                Log.e(TAG, "loadFromCacheAsync error", e);
                deliverOnMain(callback, DataResult.error("Gagal baca cache lokal", e));
            }
        });
    }

    private void saveToCache(List<DataItem> items, String bendelId, String codeBendel, String periode, String cabang) {
        AppDatabase.databaseExecutor.execute(() -> {
            try {
                int unreadCount = countUnread(items);
                CachedBendelEntity bendelHeader = BendelMapper.buildBendelEntity(
                        codeBendel, periode, cabang, unreadCount);

                List<CachedPelangganBendelEntity> entities = BendelMapper.toEntityList(items, bendelId);

                db.runInTransaction(() -> {
                    db.cachedBendelDao().insertOrReplace(bendelHeader);
                    db.cachedPelangganBendelDao().deleteByBendel(bendelId);
                    db.cachedPelangganBendelDao().insertAll(entities);
                });

                Log.d(TAG, "Cached " + entities.size() + " pelanggan untuk bendel " + bendelId);
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

    // ============== HELPERS ==============

    private void deliverOnMain(ResultCallback callback, DataResult result) {
        mainHandler.post(() -> callback.onResult(result));
    }
}