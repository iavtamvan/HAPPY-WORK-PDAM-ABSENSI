package co.id.pdamkotasmg.local.sync;

import android.content.Context;
import android.util.Log;

import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Helper untuk schedule/cancel auto-sync background work via WorkManager.
 *
 * Pakai unique work name supaya hanya ada 1 instance schedule kapanpun
 * (re-schedule pakai REPLACE policy).
 *
 * Constraints:
 *   - WiFi only ON  → NetworkType.UNMETERED (cuma jalan di WiFi/Ethernet)
 *   - WiFi only OFF → NetworkType.CONNECTED (jalan di WiFi atau cellular)
 *
 * Interval:
 *   WorkManager minimum periodic interval = 15 menit. Kalau user pilih 30 menit
 *   atau lebih, OK. Untuk reliability, rekomendasi 1 jam ke atas.
 */
public class SyncScheduler {

    private static final String TAG = "SyncScheduler";

    private SyncScheduler() {
        // util class
    }

    /**
     * Schedule periodic sync dengan parameter user.
     *
     * @param context        Context
     * @param intervalMinutes interval antara run, MIN 15 menit (WorkManager constraint)
     * @param wifiOnly       true → cuma jalan di WiFi
     */
    public static void enableAutoSync(Context context, int intervalMinutes, boolean wifiOnly) {
        if (intervalMinutes < 15) {
            Log.w(TAG, "Interval " + intervalMinutes + " min, clamped to 15 (WM minimum)");
            intervalMinutes = 15;
        }

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(wifiOnly ? NetworkType.UNMETERED : NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(true)  // skip kalau baterai low
                .build();

        PeriodicWorkRequest request = new PeriodicWorkRequest.Builder(
                SyncWorker.class,
                intervalMinutes, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .addTag(SyncWorker.UNIQUE_WORK_NAME)
                .build();

        WorkManager.getInstance(context.getApplicationContext())
                .enqueueUniquePeriodicWork(
                        SyncWorker.UNIQUE_WORK_NAME,
                        // UPDATE = kalau sudah ada schedule, replace dengan yang baru
                        ExistingPeriodicWorkPolicy.UPDATE,
                        request);

        Log.d(TAG, "Auto-sync scheduled: every " + intervalMinutes
                + " min, wifiOnly=" + wifiOnly);
    }

    /**
     * Cancel auto-sync scheduled work.
     */
    public static void disableAutoSync(Context context) {
        WorkManager.getInstance(context.getApplicationContext())
                .cancelUniqueWork(SyncWorker.UNIQUE_WORK_NAME);
        Log.d(TAG, "Auto-sync disabled");
    }

    /**
     * Cek apakah auto-sync sudah ter-schedule.
     */
    public static boolean isScheduled(Context context) {
        try {
            ListenableFuture<List<WorkInfo>> future = WorkManager
                    .getInstance(context.getApplicationContext())
                    .getWorkInfosForUniqueWork(SyncWorker.UNIQUE_WORK_NAME);
            List<WorkInfo> infos = future.get();
            for (WorkInfo info : infos) {
                if (info.getState() == WorkInfo.State.ENQUEUED
                        || info.getState() == WorkInfo.State.RUNNING) {
                    return true;
                }
            }
        } catch (ExecutionException | InterruptedException e) {
            Log.e(TAG, "isScheduled error", e);
        }
        return false;
    }
}