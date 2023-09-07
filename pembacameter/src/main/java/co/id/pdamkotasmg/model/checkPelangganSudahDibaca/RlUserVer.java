package co.id.pdamkotasmg.model.checkPelangganSudahDibaca;

import com.google.gson.annotations.SerializedName;

public class RlUserVer{

    @SerializedName("kode")
    private String kode;

    @SerializedName("nm_petugas")
    private String nmPetugas;

    public void setKode(String kode){
        this.kode = kode;
    }

    public String getKode(){
        return kode;
    }

    public void setNmPetugas(String nmPetugas){
        this.nmPetugas = nmPetugas;
    }

    public String getNmPetugas(){
        return nmPetugas;
    }
}