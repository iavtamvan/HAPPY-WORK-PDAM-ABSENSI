package com.pdamsmg.pekerjaanteknik.model.listAll;

import com.google.gson.annotations.SerializedName;

public class JenisPekerjaanItem{

    @SerializedName("kd")
    private String kd;

    @SerializedName("nm_jns")
    private String nmJns;

    @SerializedName("kd_jns")
    private String kd_jns;

    public String getKd_jns() {
        return kd_jns;
    }

    public void setKd_jns(String kd_jns) {
        this.kd_jns = kd_jns;
    }

    public void setKd(String kd){
        this.kd = kd;
    }

    public String getKd(){
        return kd;
    }

    public void setNmJns(String nmJns){
        this.nmJns = nmJns;
    }

    public String getNmJns(){
        return nmJns;
    }
}