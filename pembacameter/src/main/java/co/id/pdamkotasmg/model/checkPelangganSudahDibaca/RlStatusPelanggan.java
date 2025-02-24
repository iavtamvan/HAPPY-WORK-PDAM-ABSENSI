package co.id.pdamkotasmg.model.checkPelangganSudahDibaca;

import com.google.gson.annotations.SerializedName;

public class RlStatusPelanggan{

    @SerializedName("nm_stplg")
    private String nmStplg;

    @SerializedName("kode")
    private String kode;

    public void setNmStplg(String nmStplg){
        this.nmStplg = nmStplg;
    }

    public String getNmStplg(){
        return nmStplg;
    }

    public void setKode(String kode){
        this.kode = kode;
    }

    public String getKode(){
        return kode;
    }
}