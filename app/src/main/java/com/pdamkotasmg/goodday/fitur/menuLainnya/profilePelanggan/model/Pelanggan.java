package com.pdamkotasmg.goodday.fitur.menuLainnya.profilePelanggan.model;

import com.google.gson.annotations.SerializedName;

public class Pelanggan{

    @SerializedName("status_ket")
    private String statusKet;

    @SerializedName("cabang")
    private String cabang;

    @SerializedName("telepon")
    private String telepon;

    @SerializedName("kelurahan")
    private String kelurahan;

    @SerializedName("nolangg")
    private String nolangg;

    @SerializedName("alamat")
    private String alamat;

    @SerializedName("dism")
    private String dism;

    @SerializedName("tarif")
    private String tarif;

    @SerializedName("foto_rumah")
    private String fotoRumah;

    @SerializedName("nama")
    private String nama;

    @SerializedName("tglbuka")
    private String tglbuka;

    @SerializedName("tglmeter")
    private String tglmeter;

    @SerializedName("tglpasang")
    private String tglpasang;

    @SerializedName("kecamatan")
    private String kecamatan;

    @SerializedName("tgltutup")
    private String tgltutup;

    @SerializedName("status")
    private String status;

    public void setStatusKet(String statusKet){
        this.statusKet = statusKet;
    }

    public String getStatusKet(){
        return statusKet;
    }

    public void setCabang(String cabang){
        this.cabang = cabang;
    }

    public String getCabang(){
        return cabang;
    }

    public void setTelepon(String telepon){
        this.telepon = telepon;
    }

    public String getTelepon(){
        return telepon;
    }

    public void setKelurahan(String kelurahan){
        this.kelurahan = kelurahan;
    }

    public String getKelurahan(){
        return kelurahan;
    }

    public void setNolangg(String nolangg){
        this.nolangg = nolangg;
    }

    public String getNolangg(){
        return nolangg;
    }

    public void setAlamat(String alamat){
        this.alamat = alamat;
    }

    public String getAlamat(){
        return alamat;
    }

    public void setDism(String dism){
        this.dism = dism;
    }

    public String getDism(){
        return dism;
    }

    public void setTarif(String tarif){
        this.tarif = tarif;
    }

    public String getTarif(){
        return tarif;
    }

    public void setFotoRumah(String fotoRumah){
        this.fotoRumah = fotoRumah;
    }

    public String getFotoRumah(){
        return fotoRumah;
    }

    public void setNama(String nama){
        this.nama = nama;
    }

    public String getNama(){
        return nama;
    }

    public void setTglbuka(String tglbuka){
        this.tglbuka = tglbuka;
    }

    public String getTglbuka(){
        return tglbuka;
    }

    public void setTglmeter(String tglmeter){
        this.tglmeter = tglmeter;
    }

    public String getTglmeter(){
        return tglmeter;
    }

    public void setTglpasang(String tglpasang){
        this.tglpasang = tglpasang;
    }

    public String getTglpasang(){
        return tglpasang;
    }

    public void setKecamatan(String kecamatan){
        this.kecamatan = kecamatan;
    }

    public String getKecamatan(){
        return kecamatan;
    }

    public void setTgltutup(String tgltutup){
        this.tgltutup = tgltutup;
    }

    public String getTgltutup(){
        return tgltutup;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }
}