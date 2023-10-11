package co.id.pdamkotasmg.model.riwayatBacaMeter;

import com.google.gson.annotations.SerializedName;

public class RlStVer {

    @SerializedName("kode")
    private String kode;

    @SerializedName("nm_stver")
    private String nmStver;

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getNmStver() {
        return nmStver;
    }

    public void setNmStver(String nmStver) {
        this.nmStver = nmStver;
    }
}