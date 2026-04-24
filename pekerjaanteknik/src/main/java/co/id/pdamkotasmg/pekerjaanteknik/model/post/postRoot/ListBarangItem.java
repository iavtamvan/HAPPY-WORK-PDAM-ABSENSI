package co.id.pdamkotasmg.pekerjaanteknik.model.post.postRoot;

import com.google.gson.annotations.SerializedName;

public class ListBarangItem{

    @SerializedName("kd_brg")
    private String kdBrg;

    @SerializedName("satuan")
    private String satuan;

    @SerializedName("jml")
    private String jml;

    public void setKdBrg(String kdBrg){
        this.kdBrg = kdBrg;
    }

    public String getKdBrg(){
        return kdBrg;
    }

    public void setSatuan(String satuan){
        this.satuan = satuan;
    }

    public String getSatuan(){
        return satuan;
    }

    public void setJml(String jml){
        this.jml = jml;
    }

    public String getJml(){
        return jml;
    }
}