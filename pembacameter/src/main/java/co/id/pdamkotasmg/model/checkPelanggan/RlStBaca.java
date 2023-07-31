package co.id.pdamkotasmg.model.checkPelanggan;

import com.google.gson.annotations.SerializedName;

public class RlStBaca {

    @SerializedName("nm_status")
    private String nm_status;

    @SerializedName("kode")
    private String kode;

    public String getNm_status() {
        return nm_status;
    }

    public void setNm_status(String nm_status) {
        this.nm_status = nm_status;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }
}