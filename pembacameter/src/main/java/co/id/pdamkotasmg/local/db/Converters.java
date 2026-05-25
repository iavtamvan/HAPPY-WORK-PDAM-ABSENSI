package co.id.pdamkotasmg.local.db;

import androidx.room.TypeConverter;

import java.util.Date;

/**
 * Type converters untuk Room — bantu Room map tipe Java yang tidak primitive
 * ke kolom SQLite.
 *
 * Daftarkan converter ini di @Database annotation:
 *   @TypeConverters({Converters.class})
 *   public abstract class AppDatabase extends RoomDatabase { ... }
 */
public class Converters {

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}