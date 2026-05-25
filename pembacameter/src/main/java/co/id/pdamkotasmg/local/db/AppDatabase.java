package co.id.pdamkotasmg.local.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import co.id.pdamkotasmg.local.db.dao.CachedBendelDao;
import co.id.pdamkotasmg.local.db.dao.CachedPelangganBendelDao;
import co.id.pdamkotasmg.local.db.dao.PendingBacaanDao;
import co.id.pdamkotasmg.local.db.dao.PendingFotoDao;
import co.id.pdamkotasmg.local.db.entity.CachedBendelEntity;
import co.id.pdamkotasmg.local.db.entity.CachedPelangganBendelEntity;
import co.id.pdamkotasmg.local.db.entity.PendingBacaanEntity;
import co.id.pdamkotasmg.local.db.entity.PendingFotoEntity;

/**
 * Master Room database.
 *
 * VERSI 3 (Fase 6):
 *   - cached_bendel (+ field lastSyncAt)
 *   - cached_pelanggan_bendel
 *   - pending_bacaan
 *   - pending_foto
 *
 * Migration history:
 *   v1 → v2 : tambah pending_bacaan + pending_foto (Fase 3)
 *   v2 → v3 : tambah field lastSyncAt di cached_bendel (Fase 6)
 */
@Database(
        entities = {
                CachedBendelEntity.class,
                CachedPelangganBendelEntity.class,
                PendingBacaanEntity.class,
                PendingFotoEntity.class
        },
        version = 3,
        exportSchema = false
)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract CachedBendelDao cachedBendelDao();
    public abstract CachedPelangganBendelDao cachedPelangganBendelDao();
    public abstract PendingBacaanDao pendingBacaanDao();
    public abstract PendingFotoDao pendingFotoDao();

    private static final String DB_NAME = "pdam_offline.db";

    public static final ExecutorService databaseExecutor =
            Executors.newFixedThreadPool(4);

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    DB_NAME)
                            .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                            .fallbackToDestructiveMigration()
                            .addCallback(seedCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Migration v1 → v2: tambah tabel pending_bacaan dan pending_foto (Fase 3).
     */
    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL(
                    "CREATE TABLE IF NOT EXISTS `pending_bacaan` (" +
                            "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                            "`jenis` TEXT NOT NULL, " +
                            "`nolangg` TEXT NOT NULL, " +
                            "`periode` TEXT, " +
                            "`cabang` TEXT, " +
                            "`bendel` TEXT, " +
                            "`kini` TEXT, " +
                            "`kodeStatusMeter` TEXT, " +
                            "`keterangan` TEXT, " +
                            "`manometer` TEXT, " +
                            "`latitude` REAL, " +
                            "`longitude` REAL, " +
                            "`addressGps` TEXT, " +
                            "`actionCode` TEXT, " +
                            "`npp` TEXT, " +
                            "`versionInfo` TEXT, " +
                            "`createdAt` INTEGER NOT NULL, " +
                            "`syncStatus` INTEGER NOT NULL, " +
                            "`retryCount` INTEGER NOT NULL, " +
                            "`lastError` TEXT, " +
                            "`syncedAt` INTEGER NOT NULL)"
            );
            database.execSQL(
                    "CREATE INDEX IF NOT EXISTS `index_pending_bacaan_nolangg` ON `pending_bacaan` (`nolangg`)"
            );
            database.execSQL(
                    "CREATE INDEX IF NOT EXISTS `index_pending_bacaan_syncStatus` ON `pending_bacaan` (`syncStatus`)"
            );
            database.execSQL(
                    "CREATE INDEX IF NOT EXISTS `index_pending_bacaan_jenis` ON `pending_bacaan` (`jenis`)"
            );

            database.execSQL(
                    "CREATE TABLE IF NOT EXISTS `pending_foto` (" +
                            "`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                            "`pendingBacaanId` INTEGER NOT NULL, " +
                            "`jenis` TEXT NOT NULL, " +
                            "`localFilePath` TEXT NOT NULL, " +
                            "`serverPath` TEXT, " +
                            "`serverUrl` TEXT, " +
                            "`createdAt` INTEGER NOT NULL, " +
                            "`uploadStatus` INTEGER NOT NULL, " +
                            "`lastError` TEXT, " +
                            "FOREIGN KEY(`pendingBacaanId`) REFERENCES `pending_bacaan`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE)"
            );
            database.execSQL(
                    "CREATE INDEX IF NOT EXISTS `index_pending_foto_pendingBacaanId` ON `pending_foto` (`pendingBacaanId`)"
            );
        }
    };

    /**
     * Migration v2 → v3: tambah field lastSyncAt di cached_bendel (Fase 6).
     *
     * Cache lama tetap dipertahankan, default lastSyncAt = 0
     * (akan auto-update saat user buka bendel & fetch dari API).
     */
    public static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL(
                    "ALTER TABLE `cached_bendel` ADD COLUMN `lastSyncAt` INTEGER NOT NULL DEFAULT 0"
            );
        }
    };

    private static final Callback seedCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // No seed data
        }
    };

    public void clearAllData() {
        runInTransaction(() -> {
            pendingFotoDao().deleteByPendingBacaan(0L);
            cachedPelangganBendelDao().deleteAll();
            cachedBendelDao().deleteAll();
        });
    }
}