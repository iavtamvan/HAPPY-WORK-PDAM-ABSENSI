package com.pdamsmg.pekerjaanteknik.model.tagihan;

import com.google.gson.annotations.SerializedName;

public class Rutin{

    @SerializedName("tempat_bayar")
    private String tempatBayar;

    @SerializedName("pemakaian")
    private String pemakaian;

    @SerializedName("tarif")
    private String tarif;

    @SerializedName("tanggal_bayar")
    private String tanggalBayar;

    @SerializedName("tagihan")
    private String tagihan;

    @SerializedName("tahun")
    private String tahun;

    @SerializedName("bulan_name")
    private String bulanName;

    @SerializedName("is_terbayar")
    private boolean isTerbayar;

    @SerializedName("bulan")
    private String bulan;

    @SerializedName("periode")
    private String periode;

    @SerializedName("status_rekening")
    private String statusRekening;

    public void setTempatBayar(String tempatBayar){
        this.tempatBayar = tempatBayar;
    }

    public String getTempatBayar(){
        return tempatBayar;
    }

    public void setPemakaian(String pemakaian){
        this.pemakaian = pemakaian;
    }

    public String getPemakaian(){
        return pemakaian;
    }

    public void setTarif(String tarif){
        this.tarif = tarif;
    }

    public String getTarif(){
        return tarif;
    }

    public void setTanggalBayar(String tanggalBayar){
        this.tanggalBayar = tanggalBayar;
    }

    public String getTanggalBayar(){
        return tanggalBayar;
    }

    public void setTagihan(String tagihan){
        this.tagihan = tagihan;
    }

    public String getTagihan(){
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

    public void setBulan(String bulan){
        this.bulan = bulan;
    }

    public String getBulan(){
        return bulan;
    }

    public void setPeriode(String periode){
        this.periode = periode;
    }

    public String getPeriode(){
        return periode;
    }

    public void setStatusRekening(String statusRekening){
        this.statusRekening = statusRekening;
    }

    public String getStatusRekening(){
        return statusRekening;
    }
}