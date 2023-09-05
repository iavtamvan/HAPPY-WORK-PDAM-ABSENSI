package co.id.pdamkotasmg.model.riwayatBacaMeter;

import com.google.gson.annotations.SerializedName;

public class DataItem{

    @SerializedName("dism")
    private String dism;

    @SerializedName("jam_baca")
    private String jamBaca;

    @SerializedName("rl_pelanggan")
    private RlPelanggan rlPelanggan;

    @SerializedName("tgl_baca")
    private String tglBaca;

    @SerializedName("nolangg")
    private String nolangg;

    @SerializedName("periode")
    private String periode;

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