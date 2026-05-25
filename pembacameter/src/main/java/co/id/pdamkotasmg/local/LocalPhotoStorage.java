package co.id.pdamkotasmg.local;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Helper untuk menyimpan foto pending ke private app storage
 * (filesDir/pending_photos/).
 *
 * Kenapa di filesDir, bukan cacheDir?
 *   - cacheDir bisa dibersihkan OS kapan saja → foto pending hilang sebelum sync
 *   - filesDir hanya dihapus saat user uninstall app atau "Clear Storage"
 *
 * Kenapa COPY foto, bukan pakai path original?
 *   - File kamera/galeri user bisa kehapus saat user bersihkan galeri
 *   - Kalau file hilang sebelum sync, payload upload gagal
 *   - Copy ke private storage = lebih aman
 *
 * Strategi naming file:
 *   pending_<timestamp>_<nolangg>_<jenis>.<ext>
 *   contoh: pending_1730000000000_06371005_foto_meter.webp
 *
 * Cleanup: file dihapus setelah sync sukses (di sync engine Fase 4).
 */
public class LocalPhotoStorage {

    private static final String TAG = "LocalPhotoStorage";
    private static final String FOLDER_NAME = "pending_photos";

    private final Context appContext;

    public LocalPhotoStorage(Context context) {
        this.appContext = context.getApplicationContext();
    }

    /**
     * Folder root tempat semua foto pending disimpan.
     * Auto-create kalau belum ada.
     */
    public File getStorageDir() {
        File dir = new File(appContext.getFilesDir(), FOLDER_NAME);
        if (!dir.exists()) {
            boolean created = dir.mkdirs();
            if (!created && !dir.exists()) {
                Log.e(TAG, "Failed to create pending_photos directory");
            }
        }
        return dir;
    }

    /**
     * Copy foto dari sourcePath ke private storage.
     *
     * @param sourceFile foto asli (biasanya yang sudah di-compress oleh Compressor lib)
     * @param nolangg    untuk naming
     * @param jenis      e.g. "foto_meter", "foto_manometer"
     * @return File baru di private storage, atau null kalau gagal
     */
    public File copyToPendingStorage(File sourceFile, String nolangg, String jenis) {
        if (sourceFile == null || !sourceFile.exists()) {
            Log.e(TAG, "copyToPendingStorage: source file null or not exists");
            return null;
        }

        try {
            File destDir = getStorageDir();
            String ext = getFileExtension(sourceFile.getName());
            String fileName = "pending_"
                    + System.currentTimeMillis()
                    + "_"
                    + safeForFilename(nolangg)
                    + "_"
                    + safeForFilename(jenis)
                    + ext;
            File destFile = new File(destDir, fileName);

            copyFile(sourceFile, destFile);

            Log.d(TAG, "Copied photo to: " + destFile.getAbsolutePath()
                    + " (" + destFile.length() + " bytes)");
            return destFile;
        } catch (Exception e) {
            Log.e(TAG, "copyToPendingStorage failed", e);
            return null;
        }
    }

    /**
     * Hapus 1 file pending. Dipakai setelah upload sukses di sync engine.
     */
    public boolean deletePending(String absolutePath) {
        if (absolutePath == null) return false;
        File f = new File(absolutePath);
        if (!f.exists()) return true; // already gone, OK
        boolean deleted = f.delete();
        if (!deleted) Log.w(TAG, "Failed to delete: " + absolutePath);
        return deleted;
    }

    /**
     * Total ukuran folder pending (untuk debug / display di Settings).
     */
    public long getTotalStorageSizeBytes() {
        File dir = getStorageDir();
        if (!dir.exists()) return 0;
        long total = 0;
        File[] files = dir.listFiles();
        if (files == null) return 0;
        for (File f : files) {
            if (f.isFile()) total += f.length();
        }
        return total;
    }

    /**
     * Bersihkan semua foto orphan — yang ada di folder tapi tidak ada referensinya
     * di database. Dipakai sesekali untuk maintenance.
     *
     * @param referencedPaths set of localFilePath yang ada di tabel pending_foto
     * @return jumlah file orphan yang dihapus
     */
    public int deleteOrphanFiles(java.util.Set<String> referencedPaths) {
        File dir = getStorageDir();
        if (!dir.exists()) return 0;
        File[] files = dir.listFiles();
        if (files == null) return 0;

        int deleted = 0;
        for (File f : files) {
            if (f.isFile() && !referencedPaths.contains(f.getAbsolutePath())) {
                if (f.delete()) deleted++;
            }
        }
        Log.d(TAG, "Deleted " + deleted + " orphan files");
        return deleted;
    }

    // ============== HELPERS ==============

    private static void copyFile(File source, File dest) throws IOException {
        try (FileInputStream fis = new FileInputStream(source);
             FileOutputStream fos = new FileOutputStream(dest);
             FileChannel src = fis.getChannel();
             FileChannel dst = fos.getChannel()) {
            dst.transferFrom(src, 0, src.size());
        }
    }

    private static String getFileExtension(String fileName) {
        if (fileName == null) return "";
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex < 0 || dotIndex == fileName.length() - 1) return "";
        return fileName.substring(dotIndex);
    }

    private static String safeForFilename(String s) {
        if (s == null) return "unknown";
        return s.replaceAll("[^a-zA-Z0-9_-]", "_");
    }
}