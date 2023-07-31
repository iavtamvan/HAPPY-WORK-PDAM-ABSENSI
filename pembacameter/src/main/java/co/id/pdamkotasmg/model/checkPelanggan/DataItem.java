package co.id.pdamkotasmg.model.checkPelanggan;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataItem{

    @SerializedName("st")
    private String st;

    @SerializedName("cabang")
    private String cabang;

    @SerializedName("sumur")
    private String sumur;

    @SerializedName("kec")
    private String kec;

    @SerializedName("tgl_meter")
    private String tglMeter;

    @SerializedName("rl_trbaca")
    private List<RlTrbacaItem> rlTrbaca;

    @SerializedName("rl_tarif")
    private RlTarif rlTarif;

    @SerializedName("nolangg")
    private String nolangg;

    @SerializedName("alamat")
    private String alamat;

    @SerializedName("merek")
    private String merek;

    @SerializedName("dism")
    private String dism;

    @SerializedName("tgl_buka")
    private String tglBuka;

    @SerializedName("tarif")
    private String tarif;

    @SerializedName("kel")
    private String kel;

    @SerializedName("nama")
    private String nama;

    @SerializedName("tgl_pasang")
    private String tglPasang;

    @SerializedName("diameter")
    private String diameter;

    @SerializedName("tlp")
    private String tlp;

    @SerializedName("tgl_tutup")
    private String tglTutup;

    @SerializedName("nomormtr")
    private String nomormtr;

    @SerializedName("tanggal")
    private String tanggal;

    public void setSt(String st){
        this.st = st;
    }

    public String getSt(){
        return st;
    }

    public void setCabang(String cabang){
        this.cabang = cabang;
    }

    public String getCabang(){
        return cabang;
    }

    public void setSumur(String sumur){
        this.sumur = sumur;
    }

    public String getSumur(){
        return sumur;
    }

    public void setKec(String kec){
        this.kec = kec;
    }

    public String getKec(){
        return kec;
    }

    public void setTglMeter(String tglMeter){
        this.tglMeter = tglMeter;
    }

    public String getTglMeter(){
        return tglMeter;
    }

    public void setRlTrbaca(List<RlTrbacaItem> rlTrbaca){
        this.rlTrbaca = rlTrbaca;
    }

    public List<RlTrbacaItem> getRlTrbaca(){
        return rlTrbaca;
    }

    public void setRlTarif(RlTarif rlTarif){
        this.rlTarif = rlTarif;
    }

    public RlTarif getRlTarif(){
        return rlTarif;
    }

    public void setNolangg(String nolangg){
        this.nolangg = nolangg;
    }

    public String getNolangg(){
        return nolangg;
    }

    public void setAlamat(String alamat){
        this.alamat = alamat;
    }

    public String getAlamat(){
        return alamat;
    }

    public void setMerek(String merek){
        this.merek = merek;
    }

    public String getMerek(){
        return merek;
    }

    public void setDism(String dism){
        this.dism = dism;
    }

    public String getDism(){
        return dism;
    }

    public void setTglBuka(String tglBuka){
        this.tglBuka = tglBuka;
    }

    public String getTglBuka(){
        return tglBuka;
    }

    public void setTarif(String tarif){
        this.tarif = tarif;
    }

    public String getTarif(){
        return tarif;
    }

    public void setKel(String kel){
        this.kel = kel;
    }

    public String getKel(){
        return kel;
    }

    public void setNama(String nama){
        this.nama = nama;
    }

    public String getNama(){
        return nama;
    }

    public void setTglPasang(String tglPasang){
        this.tglPasang = tglPasang;
    }

    public String getTglPasang(){
        return tglPasang;
    }

    public void setDiameter(String diameter){
        this.diameter = diameter;
    }

    public String getDiameter(){
        return diameter;
    }

    public void setTlp(String tlp){
        this.tlp = tlp;
    }

    public String getTlp(){
        return tlp;
    }

    public void setTglTutup(String tglTutup){
        this.tglTutup = tglTutup;
    }

    public String getTglTutup(){
        return tglTutup;
    }

    public void setNomormtr(String nomormtr){
        this.nomormtr = nomormtr;
    }

    public String getNomormtr(){
        return nomormtr;
    }

    public void setTanggal(String tanggal){
        this.tanggal = tanggal;
    }

    public String getTanggal(){
        return tanggal;
    }
}