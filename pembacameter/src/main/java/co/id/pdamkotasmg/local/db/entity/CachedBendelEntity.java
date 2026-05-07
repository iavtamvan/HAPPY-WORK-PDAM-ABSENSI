package co.id.pdamkotasmg.local.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Cache satu bendel (header). Diisi dari API saat online,
 * dibaca dari local saat offline.
 *
 * Composite key bendel:
 *   - codeBendel (e.g. "2493")
 *   - periode (e.g. "202604")
 *   - cabang (e.g. "Barat")
 *
 * Tapi untuk simplicity di Fase 1, kita pakai composite ID generated:
 *   "{codeBendel}_{periode}_{cabang}"
 */
@Entity(
        tableName = "cached_bendel",
        indices = {
                @Index(value = {"codeBendel", "periode", "cabang"}, unique = true)
        }
)
public class CachedBendelEntity {

    @PrimaryKey
    @NonNull
    public String id;  // composite: "{codeBendel}_{periode}_{cabang}"

    @NonNull
    public String codeBendel;

    @NonNull
    public String periode;

    @NonNull
    public String cabang;

    /**
     * Total pelanggan yang BELUM dibaca di bendel ini.
     * Update setiap kali list di-refresh dari server.
     */
    public int totalUnread;

    /**
     * Timestamp (millis) terakhir cache ini di-refresh dari API.
     * Untuk cek apakah cache stale.
     */
    public long lastFetchedAt;

    /**
     * Helper: build composite ID dari komponen.
     */
    public static String buildId(String codeBendel, String periode, String cabang) {
        return codeBendel + "_" + periode + "_" + cabang;
    }
}