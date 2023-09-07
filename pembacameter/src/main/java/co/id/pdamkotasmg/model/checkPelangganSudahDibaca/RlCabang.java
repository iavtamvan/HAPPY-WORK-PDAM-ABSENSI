package co.id.pdamkotasmg.model.checkPelangganSudahDibaca;

import com.google.gson.annotations.SerializedName;

public class RlCabang{

    @SerializedName("kode")
    private String kode;

    @SerializedName("nm_cabang")
    private String nmCabang;

    public void setKode(String kode){
        this.kode = kode;
    }

    public String getKode(){
        return kode;
    }

    public void setNmCabang(String nmCabang){
        this.nmCabang = nmCabang;
    }

    public String getNmCabang(){
        return nmCabang;
    }
}