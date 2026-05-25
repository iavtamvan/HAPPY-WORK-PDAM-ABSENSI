package co.id.pdamkotasmg.local.sync;

/**
 * Event progress sync — di-emit oleh SyncManager ke UI listener.
 *
 * UI bisa update progress bar, status text, dll berdasarkan event ini.
 */
public class SyncProgressEvent {

    public enum Phase {
        STARTED,            // Sync baru dimulai
        UPLOADING_PHOTO,    // Sedang upload foto
        POSTING_DATA,       // Sedang POST data bacaan
        ITEM_SUCCESS,       // 1 item sukses
        ITEM_FAILED,        // 1 item gagal
        ALL_DONE            // Semua selesai
    }

    public final Phase phase;

    /**
     * Index item saat ini (1-based untuk display "1/10").
     */
    public final int current;

    /**
     * Total item yang akan di-sync.
     */
    public final int total;

    /**
     * Nolangg pelanggan yang sedang di-sync (untuk display "Mengirim 06371005...").
     * Bisa null untuk phase STARTED & ALL_DONE.
     */
    public final String currentNolangg;

    /**
     * Counter sukses & gagal sampai detik ini.
     */
    public final int successCount;
    public final int failedCount;

    /**
     * Error message kalau phase = ITEM_FAILED.
     */
    public final String errorMessage;

    public SyncProgressEvent(Phase phase, int current, int total,
                             String currentNolangg,
                             int successCount, int failedCount,
                             String errorMessage) {
        this.phase = phase;
        this.current = current;
        this.total = total;
        this.currentNolangg = currentNolangg;
        this.successCount = successCount;
        this.failedCount = failedCount;
        this.errorMessage = errorMessage;
    }

    // ============== FACTORY ==============

    public static SyncProgressEvent started(int total) {
        return new SyncProgressEvent(Phase.STARTED, 0, total, null, 0, 0, null);
    }

    public static SyncProgressEvent uploadingPhoto(int current, int total, String nolangg, int success, int failed) {
        return new SyncProgressEvent(Phase.UPLOADING_PHOTO, current, total, nolangg, success, failed, null);
    }

    public static SyncProgressEvent postingData(int current, int total, String nolangg, int success, int failed) {
        return new SyncProgressEvent(Phase.POSTING_DATA, current, total, nolangg, success, failed, null);
    }

    public static SyncProgressEvent itemSuccess(int current, int total, String nolangg, int success, int failed) {
        return new SyncProgressEvent(Phase.ITEM_SUCCESS, current, total, nolangg, success, failed, null);
    }

    public static SyncProgressEvent itemFailed(int current, int total, String nolangg, int success, int failed, String error) {
        return new SyncProgressEvent(Phase.ITEM_FAILED, current, total, nolangg, success, failed, error);
    }

    public static SyncProgressEvent allDone(int total, int success, int failed) {
        return new SyncProgressEvent(Phase.ALL_DONE, total, total, null, success, failed, null);
    }
}