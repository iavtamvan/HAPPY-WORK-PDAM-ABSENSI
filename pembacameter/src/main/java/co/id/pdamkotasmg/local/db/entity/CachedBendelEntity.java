package co.id.pdamkotasmg.local.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Cached header bendel.
 *
 * Composite ID: {codeBendel}_{periode}_{cabang}
 * (1 codeBendel bisa beda data antar periode/cabang).
 *
 * SCHEMA NOTE:
 *   Entity ini match schema asli yang sudah deployed di production:
 *     - field: totalUnread, lastFetchedAt
 *     - unique index pada (codeBendel, periode, cabang)
 *
 *   Field baru di Fase 6:
 *     - lastSyncAt (ditambah via MIGRATION_2_3)
 *
 *   Why two timestamp fields (lastFetchedAt & lastSyncAt)?
 *     - lastFetchedAt: existing dari schema lama, di-set tiap kali insert (saveToCache)
 *     - lastSyncAt: dipakai oleh UI untuk display "Disinkron X jam lalu"
 *     Keduanya di-set ke value yang sama saat ini, tapi dipisah supaya kalau
 *     ke depan ada perbedaan semantic (mis. lastSyncAt untuk manual sync,
 *     lastFetchedAt untuk auto refresh) bisa beda value.
 */
@Entity(
        tableName = "cached_bendel",
        indices = {@Index(value = {"codeBendel", "periode", "cabang"}, unique = true)}
)
public class CachedBendelEntity {

    @PrimaryKey
    @NonNull
    public String id; // composite: codeBendel_periode_cabang

    @NonNull
    public String codeBendel;

    @NonNull
    public String periode;

    @NonNull
    public String cabang;

    public int totalUnread;

    /**
     * Timestamp millis kapan cache pertama kali dibuat / di-replace via saveToCache.
     */
    public long lastFetchedAt;

    /**
     * Timestamp millis kapan terakhir kali user lihat data segar dari API.
     * Dipakai untuk display "Disinkron X jam lalu" di banner.
     * Migration v2→v3 default 0; akan auto-update di saveToCache berikutnya.
     */
    public long lastSyncAt;

    public static String buildId(String codeBendel, String periode, String cabang) {
        return safeStr(codeBendel) + "_" + safeStr(periode) + "_" + safeStr(cabang);
    }

    private static String safeStr(String s) {
        return s == null ? "" : s;
    }
}