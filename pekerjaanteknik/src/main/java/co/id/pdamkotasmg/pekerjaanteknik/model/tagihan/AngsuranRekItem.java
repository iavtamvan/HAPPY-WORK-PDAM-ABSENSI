package co.id.pdamkotasmg.pekerjaanteknik.model.tagihan;

import com.google.gson.annotations.SerializedName;

public class AngsuranRekItem{

    @SerializedName("keterangan")
    private String keterangan;

    @SerializedName("tagihan")
    private String tagihan;

    @SerializedName("tahun")
    private String tahun;

    @SerializedName("angsuran_ke")
    private String angsuranKe;

    @SerializedName("bulan_name")
    private String bulanName;

    @SerializedName("is_terbayar")
    private boolean isTerbayar;

    @SerializedName("periode_rek_angsur")
    private Object periodeRekAngsur;

    @SerializedName("bulan")
    private int bulan;

    @SerializedName("periode")
    private String periode;

    public void setKeterangan(String keterangan){
        this.keterangan = keterangan;
    }

    public String getKeterangan(){
        return keterangan;
    }

    public void setTagihan(String tagihan){
        this.tagihan = tagihan;
    }

    public String getTagihan(){
        return tagihan;
    }

    public void setTahun(String tahun){
        this.tahun = tahun;
    }

    public String getTahun(){
        return tahun;
    }

    public void setAngsuranKe(String angsuranKe){
        this.angsuranKe = angsuranKe;
    }

    public String getAngsuranKe(){
        return angsuranKe;
    }

    public void setBulanName(String bulanName){
        this.bulanName = bulanName;
    }

    public String getBulanName(){
        return bulanName;
    }

    public void setIsTerbayar(boolean isTerbayar){
        this.isTerbayar = isTerbayar;
    }

    public boolean isIsTerbayar(){
        return isTerbayar;
    }

    public void setPeriodeRekAngsur(Object periodeRekAngsur){
        this.periodeRekAngsur = periodeRekAngsur;
    }

    public Object getPeriodeRekAngsur(){
        return periodeRekAngsur;
    }

    public void setBulan(int bulan){
        this.bulan = bulan;
    }

    public int getBulan(){
        return bulan;
    }

    public void setPeriode(String periode){
        this.periode = periode;
    }

    public String getPeriode(){
        return periode;
    }
}