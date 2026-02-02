package com.pdamsmg.pekerjaanteknik.model.progressMandor;

import com.google.gson.annotations.SerializedName;

public class Data{

    @SerializedName("jml_bulan_selesai")
    private String jmlBulanSelesai;

    @SerializedName("name")
    private String name;

    @SerializedName("pekerjaan_npp")
    private String pekerjaanNpp;

    @SerializedName("jml_bulan_blm_selesai")
    private String jmlBulanBlmSelesai;

    @SerializedName("cab_bln_ini")
    private String cabBlnIni;

    @SerializedName("npp")
    private String npp;

    @SerializedName("satker")
    private String satker;

    @SerializedName("point")
    private String point;

    @SerializedName("monitoring_status_belum_selesai")
    private String monitoring_status_belum_selesai;

    @SerializedName("monitoring_status_selesai")
    private String monitoring_status_selesai;

    @SerializedName("monitoring_status_rehap")
    private String monitoring_status_rehap;

    @SerializedName("monitoring_jumlah_pekerjaan")
    private String monitoring_jumlah_pekerjaan;

    @SerializedName("monitoring_jumlah_pekerjaan_belum_diverifikasi")
    private String monitoring_jumlah_pekerjaan_belum_diverifikasi;

    @SerializedName("monitoring_jumlah_pekerjaan_diverifikasi")
    private String monitoring_jumlah_pekerjaan_diverifikasi;

    @SerializedName("monitoring_jumlah_pekerjaan_semua")
    private String monitoring_jumlah_pekerjaan_semua;

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getMonitoring_jumlah_pekerjaan_diverifikasi() {
        return monitoring_jumlah_pekerjaan_diverifikasi;
    }

    public void setMonitoring_jumlah_pekerjaan_diverifikasi(String monitoring_jumlah_pekerjaan_diverifikasi) {
        this.monitoring_jumlah_pekerjaan_diverifikasi = monitoring_jumlah_pekerjaan_diverifikasi;
    }

    public String getSatker() {
        return satker;
    }

    public void setSatker(String satker) {
        this.satker = satker;
    }

    public String getMonitoring_status_belum_selesai() {
        return monitoring_status_belum_selesai;
    }

    public void setMonitoring_status_belum_selesai(String monitoring_status_belum_selesai) {
        this.monitoring_status_belum_selesai = monitoring_status_belum_selesai;
    }

    public String getMonitoring_status_selesai() {
        return monitoring_status_selesai;
    }

    public void setMonitoring_status_selesai(String monitoring_status_selesai) {
        this.monitoring_status_selesai = monitoring_status_selesai;
    }

    public String getMonitoring_status_rehap() {
        return monitoring_status_rehap;
    }

    public void setMonitoring_status_rehap(String monitoring_status_rehap) {
        this.monitoring_status_rehap = monitoring_status_rehap;
    }

    public String getMonitoring_jumlah_pekerjaan() {
        return monitoring_jumlah_pekerjaan;
    }

    public void setMonitoring_jumlah_pekerjaan(String monitoring_jumlah_pekerjaan) {
        this.monitoring_jumlah_pekerjaan = monitoring_jumlah_pekerjaan;
    }

    public String getMonitoring_jumlah_pekerjaan_belum_diverifikasi() {
        return monitoring_jumlah_pekerjaan_belum_diverifikasi;
    }

    public void setMonitoring_jumlah_pekerjaan_belum_diverifikasi(String monitoring_jumlah_pekerjaan_belum_diverifikasi) {
        this.monitoring_jumlah_pekerjaan_belum_diverifikasi = monitoring_jumlah_pekerjaan_belum_diverifikasi;
    }

    public String getMonitoring_jumlah_pekerjaan_semua() {
        return monitoring_jumlah_pekerjaan_semua;
    }

    public void setMonitoring_jumlah_pekerjaan_semua(String monitoring_jumlah_pekerjaan_semua) {
        this.monitoring_jumlah_pekerjaan_semua = monitoring_jumlah_pekerjaan_semua;
    }

    public void setJmlBulanSelesai(String jmlBulanSelesai){
        this.jmlBulanSelesai = jmlBulanSelesai;
    }

    public String getJmlBulanSelesai(){
        return jmlBulanSelesai;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setPekerjaanNpp(String pekerjaanNpp){
        this.pekerjaanNpp = pekerjaanNpp;
    }

    public String getPekerjaanNpp(){
        return pekerjaanNpp;
    }

    public void setJmlBulanBlmSelesai(String jmlBulanBlmSelesai){
        this.jmlBulanBlmSelesai = jmlBulanBlmSelesai;
    }

    public String getJmlBulanBlmSelesai(){
        return jmlBulanBlmSelesai;
    }

    public void setCabBlnIni(String cabBlnIni){
        this.cabBlnIni = cabBlnIni;
    }

    public String getCabBlnIni(){
        return cabBlnIni;
    }

    public void setNpp(String npp){
        this.npp = npp;
    }

    public String getNpp(){
        return npp;
    }
}