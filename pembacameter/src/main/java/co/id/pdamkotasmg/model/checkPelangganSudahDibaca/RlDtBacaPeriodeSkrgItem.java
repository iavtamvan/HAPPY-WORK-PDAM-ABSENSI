package co.id.pdamkotasmg.model.checkPelangganSudahDibaca;

import com.google.gson.annotations.SerializedName;

public class RlDtBacaPeriodeSkrgItem{

    @SerializedName("dt")
    private Integer dt;

    @SerializedName("rl_petugas")
    private Object rlPetugas;

    @SerializedName("cabang")
    private String cabang;

    @SerializedName("m3")
    private Integer m3;

    @SerializedName("kt")
    private Object kt;

    @SerializedName("kini")
    private Integer kini;

    @SerializedName("rl_dt_baca")
    private RlDtBaca rlDtBaca;

    @SerializedName("lalu")
    private Integer lalu;

    @SerializedName("tgl_baca")
    private Object tglBaca;

    @SerializedName("nolangg")
    private String nolangg;

    @SerializedName("periode")
    private String periode;

    @SerializedName("petugas")
    private Object petugas;

    public void setDt(Integer dt){
        this.dt = dt;
    }

    public Integer getDt(){
        return dt;
    }

    public void setRlPetugas(Object rlPetugas){
        this.rlPetugas = rlPetugas;
    }

    public Object getRlPetugas(){
        return rlPetugas;
    }

    public void setCabang(String cabang){
        this.cabang = cabang;
    }

    public String getCabang(){
        return cabang;
    }

    public void setM3(Integer m3){
        this.m3 = m3;
    }

    public Integer getM3(){
        return m3;
    }

    public void setKt(Object kt){
        this.kt = kt;
    }

    public Object getKt(){
        return kt;
    }

    public void setKini(Integer kini){
        this.kini = kini;
    }

    public Integer getKini(){
        return kini;
    }

    public void setRlDtBaca(RlDtBaca rlDtBaca){
        this.rlDtBaca = rlDtBaca;
    }

    public RlDtBaca getRlDtBaca(){
        return rlDtBaca;
    }

    public void setLalu(Integer lalu){
        this.lalu = lalu;
    }

    public Integer getLalu(){
        return lalu;
    }

    public void setTglBaca(Object tglBaca){
        this.tglBaca = tglBaca;
    }

    public Object getTglBaca(){
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

    public void setPetugas(Object petugas){
        this.petugas = petugas;
    }

    public Object getPetugas(){
        return petugas;
    }
}