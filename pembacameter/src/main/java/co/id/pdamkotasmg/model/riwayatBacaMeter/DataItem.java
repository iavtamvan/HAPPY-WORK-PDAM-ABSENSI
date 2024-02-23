package co.id.pdamkotasmg.model.riwayatBacaMeter;

import com.google.gson.annotations.SerializedName;

public class DataItem{

    @SerializedName("dism")
    private String dism;

    @SerializedName("jam_baca")
    private String jamBaca;

    @SerializedName("dt")
    private String dt;

    @SerializedName("st")
    private String st;

    @SerializedName("kini")
    private String kini;

    @SerializedName("m3")
    private String m3;

    @SerializedName("stver")
    private String stver;

    @SerializedName("rl_pelanggan")
    private RlPelanggan rlPelanggan;

    @SerializedName("rl_st_meter")
    private RlStatusMeter rlStatusMeter;

    @SerializedName("rl_st_ver")
    private RlStVer rlStVer;

    @SerializedName("tgl_baca")
    private String tglBaca;

    @SerializedName("nolangg")
    private String nolangg;

    @SerializedName("periode")
    private String periode;

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public String getKini() {
        return kini;
    }

    public void setKini(String kini) {
        this.kini = kini;
    }

    public String getM3() {
        return m3;
    }

    public void setM3(String m3) {
        this.m3 = m3;
    }

    public RlStatusMeter getRlStatusMeter() {
        return rlStatusMeter;
    }

    public void setRlStatusMeter(RlStatusMeter rlStatusMeter) {
        this.rlStatusMeter = rlStatusMeter;
    }

    public RlStVer getRlStVer() {
        return rlStVer;
    }

    public void setRlStVer(RlStVer rlStVer) {
        this.rlStVer = rlStVer;
    }

    public String getStver() {
        return stver;
    }

    public void setStver(String stver) {
        this.stver = stver;
    }

    public String getDt() {
        return dt;
    }

    public void setDt(String dt) {
        this.dt = dt;
    }

    public void setDism(String dism){
        this.dism = dism;
    }

    public String getDism(){
        return dism;
    }

    public void setJamBaca(String jamBaca){
        this.jamBaca = jamBaca;
    }

    public String getJamBaca(){
        return jamBaca;
    }

    public void setRlPelanggan(RlPelanggan rlPelanggan){
        this.rlPelanggan = rlPelanggan;
    }

    public RlPelanggan getRlPelanggan(){
        return rlPelanggan;
    }

    public void setTglBaca(String tglBaca){
        this.tglBaca = tglBaca;
    }

    public String getTglBaca(){
        return tglBaca;
    }

    public void setNolangg(String nolangg){
        this.nolangg = nolangg;
    }

    public String getNolangg(){
        return nolangg;
    }

    public void setPeriode(String periode){
        this.periode = periode;
    }

    public String getPeriode(){
        return periode;
    }
}