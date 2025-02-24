package co.id.pdamkotasmg.model.checkPelangganSudahDibaca;

import com.google.gson.annotations.SerializedName;

public class RlStMeter {

    @SerializedName("kode")
    private String kode;

    @SerializedName("status")
    private String status;

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}