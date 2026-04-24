package co.id.pdamkotasmg.model.listGabungan;

import com.google.gson.annotations.SerializedName;

public class StBacaItem{

    @SerializedName("kode")
    private Integer kode;

    @SerializedName("nm_status")
    private String nmStatus;

    public void setKode(Integer kode){
        this.kode = kode;
    }

    public Integer getKode(){
        return kode;
    }

    public void setNmStatus(String nmStatus){
        this.nmStatus = nmStatus;
    }

    public String getNmStatus(){
        return nmStatus;
    }
}