package co.id.pdamkotasmg.local.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Foto yang akan di-upload bersamaan dengan bacaan.
 *
 * 1 PendingBacaanEntity bisa punya banyak foto (e.g. foto meter + foto manometer
 * untuk khusus bendel).
 *
 * Foto disimpan di filesDir/pending_photos/ — private app storage yang aman
 * dari user dan cleaner. Field {@link #localFilePath} berisi absolute path
 * ke file foto.
 */
@Entity(
        tableName = "pending_foto",
        indices = {@Index("pendingBacaanId")},
        foreignKeys = @ForeignKey(
                entity = PendingBacaanEntity.class,
                parentColumns = "id",
                childColumns = "pendingBacaanId",
                onDelete = ForeignKey.CASCADE
        )
)
public class PendingFotoEntity {

    @PrimaryKey(autoGenerate = true)
    public long id;

    /**
     * FK ke PendingBacaanEntity.id — semua foto akan otomatis ter-delete kalau
     * pending bacaan-nya di-delete.
     */
    public long pendingBacaanId;

    /**
     * Jenis foto:
     *   "foto_meter"     = foto meter water (yang dilihat angka-nya)
     *   "foto_manometer" = foto manometer (untuk industrial customers)
     *   "watermarked"    = foto sudah di-watermark dengan info GPS, dll
     */
    @NonNull
    public String jenis;

    /**
     * Absolute path ke file foto di filesDir/pending_photos/...
     * Saat sync, file ini di-upload ke server lalu di-delete dari storage.
     */
    @NonNull
    public String localFilePath;

    /**
     * Server path response — di-set setelah upload sukses (untuk audit).
     */
    public String serverPath;

    /**
     * Server URL response — di-set setelah upload sukses.
     */
    public String serverUrl;

    /**
     * Timestamp (millis) foto dibuat.
     */
    public long createdAt;

    /**
     * Status upload terpisah dari status pending bacaan.
     *   0 = belum upload
     *   1 = sedang upload
     *   2 = sukses upload (server fields ter-isi)
     *   3 = gagal upload
     */
    public int uploadStatus;

    public String lastError;

    // ============== JENIS CONSTANTS ==============

    public static final String JENIS_FOTO_METER = "foto_meter";
    public static final String JENIS_FOTO_MANOMETER = "foto_manometer";

    // ============== STATUS CONSTANTS ==============

    public static final int STATUS_PENDING = 0;
    public static final int STATUS_IN_FLIGHT = 1;
    public static final int STATUS_SUCCESS = 2;
    public static final int STATUS_FAILED = 3;
}