package co.id.pdamkotasmg.local;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Manager untuk semua app preferences terkait fitur offline-first.
 *
 * Fase 5 additions:
 *   - KEY_AUTO_SYNC_INTERVAL_MINUTES — frekuensi auto-sync
 *   - KEY_AUTO_SYNC_WIFI_ONLY        — constraint koneksi
 */
public class SettingsManager {

    private static final String PREF_NAME = "pdam_offline_settings";

    // Keys
    private static final String KEY_OFFLINE_MODE = "offline_mode_enabled";
    private static final String KEY_AUTO_SYNC = "auto_sync_enabled";
    private static final String KEY_LAST_SYNC_TIMESTAMP = "last_sync_timestamp";
    private static final String KEY_AUTO_SYNC_INTERVAL_MINUTES = "auto_sync_interval_minutes";
    private static final String KEY_AUTO_SYNC_WIFI_ONLY = "auto_sync_wifi_only";
    private static final String KEY_AUTO_CLEANUP_CACHE = "auto_cleanup_cache_enabled";

    // Default values
    private static final boolean DEFAULT_OFFLINE_MODE = false;
    private static final boolean DEFAULT_AUTO_SYNC = false;  // Fase 5: default OFF — user opt-in
    private static final int DEFAULT_INTERVAL_MINUTES = 60;  // 1 jam
    private static final boolean DEFAULT_WIFI_ONLY = true;   // Hemat data petugas
    private static final boolean DEFAULT_AUTO_CLEANUP = true; // Fase 6: default ON — hemat storage

    // Interval choices (untuk UI dropdown)
    public static final int[] INTERVAL_CHOICES_MINUTES = {15, 30, 60, 180, 360, 720};
    public static final String[] INTERVAL_CHOICES_LABEL = {
            "15 menit", "30 menit", "1 jam", "3 jam", "6 jam", "12 jam"
    };

    private static volatile SettingsManager INSTANCE;
    private final SharedPreferences prefs;

    private SettingsManager(Context appContext) {
        this.prefs = appContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static SettingsManager getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SettingsManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SettingsManager(context.getApplicationContext());
                }
            }
        }
        return INSTANCE;
    }

    // ============== OFFLINE MODE TOGGLE ==============

    public boolean isOfflineModeEnabled() {
        return prefs.getBoolean(KEY_OFFLINE_MODE, DEFAULT_OFFLINE_MODE);
    }

    public void setOfflineModeEnabled(boolean enabled) {
        prefs.edit().putBoolean(KEY_OFFLINE_MODE, enabled).apply();
    }

    // ============== AUTO-SYNC TOGGLE ==============

    public boolean isAutoSyncEnabled() {
        return prefs.getBoolean(KEY_AUTO_SYNC, DEFAULT_AUTO_SYNC);
    }

    public void setAutoSyncEnabled(boolean enabled) {
        prefs.edit().putBoolean(KEY_AUTO_SYNC, enabled).apply();
    }

    // ============== AUTO-SYNC INTERVAL ==============

    public int getAutoSyncIntervalMinutes() {
        return prefs.getInt(KEY_AUTO_SYNC_INTERVAL_MINUTES, DEFAULT_INTERVAL_MINUTES);
    }

    public void setAutoSyncIntervalMinutes(int minutes) {
        prefs.edit().putInt(KEY_AUTO_SYNC_INTERVAL_MINUTES, minutes).apply();
    }

    /**
     * Get index dari INTERVAL_CHOICES_MINUTES untuk current value.
     * Untuk pre-select dropdown UI.
     */
    public int getAutoSyncIntervalChoiceIndex() {
        int current = getAutoSyncIntervalMinutes();
        for (int i = 0; i < INTERVAL_CHOICES_MINUTES.length; i++) {
            if (INTERVAL_CHOICES_MINUTES[i] == current) return i;
        }
        return 2; // default ke "1 jam"
    }

    // ============== WIFI ONLY ==============

    public boolean isAutoSyncWifiOnly() {
        return prefs.getBoolean(KEY_AUTO_SYNC_WIFI_ONLY, DEFAULT_WIFI_ONLY);
    }

    public void setAutoSyncWifiOnly(boolean wifiOnly) {
        prefs.edit().putBoolean(KEY_AUTO_SYNC_WIFI_ONLY, wifiOnly).apply();
    }

    // ============== LAST SYNC TIMESTAMP ==============

    public long getLastSyncTimestamp() {
        return prefs.getLong(KEY_LAST_SYNC_TIMESTAMP, 0L);
    }

    public void setLastSyncTimestamp(long timestamp) {
        prefs.edit().putLong(KEY_LAST_SYNC_TIMESTAMP, timestamp).apply();
    }

    public void markSyncSuccessNow() {
        setLastSyncTimestamp(System.currentTimeMillis());
    }

    // ============== AUTO-CLEANUP CACHE BENDEL ==============

    public boolean isAutoCleanupCacheEnabled() {
        return prefs.getBoolean(KEY_AUTO_CLEANUP_CACHE, DEFAULT_AUTO_CLEANUP);
    }

    public void setAutoCleanupCacheEnabled(boolean enabled) {
        prefs.edit().putBoolean(KEY_AUTO_CLEANUP_CACHE, enabled).apply();
    }
}