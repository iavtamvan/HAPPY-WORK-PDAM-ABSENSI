package co.id.pdamkotasmg.local.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import co.id.pdamkotasmg.local.db.entity.CachedBendelEntity;

@Dao
public interface CachedBendelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrReplace(CachedBendelEntity entity);

    @Query("SELECT * FROM cached_bendel WHERE id = :id LIMIT 1")
    CachedBendelEntity getById(String id);

    @Query("SELECT * FROM cached_bendel ORDER BY lastSyncAt DESC")
    List<CachedBendelEntity> getAll();

    @Query("SELECT COUNT(*) FROM cached_bendel WHERE id = :id")
    int existsCount(String id);

    /**
     * Apakah cache untuk bendel ini sudah ada?
     */
    default boolean exists(String id) {
        return existsCount(id) > 0;
    }

    @Query("UPDATE cached_bendel SET totalUnread = :totalUnread WHERE id = :id")
    void updateUnreadCount(String id, int totalUnread);

    @Query("UPDATE cached_bendel SET totalUnread = totalUnread - 1 WHERE id = :id AND totalUnread > 0")
    void decrementUnreadCount(String id);

    @Query("DELETE FROM cached_bendel WHERE id = :id")
    void deleteById(String id);

    @Query("DELETE FROM cached_bendel")
    void deleteAll();

    /**
     * Ambil semua bendel yang totalUnread-nya 0 — kandidat cleanup.
     * Dipanggil saat tombol Sync ditekan (manual atau auto-sync).
     */
    @Query("SELECT * FROM cached_bendel WHERE totalUnread = 0")
    List<CachedBendelEntity> getEmptyBendels();
}