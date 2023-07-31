package co.id.pdamkotasmg.model.listGabungan;

import com.google.gson.annotations.SerializedName;

public class StVerItem{

    @SerializedName("kode")
    private Integer kode;

    @SerializedName("nm_stver")
    private String nmStver;

    public void setKode(Integer kode){
        this.kode = kode;
    }

    public Integer getKode(){
        return kode;
    }

    public void setNmStver(String nmStver){
        this.nmStver = nmStver;
    }

    public String getNmStver(){
        return nmStver;
    }
}