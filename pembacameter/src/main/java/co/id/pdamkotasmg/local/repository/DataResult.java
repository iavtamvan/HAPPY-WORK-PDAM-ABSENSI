package co.id.pdamkotasmg.local.repository;

import java.util.List;

import co.id.pdamkotasmg.model.bendel.DataItem;

/**
 * Wrapper hasil dari {@link BendelRepository getBendel}.
 *
 * Memberitahu UI:
 *   - data-nya ada (success / cache / empty)
 *   - sumber data: dari network atau dari cache lokal
 *   - kalau error, kenapa
 *
 * Contoh penggunaan di Activity:
 *
 *   bendelRepository.getBendel(token, codeBendel, "", new BendelRepository.Callback() {
 *       public void onResult(DataResult result) {
 *           runOnUiThread(() -> {
 *               if (result.isSuccess()) {
 *                   updateList(result.getData());
 *                   if (result.isFromCache()) showOfflineBanner();
 *               } else {
 *                   showError(result.getErrorMessage());
 *               }
 *           });
 *       }
 *   });
 */
public class DataResult {

    public enum Source {
        NETWORK,    // segar dari API
        CACHE,      // dari Room (mode offline atau API gagal)
        ERROR       // gagal total (no cache, no network)
    }

    private final Source source;
    private final List<DataItem> data;
    private final String errorMessage;
    private final Throwable error;

    private DataResult(Source source, List<DataItem> data, String errorMessage, Throwable error) {
        this.source = source;
        this.data = data;
        this.errorMessage = errorMessage;
        this.error = error;
    }

    // ============== FACTORY METHODS ==============

    public static DataResult fromNetwork(List<DataItem> data) {
        return new DataResult(Source.NETWORK, data, null, null);
    }

    public static DataResult fromCache(List<DataItem> data) {
        return new DataResult(Source.CACHE, data, null, null);
    }

    public static DataResult error(String message, Throwable t) {
        return new DataResult(Source.ERROR, null, message, t);
    }

    // ============== GETTERS ==============

    public Source getSource() {
        return source;
    }

    public boolean isSuccess() {
        return source != Source.ERROR;
    }

    public boolean isFromCache() {
        return source == Source.CACHE;
    }

    public boolean isFromNetwork() {
        return source == Source.NETWORK;
    }

    public List<DataItem> getData() {
        return data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Throwable getError() {
        return error;
    }
}