package co.id.pdamkotasmg.model.checkPelangganSudahDibaca;

import com.google.gson.annotations.SerializedName;

public class RlTrbacaItem{

    @SerializedName("dt")
    private String dt;

    @SerializedName("rl_petugas")
    private RlPetugas rlPetugas;

    @SerializedName("cabang")
    private String cabang;

    @SerializedName("m3")
    private String m3;

    @SerializedName("kt")
    private String kt;

    @SerializedName("kini")
    private String kini;

    @SerializedName("rl_dt_baca")
    private RlDtBaca rlDtBaca;

    @SerializedName("lalu")
    private String lalu;

    @SerializedName("tgl_baca")
    private String tglBaca;

    @SerializedName("nolangg")
    private String nolangg;

    @SerializedName("periode")
    private String periode;

    @SerializedName("petugas")
    private String petugas;

    public void setDt(String dt){
        this.dt = dt;
    }

    public String getDt(){
        return dt;
    }

    public void setRlPetugas(RlPetugas rlPetugas){
        this.rlPetugas = rlPetugas;
    }

    public RlPetugas getRlPetugas(){
        return rlPetugas;
    }

    public void setCabang(String cabang){
        this.cabang = cabang;
    }

    public String getCabang(){
        return cabang;
    }

    public void setM3(String m3){
        this.m3 = m3;
    }

    public String getM3(){
        return m3;
    }

    public void setKt(String kt){
        this.kt = kt;
    }

    public String getKt(){
        return kt;
    }

    public void setKini(String kini){
        this.kini = kini;
    }

    public String getKini(){
        return kini;
    }

    public void setRlDtBaca(RlDtBaca rlDtBaca){
        this.rlDtBaca = rlDtBaca;
    }

    public RlDtBaca getRlDtBaca(){
        return rlDtBaca;
    }

    public void setLalu(String lalu){
        this.lalu = lalu;
    }

    public String getLalu(){
        return lalu;
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

    public void setPetugas(String petugas){
        this.petugas = petugas;
    }

    public String getPetugas(){
        return petugas;
    }
}