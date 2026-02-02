package com.pdamsmg.pekerjaanteknik.model.riwayatSpk.mandor;

import com.google.gson.annotations.SerializedName;

public class RlMBarang{

    @SerializedName("kd_jns1")
    private String kdJns1;

    @SerializedName("diameter")
    private String diameter;

    @SerializedName("kd_brg")
    private String kdBrg;

    @SerializedName("nm_brg")
    private String nmBrg;

    @SerializedName("satuan")
    private String satuan;

    @SerializedName("brg")
    private String brg;

    public void setKdJns1(String kdJns1){
        this.kdJns1 = kdJns1;
    }

    public String getKdJns1(){
        return kdJns1;
    }

    public void setDiameter(String diameter){
        this.diameter = diameter;
    }

    public String getDiameter(){
        return diameter;
    }

    public void setKdBrg(String kdBrg){
        this.kdBrg = kdBrg;
    }

    public String getKdBrg(){
        return kdBrg;
    }

    public void setNmBrg(String nmBrg){
        this.nmBrg = nmBrg;
    }

    public String getNmBrg(){
        return nmBrg;
    }

    public void setSatuan(String satuan){
        this.satuan = satuan;
    }

    public String getSatuan(){
        return satuan;
    }

    public void setBrg(String brg){
        this.brg = brg;
    }

    public String getBrg(){
        return brg;
    }
}