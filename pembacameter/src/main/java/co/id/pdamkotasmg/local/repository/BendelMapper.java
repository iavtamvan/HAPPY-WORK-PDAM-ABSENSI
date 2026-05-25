package co.id.pdamkotasmg.local.repository;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import co.id.pdamkotasmg.local.db.entity.CachedBendelEntity;
import co.id.pdamkotasmg.local.db.entity.CachedPelangganBendelEntity;
import co.id.pdamkotasmg.model.bendel.DataItem;
import co.id.pdamkotasmg.model.bendel.RlDataBacaSekarang;
import co.id.pdamkotasmg.model.bendel.RlTrbacaItem;

/**
 * Mapper antara model API ↔ Room entity.
 *
 * STRUKTUR (verified dari upload user):
 *
 *   DataItem
 *     ├── nolangg, nama, alamat, dism (display dasar)
 *     ├── merek, nomormtr, tarif, st (status/info meter)
 *     ├── kec, kel, cabang, sumur (lokasi)
 *     ├── tglMeter, tglPasang, tglBuka, tglTutup, tanggal (tanggal-tanggal)
 *     ├── diameter, tlp
 *     │
 *     ├── rlTrbaca (List<RlTrbacaItem>)
 *     │     └── BACAAN PERIODE LALU - untuk display "LALU 733 - 15m3"
 *     │     └── fields: m3, kini, nolangg, periode
 *     │
 *     └── rlDtBacaSekarang (List<RlDataBacaSekarang>)
 *           └── BACAAN PERIODE SEKARANG - untuk cek sudah dibaca atau belum
 *           └── fields: m3, kini, nolangg, periode, tglBaca, tandai,
 *                       surveyKoordinat, surveyPelanggan
 *
 * LOGIC "sudah dibaca atau belum":
 *   Karena rlDtBacaSekarang TIDAK punya nested object dengan field "kode"/"status",
 *   kita pakai logika: kalau rlDtBacaSekarang ada item dengan kini non-empty
 *   → sudah dibaca (kodeStatus=1). Kalau kosong/null → belum dibaca (kodeStatus=0).
 */
public class BendelMapper {

    private static final Gson gson = new Gson();

    private BendelMapper() {
        // util class
    }

    // ============== API → ROOM ==============

    public static CachedPelangganBendelEntity toEntity(DataItem item, String bendelId) {
        CachedPelangganBendelEntity entity = new CachedPelangganBendelEntity();
        entity.bendelId = bendelId;

        // Display dasar
        entity.nolangg = nullSafe(item.getNolangg());
        entity.nama = nullSafe(item.getNama());
        entity.alamat = nullSafe(item.getAlamat());
        entity.dism = nullSafe(item.getDism());

        // Status meter — pakai field "st" di top-level DataItem
        entity.stMeter = nullSafe(item.getSt());

        // Bacaan periode LALU dari rlTrbaca[0]
        entity.lalu = extractKiniPeriodeLalu(item);
        entity.lalum3 = extractM3PeriodeLalu(item);

        // Status sudah dibaca: cek apakah rlDtBacaSekarang sudah punya nilai kini
        entity.kodeStatus = isAlreadyRead(item) ? 1 : 0;

        entity.userTandai = false;
        entity.cachedAt = System.currentTimeMillis();

        // Simpan full DataItem sebagai JSON — supaya saat user buka form input
        // (Fase 3), semua field nested tetap bisa di-restore utuh.
        try {
            entity.extraDataJson = gson.toJson(item);
        } catch (Exception e) {
            entity.extraDataJson = null;
        }

        return entity;
    }

    public static List<CachedPelangganBendelEntity> toEntityList(List<DataItem> items, String bendelId) {
        List<CachedPelangganBendelEntity> result = new ArrayList<>();
        if (items == null) return result;
        for (DataItem item : items) {
            if (item != null) {
                result.add(toEntity(item, bendelId));
            }
        }
        return result;
    }

    // ============== ROOM → API ==============

    public static DataItem fromEntity(CachedPelangganBendelEntity entity) {
        if (entity == null) return null;

        // Strategy 1 (preferred): restore dari JSON utuh — semua field nested
        // (rlTrbaca, rlDtBacaSekarang, dll) tetap utuh untuk form input.
        if (entity.extraDataJson != null && !entity.extraDataJson.isEmpty()) {
            try {
                DataItem restored = gson.fromJson(entity.extraDataJson, DataItem.class);
                if (restored != null) return restored;
            } catch (Exception e) {
                // JSON corrupt — fallback
            }
        }

        // Strategy 2 (fallback): build minimal DataItem dari kolom dasar.
        // Cuma display fields yang ke-set; nested list akan null.
        DataItem item = new DataItem();
        item.setNolangg(entity.nolangg);
        item.setNama(entity.nama);
        item.setAlamat(entity.alamat);
        item.setDism(entity.dism);
        item.setSt(entity.stMeter);
        return item;
    }

    public static List<DataItem> fromEntityList(List<CachedPelangganBendelEntity> entities) {
        List<DataItem> result = new ArrayList<>();
        if (entities == null) return result;
        for (CachedPelangganBendelEntity e : entities) {
            DataItem item = fromEntity(e);
            if (item != null) result.add(item);
        }
        return result;
    }

    // ============== BENDEL ENTITY HELPER ==============

    public static CachedBendelEntity buildBendelEntity(String codeBendel, String periode, String cabang, int unreadCount) {
        CachedBendelEntity bendel = new CachedBendelEntity();
        bendel.id = CachedBendelEntity.buildId(codeBendel, periode, cabang);
        bendel.codeBendel = nullSafe(codeBendel);
        bendel.periode = nullSafe(periode);
        bendel.cabang = nullSafe(cabang);
        bendel.totalUnread = unreadCount;
        long now = System.currentTimeMillis();
        bendel.lastFetchedAt = now;
        bendel.lastSyncAt = now;
        return bendel;
    }

    // ============== HELPERS ==============

    private static String nullSafe(String s) {
        return s == null ? "" : s;
    }

    /**
     * Apakah pelanggan sudah dibaca di periode sekarang?
     *
     * Logic: kalau rlDtBacaSekarang punya item dengan field "kini" non-empty
     * → sudah dibaca.
     */
    public static boolean isAlreadyRead(DataItem item) {
        if (item == null) return false;
        List<RlDataBacaSekarang> skrgList = item.getRlDtBacaSekarang();
        if (skrgList == null || skrgList.isEmpty()) return false;

        RlDataBacaSekarang skrg = skrgList.get(0);
        if (skrg == null) return false;

        String kini = skrg.getKini();
        return kini != null && !kini.trim().isEmpty();
    }

    /**
     * Bacaan kini periode LALU (untuk display "LALU 733").
     */
    private static String extractKiniPeriodeLalu(DataItem item) {
        List<RlTrbacaItem> rlTrbaca = item.getRlTrbaca();
        if (rlTrbaca == null || rlTrbaca.isEmpty()) return "";

        RlTrbacaItem first = rlTrbaca.get(0);
        if (first == null) return "";

        return nullSafe(first.getKini());
    }

    /**
     * Pemakaian m3 periode LALU (untuk display "15m3").
     */
    private static String extractM3PeriodeLalu(DataItem item) {
        List<RlTrbacaItem> rlTrbaca = item.getRlTrbaca();
        if (rlTrbaca == null || rlTrbaca.isEmpty()) return "";

        RlTrbacaItem first = rlTrbaca.get(0);
        if (first == null) return "";

        return nullSafe(first.getM3());
    }
}