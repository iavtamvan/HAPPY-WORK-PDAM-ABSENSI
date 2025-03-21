package co.id.pdamkotasmg.pekerjaanteknik.model.callCenter.mastCCSatker;

import com.google.gson.annotations.SerializedName;

public class MCcSatkerItem{

    @SerializedName("idsatker_cbg")
    private String idsatkerCbg;

    @SerializedName("nama_cc")
    private String namaCc;

    @SerializedName("id")
    private Integer id;

    public void setIdsatkerCbg(String idsatkerCbg){
        this.idsatkerCbg = idsatkerCbg;
    }

    public String getIdsatkerCbg(){
        return idsatkerCbg;
    }

    public void setNamaCc(String namaCc){
        this.namaCc = namaCc;
    }

    public String getNamaCc(){
        return namaCc;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public Integer getId(){
        return id;
    }
}