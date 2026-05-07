package co.id.pdamkotasmg.local.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import co.id.pdamkotasmg.local.db.entity.CachedPelangganBendelEntity;

@Dao
public interface CachedPelangganBendelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(CachedPelangganBendelEntity entity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CachedPelangganBendelEntity> entities);

    @Update
    void update(CachedPelangganBendelEntity entity);

    @Query("SELECT * FROM cached_pelanggan_bendel WHERE bendelId = :bendelId ORDER BY id ASC")
    List<CachedPelangganBendelEntity> getByBendel(String bendelId);

    @Query("SELECT * FROM cached_pelanggan_bendel WHERE bendelId = :bendelId AND kodeStatus = 0 ORDER BY id ASC")
    List<CachedPelangganBendelEntity> getUnreadByBendel(String bendelId);

    @Query("SELECT * FROM cached_pelanggan_bendel WHERE nolangg = :nolangg LIMIT 1")
    CachedPelangganBendelEntity findByNolangg(String nolangg);

    /**
     * Cari pelanggan dalam bendel tertentu — kombinasi bendel + nolangg unik.
     */
    @Query("SELECT * FROM cached_pelanggan_bendel WHERE bendelId = :bendelId AND nolangg = :nolangg LIMIT 1")
    CachedPelangganBendelEntity findInBendel(String bendelId, String nolangg);

    @Query("SELECT COUNT(*) FROM cached_pelanggan_bendel WHERE bendelId = :bendelId")
    int countByBendel(String bendelId);

    @Query("SELECT COUNT(*) FROM cached_pelanggan_bendel WHERE bendelId = :bendelId AND kodeStatus = 0")
    int countUnreadByBendel(String bendelId);

    /**
     * Tandai pelanggan sebagai sudah dibaca (kodeStatus = 1).
     * Dipakai setelah user submit bacaan supaya hilang dari list "belum dibaca".
     */
    @Query("UPDATE cached_pelanggan_bendel SET kodeStatus = 1 WHERE bendelId = :bendelId AND nolangg = :nolangg")
    void markAsRead(String bendelId, String nolangg);

    /**
     * Toggle user-flag bookmark.
     */
    @Query("UPDATE cached_pelanggan_bendel SET userTandai = :tandai WHERE bendelId = :bendelId AND nolangg = :nolangg")
    void setUserTandai(String bendelId, String nolangg, boolean tandai);

    @Query("DELETE FROM cached_pelanggan_bendel WHERE bendelId = :bendelId")
    void deleteByBendel(String bendelId);

    @Query("DELETE FROM cached_pelanggan_bendel")
    void deleteAll();
}