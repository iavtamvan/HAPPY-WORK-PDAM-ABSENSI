package com.pdamkotasmg.goodday.fitur.menuLainnya.profilePelanggan.model;

import com.google.gson.annotations.SerializedName;

public class FlatItem{

    @SerializedName("pemakaian")
    private String pemakaian;

    @SerializedName("tagihan")
    private int tagihan;

    @SerializedName("tahun")
    private String tahun;

    @SerializedName("bulan_name")
    private String bulanName;

    @SerializedName("is_terbayar")
    private boolean isTerbayar;

    @SerializedName("bulan")
    private int bulan;

    @SerializedName("periode")
    private int periode;

    public void setPemakaian(String pemakaian){
        this.pemakaian = pemakaian;
    }

    public String getPemakaian(){
        return pemakaian;
    }

    public void setTagihan(int tagihan){
        this.tagihan = tagihan;
    }

    public int getTagihan(){
        return tagihan;
    }

    public void setTahun(String tahun){
        this.tahun = tahun;
    }

    public String getTahun(){
        return tahun;
    }

    public void setBulanName(String bulanName){
        this.bulanName = bulanName;
    }

    public String getBulanName(){
        return bulanName;
    }

    public void setIsTerbayar(boolean isTerbayar){
        this.isTerbayar = isTerbayar;
    }

    public boolean isIsTerbayar(){
        return isTerbayar;
    }

    public void setBulan(int bulan){
        this.bulan = bulan;
    }

    public int getBulan(){
        return bulan;
    }

    public void setPeriode(int periode){
        this.periode = periode;
    }

    public int getPeriode(){
        return periode;
    }
}