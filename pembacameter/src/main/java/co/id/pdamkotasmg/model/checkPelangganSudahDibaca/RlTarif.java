package co.id.pdamkotasmg.model.checkPelangganSudahDibaca;

import com.google.gson.annotations.SerializedName;

public class RlTarif{

    @SerializedName("nm_gol")
    private String nmGol;

    @SerializedName("kode")
    private String kode;

    @SerializedName("nm_tarif")
    private String nmTarif;

    public void setNmGol(String nmGol){
        this.nmGol = nmGol;
    }

    public String getNmGol(){
        return nmGol;
    }

    public void setKode(String kode){
        this.kode = kode;
    }

    public String getKode(){
        return kode;
    }

    public void setNmTarif(String nmTarif){
        this.nmTarif = nmTarif;
    }

    public String getNmTarif(){
        return nmTarif;
    }
}