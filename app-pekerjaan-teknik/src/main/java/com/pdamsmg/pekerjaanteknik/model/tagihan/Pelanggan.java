package com.pdamsmg.pekerjaanteknik.model.tagihan;

import com.google.gson.annotations.SerializedName;

public class Pelanggan{

    @SerializedName("nama")
    private String nama;

    @SerializedName("nolangg")
    private String nolangg;

    @SerializedName("alamat")
    private String alamat;

    @SerializedName("status_ket")
    private String status_ket;

    @SerializedName("kelurahan")
    private String kelurahan;

    @SerializedName("kecamatan")
    private String kecamatan;

    @SerializedName("cabang")
    private String cabang;

    public String getCabang() {
        return cabang;
    }

    public void setCabang(String cabang) {
        this.cabang = cabang;
    }

    public String getStatus_ket() {
        return status_ket;
    }

    public void setStatus_ket(String status_ket) {
        this.status_ket = status_ket;
    }

    public String getKelurahan() {
        return kelurahan;
    }

    public void setKelurahan(String kelurahan) {
        this.kelurahan = kelurahan;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public void setNama(String nama){
        this.nama = nama;
    }

    public String getNama(){
        return nama;
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
}