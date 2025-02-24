package co.id.pdamkotasmg.model.home;

import com.google.gson.annotations.SerializedName;

public class Data{

    @SerializedName("sisaBacaanCountPeriodeLalu")
    private String sisaBacaanCountPeriodeLalu;

    @SerializedName("sisaBacaanCountPeriodeNow")
    private String sisaBacaanCountPeriodeNow;

    @SerializedName("cabang")
    private String cabang;

    @SerializedName("name")
    private String name;

    @SerializedName("countTotalpembacaanPetugasHariIni")
    private String countTotalpembacaanPetugasHariIni;

    @SerializedName("countTotalPetugas")
    private String countTotalPetugas;

    @SerializedName("jamAwal")
    private JamAwal jamAwal;

    @SerializedName("npp")
    private String npp;

    @SerializedName("periode")
    private String periode;

    public void setSisaBacaanCountPeriodeLalu(String sisaBacaanCountPeriodeLalu){
        this.sisaBacaanCountPeriodeLalu = sisaBacaanCountPeriodeLalu;
    }

    public String getSisaBacaanCountPeriodeLalu(){
        return sisaBacaanCountPeriodeLalu;
    }

    public void setSisaBacaanCountPeriodeNow(String sisaBacaanCountPeriodeNow){
        this.sisaBacaanCountPeriodeNow = sisaBacaanCountPeriodeNow;
    }

    public String getSisaBacaanCountPeriodeNow(){
        return sisaBacaanCountPeriodeNow;
    }

    public void setCabang(String cabang){
        this.cabang = cabang;
    }

    public String getCabang(){
        return cabang;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setCountTotalpembacaanPetugasHariIni(String countTotalpembacaanPetugasHariIni){
        this.countTotalpembacaanPetugasHariIni = countTotalpembacaanPetugasHariIni;
    }

    public String getCountTotalpembacaanPetugasHariIni(){
        return countTotalpembacaanPetugasHariIni;
    }

    public void setCountTotalPetugas(String countTotalPetugas){
        this.countTotalPetugas = countTotalPetugas;
    }

    public String getCountTotalPetugas(){
        return countTotalPetugas;
    }

    public void setJamAwal(JamAwal jamAwal){
        this.jamAwal = jamAwal;
    }

    public JamAwal getJamAwal(){
        return jamAwal;
    }

    public void setNpp(String npp){
        this.npp = npp;
    }

    public String getNpp(){
        return npp;
    }

    public void setPeriode(String periode){
        this.periode = periode;
    }

    public String getPeriode(){
        return periode;
    }
}