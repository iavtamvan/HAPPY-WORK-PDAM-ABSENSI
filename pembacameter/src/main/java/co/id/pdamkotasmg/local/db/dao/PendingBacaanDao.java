package co.id.pdamkotasmg.local.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import co.id.pdamkotasmg.local.db.entity.PendingBacaanEntity;

@Dao
public interface PendingBacaanDao {

    /**
     * Insert pending bacaan baru.
     * @return generated ID — wajib disimpan untuk relasi dengan PendingFotoEntity.
     */
    @Insert
    long insert(PendingBacaanEntity entity);

    @Update
    void update(PendingBacaanEntity entity);

    @Query("SELECT * FROM pending_bacaan WHERE id = :id LIMIT 1")
    PendingBacaanEntity getById(long id);

    @Query("SELECT * FROM pending_bacaan ORDER BY createdAt DESC")
    List<PendingBacaanEntity> getAll();

    /**
     * Pending yang BELUM sync sukses (status != 2).
     * Dipakai di Sync engine untuk loop processing.
     */
    @Query("SELECT * FROM pending_bacaan WHERE syncStatus != 2 ORDER BY createdAt ASC")
    List<PendingBacaanEntity> getAllUnsynced();

    @Query("SELECT * FROM pending_bacaan WHERE jenis = :jenis ORDER BY createdAt DESC")
    List<PendingBacaanEntity> getByJenis(String jenis);

    @Query("SELECT * FROM pending_bacaan WHERE nolangg = :nolangg ORDER BY createdAt DESC")
    List<PendingBacaanEntity> getByNolangg(String nolangg);

    @Query("SELECT COUNT(*) FROM pending_bacaan WHERE syncStatus != 2")
    int countUnsynced();

    /**
     * Untuk badge di menu home (Fase 4): hitung pending per jenis.
     */
    @Query("SELECT COUNT(*) FROM pending_bacaan WHERE jenis = :jenis AND syncStatus != 2")
    int countUnsyncedByJenis(String jenis);

    @Query("UPDATE pending_bacaan SET syncStatus = :status WHERE id = :id")
    void updateSyncStatus(long id, int status);

    @Query("UPDATE pending_bacaan SET syncStatus = :status, lastError = :error, retryCount = retryCount + 1 WHERE id = :id")
    void markFailed(long id, int status, String error);

    @Query("UPDATE pending_bacaan SET syncStatus = 2, syncedAt = :timestamp, lastError = NULL WHERE id = :id")
    void markSuccess(long id, long timestamp);

    @Query("DELETE FROM pending_bacaan WHERE id = :id")
    void deleteById(long id);

    /**
     * Cleanup: hapus pending yang sudah sukses sync lebih dari X hari.
     */
    @Query("DELETE FROM pending_bacaan WHERE syncStatus = 2 AND syncedAt < :olderThanMillis")
    int deleteSyncedOlderThan(long olderThanMillis);
}