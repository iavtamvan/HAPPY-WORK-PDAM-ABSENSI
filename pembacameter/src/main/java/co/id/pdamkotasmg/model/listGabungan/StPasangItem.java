package co.id.pdamkotasmg.model.listGabungan;

import com.google.gson.annotations.SerializedName;

public class StPasangItem{

    @SerializedName("stpasang")
    private Integer stpasang;

    @SerializedName("nmstpasang")
    private String nmstpasang;

    public void setStpasang(Integer stpasang){
        this.stpasang = stpasang;
    }

    public Integer getStpasang(){
        return stpasang;
    }

    public void setNmstpasang(String nmstpasang){
        this.nmstpasang = nmstpasang;
    }

    public String getNmstpasang(){
        return nmstpasang;
    }
}