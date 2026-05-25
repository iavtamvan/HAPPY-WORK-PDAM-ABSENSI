package co.id.pdamkotasmg.local.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import co.id.pdamkotasmg.local.db.entity.PendingFotoEntity;

@Dao
public interface PendingFotoDao {

    @Insert
    long insert(PendingFotoEntity entity);

    @Update
    void update(PendingFotoEntity entity);

    @Query("SELECT * FROM pending_foto WHERE pendingBacaanId = :pendingBacaanId")
    List<PendingFotoEntity> getByPendingBacaan(long pendingBacaanId);

    @Query("SELECT * FROM pending_foto WHERE pendingBacaanId = :pendingBacaanId AND jenis = :jenis LIMIT 1")
    PendingFotoEntity getByPendingBacaanAndJenis(long pendingBacaanId, String jenis);

    @Query("UPDATE pending_foto SET uploadStatus = :status, serverPath = :serverPath, serverUrl = :serverUrl, lastError = NULL WHERE id = :id")
    void markUploaded(long id, int status, String serverPath, String serverUrl);

    @Query("UPDATE pending_foto SET uploadStatus = :status, lastError = :error WHERE id = :id")
    void markFailed(long id, int status, String error);

    @Query("DELETE FROM pending_foto WHERE id = :id")
    void deleteById(long id);

    @Query("DELETE FROM pending_foto WHERE pendingBacaanId = :pendingBacaanId")
    void deleteByPendingBacaan(long pendingBacaanId);
}