package co.id.pdamkotasmg.model.listGabungan;

import com.google.gson.annotations.SerializedName;

public class BagianItem{

    @SerializedName("kode")
    private String kode;

    @SerializedName("nm_bag")
    private String nmBag;

    public void setKode(String kode){
        this.kode = kode;
    }

    public String getKode(){
        return kode;
    }

    public void setNmBag(String nmBag){
        this.nmBag = nmBag;
    }

    public String getNmBag(){
        return nmBag;
    }
}