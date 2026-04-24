package co.id.pdamkotasmg.model.checkByNolangg;

import com.google.gson.annotations.SerializedName;

public class RlStatusTarif{

    @SerializedName("no")
    private Integer no;

    @SerializedName("tgl_aktif")
    private Object tglAktif;

    @SerializedName("nm_gol")
    private String nmGol;

    @SerializedName("kode")
    private String kode;

    @SerializedName("nm_tarif")
    private String nmTarif;

    @SerializedName("tarif_gr")
    private Object tarifGr;

    public void setNo(Integer no){
        this.no = no;
    }

    public Integer getNo(){
        return no;
    }

    public void setTglAktif(Object tglAktif){
        this.tglAktif = tglAktif;
    }

    public Object getTglAktif(){
        return tglAktif;
    }

    public void setNmGol(String nmGol){
        this.nmGol = nmGol;
    }

    public String getNmGol(){
        return nmGol;
    }

    public void setKode(String kode){
        this.kode = kode;
    }

    public String getKode(){
        return kode;
    }

    public void setNmTarif(String nmTarif){
        this.nmTarif = nmTarif;
    }

    public String getNmTarif(){
        return nmTarif;
    }

    public void setTarifGr(Object tarifGr){
        this.tarifGr = tarifGr;
    }

    public Object getTarifGr(){
        return tarifGr;
    }
}