package com.pdamsmg.pekerjaanteknik.model.post.postRoot;

import com.google.gson.annotations.SerializedName;

public class ListBarangLocalItem {

    @SerializedName("kd_brg")
    private String kdBrg;

    @SerializedName("satuan")
    private String satuan;

    @SerializedName("jml")
    private String jml;

    @SerializedName("nm_brg")
    private String nm_brg;

    @SerializedName("jns_brg")
    private String jns_brg;

    @SerializedName("merek")
    private String merek;

    public String getNm_brg() {
        return nm_brg;
    }

    public void setNm_brg(String nm_brg) {
        this.nm_brg = nm_brg;
    }

    public String getJns_brg() {
        return jns_brg;
    }

    public void setJns_brg(String jns_brg) {
        this.jns_brg = jns_brg;
    }

    public String getMerek() {
        return merek;
    }

    public void setMerek(String merek) {
        this.merek = merek;
    }

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