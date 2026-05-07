package co.id.pdamkotasmg.local.sync;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

/**
 * Helper untuk channel notifikasi + post hasil auto-sync.
 *
 * Notifikasi yang di-show:
 *   - Sukses semua: "Sinkronisasi selesai · X data terkirim"
 *   - Sebagian gagal: "Sinkronisasi selesai · X sukses, Y gagal"
 *   - Gagal semua: "Sinkronisasi gagal · Y data tidak terkirim"
 *
 * Tap notif → buka SettingsActivity (tab Data Pending akan tampil status terbaru).
 *
 * Permission requirement:
 *   - Android 13+ (API 33+) wajib runtime permission POST_NOTIFICATIONS.
 *     Kalau user belum grant, NotificationManagerCompat.notify() silent fail.
 *     Permission request dilakukan di SettingsFragment saat first toggle ON.
 */
public class SyncNotificationHelper {

    public static final String CHANNEL_ID = "pdam_sync_channel";
    public static final String CHANNEL_NAME = "Sinkronisasi PDAM";
    public static final String CHANNEL_DESC = "Notifikasi hasil sinkronisasi data offline ke server";

    public static final int NOTIFICATION_ID_SYNC_RESULT = 1001;

    private SyncNotificationHelper() {
        // util class
    }

    /**
     * Pastikan channel sudah dibuat. Idempotent — boleh dipanggil berkali-kali.
     * Wajib panggil sebelum show notif (Android 8+).
     */
    public static void ensureChannel(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return;

        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT);
        channel.setDescription(CHANNEL_DESC);

        NotificationManager nm = context.getSystemService(NotificationManager.class);
        if (nm != null) {
            nm.createNotificationChannel(channel);
        }
    }

    /**
     * Show notif hasil sync.
     *
     * @param context Context (boleh app context)
     * @param success jumlah pending yang sukses ter-sync
     * @param failed  jumlah pending yang gagal
     */
    public static void showSyncResult(Context context, int success, int failed) {
        if (success == 0 && failed == 0) {
            // Tidak ada apa-apa untuk dilaporkan
            return;
        }

        ensureChannel(context);

        String title;
        String body;

        if (failed == 0) {
            title = "Sinkronisasi selesai ✓";
            body = success + " data berhasil terkirim ke server";
        } else if (success == 0) {
            title = "Sinkronisasi gagal";
            body = failed + " data tidak terkirim. Buka aplikasi untuk detail.";
        } else {
            title = "Sinkronisasi selesai (sebagian)";
            body = "✓ " + success + " sukses, ✗ " + failed + " gagal";
        }

        // Tap notif → buka SettingsActivity
        Intent intent = buildOpenSettingsIntent(context);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.stat_sys_upload_done)
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(body))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        try {
            NotificationManagerCompat.from(context)
                    .notify(NOTIFICATION_ID_SYNC_RESULT, builder.build());
        } catch (SecurityException e) {
            // Android 13+ tanpa permission POST_NOTIFICATIONS — silent fail
        }
    }

    /**
     * Build Intent untuk buka SettingsActivity dari notif.
     *
     * Pakai reflection ke nama class supaya tidak hard-coded import — kalau
     * SettingsActivity dipindah package, cukup ganti string di sini.
     */
    private static Intent buildOpenSettingsIntent(Context context) {
        Intent intent;
        try {
            Class<?> settingsCls = Class.forName(
                    "co.id.pdamkotasmg.ui.activity.settings.SettingsActivity");
            intent = new Intent(context, settingsCls);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        } catch (ClassNotFoundException e) {
            // Fallback: launcher intent
            intent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
            if (intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            } else {
                intent = new Intent();
            }
        }
        return intent;
    }
}