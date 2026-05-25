package co.id.pdamkotasmg.local.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Pending bacaan — 1 baris untuk satu input yang belum tersync ke server.
 *
 * 1 tabel untuk semua jenis bacaan (per pelanggan, per bendel khusus,
 * baca ulang, manometer manual). Dibedakan kolom {@link #jenis}.
 *
 * Field di-flatten dari payload yang dikirim ke API supaya:
 *   - bisa langsung dipakai untuk re-construct request saat sync
 *   - tidak perlu deserialize JSON yang risky
 *
 * Foto disimpan TERPISAH di {@link PendingFotoEntity} dengan FK ke pending bacaan ini.
 * Alasan: 1 bacaan bisa punya 2+ foto (foto meter + foto manometer).
 */
@Entity(
        tableName = "pending_bacaan",
        indices = {
                @Index("nolangg"),
                @Index("syncStatus"),
                @Index("jenis")
        }
)
public class PendingBacaanEntity {

    @PrimaryKey(autoGenerate = true)
    public long id;

    /**
     * Jenis bacaan — sesuai dengan kategori menu input:
     *   "khusus_bendel"   = BendelPembacaKhususActivity
     *   "per_pelanggan"   = (Fase 3 ronde 2)
     *   "per_foto_meter"  = (Fase 3 ronde 2)
     *   "baca_ulang"      = (Fase 3 ronde 2)
     */
    @NonNull
    public String jenis;

    // ============== IDENTITAS ==============

    @NonNull
    public String nolangg;

    public String periode;
    public String cabang;
    public String bendel;       // codeBendel (kalau dari per-bendel flow)

    // ============== BACAAN ==============

    /**
     * Nilai meter sekarang yang user input (e.g. "1234").
     */
    public String kini;

    /**
     * Kode status meter (e.g. "1" = normal, "2" = rusak, dll).
     * Tergantung mapping di project Anda.
     */
    public String kodeStatusMeter;

    public String keterangan;
    public String manometer;

    // ============== LOKASI ==============

    public Double latitude;
    public Double longitude;
    public String addressGps;

    // ============== METADATA ==============

    /**
     * action_code untuk endpoint update — biasanya konstanta seperti "input"/"edit"/"baca_ulang".
     */
    public String actionCode;

    /**
     * NPP / ID petugas yang input.
     */
    public String npp;

    /**
     * Versi app + model device (untuk audit di server).
     */
    public String versionInfo;

    /**
     * Timestamp (millis) saat user submit.
     */
    public long createdAt;

    // ============== SYNC TRACKING ==============

    /**
     * Status sync ke server:
     *   0 = belum sync (default)
     *   1 = sedang sync (in flight)
     *   2 = sukses sync (akan dihapus di cleanup)
     *   3 = gagal sync (error message di lastError)
     */
    public int syncStatus;

    /**
     * Berapa kali sudah dicoba sync. Sync engine bisa cap retry max attempts.
     */
    public int retryCount;

    /**
     * Pesan error sync terakhir (untuk display di Pending screen Fase 4).
     */
    public String lastError;

    /**
     * Timestamp (millis) sync sukses.
     */
    public long syncedAt;

    // ============== JENIS CONSTANTS ==============

    public static final String JENIS_KHUSUS_BENDEL = "khusus_bendel";
    public static final String JENIS_PER_PELANGGAN = "per_pelanggan";
    public static final String JENIS_PER_FOTO_METER = "per_foto_meter";
    public static final String JENIS_BACA_ULANG = "baca_ulang";

    // ============== SYNC STATUS CONSTANTS ==============

    public static final int STATUS_PENDING = 0;
    public static final int STATUS_IN_FLIGHT = 1;
    public static final int STATUS_SUCCESS = 2;
    public static final int STATUS_FAILED = 3;
}