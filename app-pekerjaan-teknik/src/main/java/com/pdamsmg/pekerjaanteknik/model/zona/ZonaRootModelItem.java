package com.pdamsmg.pekerjaanteknik.model.zona;

import com.google.gson.annotations.SerializedName;

public class ZonaRootModelItem{

    @SerializedName("SUB")
    private String sUB;

    @SerializedName("CABANG")
    private String cABANG;

    @SerializedName("DIAMETER")
    private Integer dIAMETER;

    @SerializedName("NM_ZONA")
    private String nMZONA;

    @SerializedName("KD")
    private String kD;

    @SerializedName("KET")
    private String kET;

    @SerializedName("KD_ZONA")
    private String kDZONA;

    public void setSUB(String sUB){
        this.sUB = sUB;
    }

    public String getSUB(){
        return sUB;
    }

    public void setCABANG(String cABANG){
        this.cABANG = cABANG;
    }

    public String getCABANG(){
        return cABANG;
    }

    public void setDIAMETER(Integer dIAMETER){
        this.dIAMETER = dIAMETER;
    }

    public Integer getDIAMETER(){
        return dIAMETER;
    }

    public void setNMZONA(String nMZONA){
        this.nMZONA = nMZONA;
    }

    public String getNMZONA(){
        return nMZONA;
    }

    public void setKD(String kD){
        this.kD = kD;
    }

    public String getKD(){
        return kD;
    }

    public void setKET(String kET){
        this.kET = kET;
    }

    public String getKET(){
        return kET;
    }

    public void setKDZONA(String kDZONA){
        this.kDZONA = kDZONA;
    }

    public String getKDZONA(){
        return kDZONA;
    }
}