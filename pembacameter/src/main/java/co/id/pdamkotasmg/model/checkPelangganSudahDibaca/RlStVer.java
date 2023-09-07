package co.id.pdamkotasmg.model.checkPelangganSudahDibaca;

import com.google.gson.annotations.SerializedName;

public class RlStVer{

    @SerializedName("kode")
    private String kode;

    @SerializedName("nm_stver")
    private String nmStver;

    public void setKode(String kode){
        this.kode = kode;
    }

    public String getKode(){
        return kode;
    }

    public void setNmStver(String nmStver){
        this.nmStver = nmStver;
    }

    public String getNmStver(){
        return nmStver;
    }
}