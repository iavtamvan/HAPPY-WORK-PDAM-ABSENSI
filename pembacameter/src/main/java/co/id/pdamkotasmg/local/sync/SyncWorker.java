package co.id.pdamkotasmg.local.sync;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.pdamkotasmg.goodday.utils.Config;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import co.id.pdamkotasmg.local.NetworkUtil;
import co.id.pdamkotasmg.local.db.AppDatabase;
import co.id.pdamkotasmg.local.db.entity.PendingBacaanEntity;

/**
 * Background worker untuk auto-sync pending bacaan.
 *
 * Trigger: WorkManager periodic, sesuai schedule {@link SyncScheduler#enableAutoSync}.
 *
 * Behavior:
 *   1. Cek pending unsynced — kalau kosong, return success (no-op, tidak post notif)
 *   2. Reuse SyncManager.startSyncAll() dengan await pakai CountDownLatch
 *   3. Post notifikasi summary (X sukses, Y gagal)
 *   4. Return Result.success() — meskipun ada item gagal, worker tetap success
 *      karena gagal-nya akan di-retry di run berikutnya (no cap retry).
 *
 * KENAPA pakai CountDownLatch?
 *   SyncManager.startSyncAll() callback-based async — Worker.doWork() perlu
 *   block sampai sync selesai. Pakai latch buat sinkronisasi thread.
 */
public class SyncWorker extends Worker {

    private static final String TAG = "SyncWorker";
    public static final String UNIQUE_WORK_NAME = "pdam_auto_sync";

    public SyncWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Context ctx = getApplicationContext();
        Log.d(TAG, "Auto-sync triggered");

        try {
            // 1. Cek pending — kalau kosong, no-op
            int unsyncedCount = AppDatabase.getInstance(ctx).pendingBacaanDao().countUnsynced();
            if (unsyncedCount == 0) {
                Log.d(TAG, "No pending data, skipping sync");
                return Result.success();
            }

            // 2. Cek koneksi (WorkManager constraint sudah handle, tapi double-check)
            if (!NetworkUtil.isOnline(ctx)) {
                Log.w(TAG, "No connection, will retry");
                return Result.retry();
            }

            // 3. Ambil token
            SharedPreferences sp = ctx.getSharedPreferences(
                    Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
            String token = sp.getString(Config.SHARED_ACCESS_TOKEN, "");
            if (token == null || token.isEmpty()) {
                Log.e(TAG, "Token empty, cannot sync");
                return Result.success(); // skip, akan dicoba di run berikutnya
            }

            // 4. Sync sequential, tunggu selesai dengan CountDownLatch
            CountDownLatch latch = new CountDownLatch(1);
            AtomicInteger finalSuccess = new AtomicInteger(0);
            AtomicInteger finalFailed = new AtomicInteger(0);

            SyncManager.getInstance(ctx).startSyncAll(token, event -> {
                if (event.phase == SyncProgressEvent.Phase.ALL_DONE) {
                    finalSuccess.set(event.successCount);
                    finalFailed.set(event.failedCount);
                    latch.countDown();
                }
            });

            // Tunggu max 10 menit (asumsi sync 1 batch tidak lebih dari itu)
            boolean completed = latch.await(10, TimeUnit.MINUTES);
            if (!completed) {
                Log.w(TAG, "Sync timeout after 10 minutes");
                return Result.retry();
            }

            int success = finalSuccess.get();
            int failed = finalFailed.get();
            Log.d(TAG, "Auto-sync done: " + success + " success, " + failed + " failed");

            // 5. Post notifikasi summary
            SyncNotificationHelper.showSyncResult(ctx, success, failed);

            return Result.success();

        } catch (Exception e) {
            Log.e(TAG, "Auto-sync error", e);
            return Result.retry();
        }
    }
}