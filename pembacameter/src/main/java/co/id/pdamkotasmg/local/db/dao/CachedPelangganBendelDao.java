package co.id.pdamkotasmg.local.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import co.id.pdamkotasmg.local.db.entity.CachedPelangganBendelEntity;

@Dao
public interface CachedPelangganBendelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CachedPelangganBendelEntity> entities);

    @Query("SELECT * FROM cached_pelanggan_bendel WHERE bendelId = :bendelId ORDER BY id ASC")
    List<CachedPelangganBendelEntity> getAllByBendel(String bendelId);

    /**
     * Pelanggan yang BELUM dibaca di bendel ini (kodeStatus = 0).
     * Dipakai untuk tampilkan di list bendel.
     */
    @Query("SELECT * FROM cached_pelanggan_bendel WHERE bendelId = :bendelId AND kodeStatus = 0 ORDER BY id ASC")
    List<CachedPelangganBendelEntity> getUnreadByBendel(String bendelId);

    @Query("SELECT * FROM cached_pelanggan_bendel WHERE bendelId = :bendelId AND nolangg = :nolangg LIMIT 1")
    CachedPelangganBendelEntity findInBendel(String bendelId, String nolangg);

    /**
     * Sama, tapi cari di SEMUA bendel — fallback kalau bendelId tidak diketahui.
     */
    @Query("SELECT * FROM cached_pelanggan_bendel WHERE nolangg = :nolangg LIMIT 1")
    CachedPelangganBendelEntity findByNolangg(String nolangg);

    @Query("SELECT COUNT(*) FROM cached_pelanggan_bendel WHERE bendelId = :bendelId AND kodeStatus = 0")
    int countUnreadInBendel(String bendelId);

    /**
     * Tandai pelanggan sebagai sudah dibaca (kodeStatus = 1).
     *
     * RETURN: jumlah row yang ter-update.
     *   - 0 = pelanggan tidak ditemukan di bendel itu (mismatch bendelId / nolangg tidak di-cache)
     *   - 1 = sukses
     *   - >1 = sangat jarang (kalau ada duplikat data)
     */
    @Query("UPDATE cached_pelanggan_bendel SET kodeStatus = 1 WHERE bendelId = :bendelId AND nolangg = :nolangg")
    int markAsRead(String bendelId, String nolangg);

    /**
     * FALLBACK: tandai pelanggan sebagai sudah dibaca berdasarkan nolangg saja —
     * dipakai kalau bendelId tidak diketahui.
     * Akan mark SEMUA pelanggan dengan nolangg ini di SEMUA bendel.
     *
     * RETURN: jumlah row yang ter-update.
     */
    @Query("UPDATE cached_pelanggan_bendel SET kodeStatus = 1 WHERE nolangg = :nolangg AND kodeStatus = 0")
    int markAsReadByNolangg(String nolangg);

    @Query("DELETE FROM cached_pelanggan_bendel WHERE bendelId = :bendelId")
    void deleteByBendel(String bendelId);

    @Query("DELETE FROM cached_pelanggan_bendel")
    void deleteAll();
}