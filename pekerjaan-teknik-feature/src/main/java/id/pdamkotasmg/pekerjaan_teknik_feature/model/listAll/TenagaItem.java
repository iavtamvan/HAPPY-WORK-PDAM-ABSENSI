package id.pdamkotasmg.pekerjaan_teknik_feature.model.listAll;

import com.google.gson.annotations.SerializedName;

public class TenagaItem{

    @SerializedName("kode")
    private String kode;

    @SerializedName("ket")
    private String ket;

    public void setKode(String kode){
        this.kode = kode;
    }

    public String getKode(){
        return kode;
    }

    public void setKet(String ket){
        this.ket = ket;
    }

    public String getKet(){
        return ket;
    }
}