package co.id.pdamkotasmg.model.bendel.bendelNext;

import com.google.gson.annotations.SerializedName;

public class RlDtBacaPeriodeSkrgItem{

    @SerializedName("m3")
    private Integer m3;

    @SerializedName("kini")
    private Integer kini;

    @SerializedName("tgl_baca")
    private Object tglBaca;

    @SerializedName("nolangg")
    private String nolangg;

    @SerializedName("periode")
    private String periode;

    public void setM3(Integer m3){
        this.m3 = m3;
    }

    public Integer getM3(){
        return m3;
    }

    public void setKini(Integer kini){
        this.kini = kini;
    }

    public Integer getKini(){
        return kini;
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
}