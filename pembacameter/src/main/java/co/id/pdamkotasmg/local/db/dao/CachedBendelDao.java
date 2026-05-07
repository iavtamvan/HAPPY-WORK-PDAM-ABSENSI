package co.id.pdamkotasmg.local.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import co.id.pdamkotasmg.local.db.entity.CachedBendelEntity;

@Dao
public interface CachedBendelDao {

    /**
     * Insert atau replace bendel cache.
     * onConflict=REPLACE — kalau bendel dengan ID sama sudah ada, overwrite.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrReplace(CachedBendelEntity bendel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOrReplaceAll(List<CachedBendelEntity> bendels);

    @Query("SELECT * FROM cached_bendel WHERE id = :id LIMIT 1")
    CachedBendelEntity getById(String id);

    @Query("SELECT * FROM cached_bendel WHERE codeBendel = :code AND periode = :periode AND cabang = :cabang LIMIT 1")
    CachedBendelEntity findByCodes(String code, String periode, String cabang);

    @Query("SELECT * FROM cached_bendel ORDER BY lastFetchedAt DESC")
    List<CachedBendelEntity> getAllOrderedByRecency();

    @Query("DELETE FROM cached_bendel WHERE id = :id")
    void deleteById(String id);

    @Query("DELETE FROM cached_bendel")
    void deleteAll();

    @Query("SELECT COUNT(*) FROM cached_bendel")
    int count();
}