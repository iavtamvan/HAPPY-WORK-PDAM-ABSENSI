package co.id.pdamkotasmg.model.bendel;

import com.google.gson.annotations.SerializedName;

public class RlDataBacaSekarang {

    @SerializedName("m3")
    private String m3;

    @SerializedName("kini")
    private String kini;

    @SerializedName("nolangg")
    private String nolangg;

    @SerializedName("periode")
    private String periode;

    @SerializedName("tgl_baca")
    private String tglBaca;

    @SerializedName("tandai")
    private String tandai;

    public String getTglBaca() {
        return tglBaca;
    }

    public void setTglBaca(String tglBaca) {
        this.tglBaca = tglBaca;
    }

    public String getTandai() {
        return tandai;
    }

    public void setTandai(String tandai) {
        this.tandai = tandai;
    }

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