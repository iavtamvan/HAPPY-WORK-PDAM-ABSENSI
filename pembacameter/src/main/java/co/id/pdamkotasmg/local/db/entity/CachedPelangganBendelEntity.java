package co.id.pdamkotasmg.local.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Cache pelanggan yang ada di dalam satu bendel.
 *
 * Foreign key ke CachedBendelEntity dengan onDelete=CASCADE — kalau bendel
 * dihapus dari cache, pelanggannya ikut terhapus.
 *
 * Field di-flatten dari struktur API supaya:
 *   - hemat storage (tidak nested)
 *   - mudah di-query
 *   - tidak perlu serialize JSON
 *
 * Field yang tidak ditampilkan di list bisa di-stored sebagai JSON di
 * extraDataJson (untuk Fase 3 saat butuh full detail saat input).
 */
@Entity(
        tableName = "cached_pelanggan_bendel",
        indices = {
                @Index("bendelId"),
                @Index("nolangg")
        },
        foreignKeys = @ForeignKey(
                entity = CachedBendelEntity.class,
                parentColumns = "id",
                childColumns = "bendelId",
                onDelete = ForeignKey.CASCADE
        )
)
public class CachedPelangganBendelEntity {

    @PrimaryKey(autoGenerate = true)
    public long id;

    /**
     * FK ke CachedBendelEntity.id
     */
    @NonNull
    public String bendelId;

    @NonNull
    public String nolangg;       // ID Pelanggan, e.g. "06371005"

    public String nama;          // e.g. "PT KORONKA"
    public String alamat;        // e.g. "Semarang Bizpark A1"
    public String dism;          // e.g. "2493001"
    public String stMeter;       // e.g. "Aktif"

    /**
     * Bacaan periode lalu (untuk display "LALU 733 - 15m3").
     */
    public String lalu;          // e.g. "733"
    public String lalum3;        // e.g. "15m3"

    /**
     * Status sudah dibaca atau belum.
     * 0 = belum dibaca (tampil di list)
     * 1 = sudah dibaca (sembunyikan dari list "belum dibaca")
     */
    public int kodeStatus;

    /**
     * Apakah user sudah "menandai" pelanggan ini di lapangan (bookmark/flag).
     * Disimpan lokal — tidak perlu sync ke server.
     */
    public boolean userTandai;

    /**
     * Full JSON dari response API untuk pelanggan ini.
     * Diparse ulang saat masuk form input untuk dapat field yang lebih detail
     * (merek meter, nomormtr, tarif, dll).
     */
    public String extraDataJson;

    /**
     * Timestamp (millis) item ini di-cache.
     */
    public long cachedAt;
}