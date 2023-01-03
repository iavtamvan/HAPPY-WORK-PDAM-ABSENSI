package id.pdamkotasmg.pekerjaan_teknik_feature.model.listAll;

import com.google.gson.annotations.SerializedName;

public class StatusPenyelesaianItem {

    @SerializedName("kode")
    private String kode;

    @SerializedName("status")
    private String status;
    @SerializedName("kode_cc")
    private String kode_cc;
    @SerializedName("ket")
    private String ket;

    public String getKode_cc() {
        return kode_cc;
    }

    public void setKode_cc(String kode_cc) {
        this.kode_cc = kode_cc;
    }

    public String getKet() {
        return ket;
    }

    public void setKet(String ket) {
        this.ket = ket;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getKode() {
        return kode;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}