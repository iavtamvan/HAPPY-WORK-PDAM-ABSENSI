package co.id.pdamkotasmg.model.bendel;

import com.google.gson.annotations.SerializedName;

public class RlTrbacaItem{

    @SerializedName("m3")
    private String m3;

    @SerializedName("kini")
    private String kini;

    @SerializedName("nolangg")
    private String nolangg;

    @SerializedName("periode")
    private String periode;

    public void setM3(String m3){
        this.m3 = m3;
    }

    public String getM3(){
        return m3;
    }

    public void setKini(String kini){
        this.kini = kini;
    }

    public String getKini(){
        return kini;
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