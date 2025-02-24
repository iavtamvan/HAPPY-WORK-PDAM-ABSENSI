package co.id.pdamkotasmg.model.bendel.bendelNext;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Data{

    @SerializedName("st")
    private String st;

    @SerializedName("cabang")
    private String cabang;

    @SerializedName("sumur")
    private Object sumur;

    @SerializedName("kec")
    private Object kec;

    @SerializedName("tgl_meter")
    private Object tglMeter;

    @SerializedName("rl_trbaca")
    private List<RlTrbacaItem> rlTrbaca;

    @SerializedName("nolangg")
    private String nolangg;

    @SerializedName("alamat")
    private String alamat;

    @SerializedName("merek")
    private String merek;

    @SerializedName("dism")
    private String dism;

    @SerializedName("tgl_buka")
    private Object tglBuka;

    @SerializedName("tarif")
    private String tarif;

    @SerializedName("kel")
    private Object kel;

    @SerializedName("nama")
    private String nama;

    @SerializedName("tgl_pasang")
    private Object tglPasang;

    @SerializedName("diameter")
    private String diameter;

    @SerializedName("rl_dt_baca_periode_skrg")
    private List<RlDtBacaPeriodeSkrgItem> rlDtBacaPeriodeSkrg;

    @SerializedName("tlp")
    private String tlp;

    @SerializedName("tgl_tutup")
    private Object tglTutup;

    @SerializedName("nomormtr")
    private String nomormtr;

    @SerializedName("tanggal")
    private Object tanggal;

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

    public void setSumur(Object sumur){
        this.sumur = sumur;
    }

    public Object getSumur(){
        return sumur;
    }

    public void setKec(Object kec){
        this.kec = kec;
    }

    public Object getKec(){
        return kec;
    }

    public void setTglMeter(Object tglMeter){
        this.tglMeter = tglMeter;
    }

    public Object getTglMeter(){
        return tglMeter;
    }

    public void setRlTrbaca(List<RlTrbacaItem> rlTrbaca){
        this.rlTrbaca = rlTrbaca;
    }

    public List<RlTrbacaItem> getRlTrbaca(){
        return rlTrbaca;
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

    public void setTglBuka(Object tglBuka){
        this.tglBuka = tglBuka;
    }

    public Object getTglBuka(){
        return tglBuka;
    }

    public void setTarif(String tarif){
        this.tarif = tarif;
    }

    public String getTarif(){
        return tarif;
    }

    public void setKel(Object kel){
        this.kel = kel;
    }

    public Object getKel(){
        return kel;
    }

    public void setNama(String nama){
        this.nama = nama;
    }

    public String getNama(){
        return nama;
    }

    public void setTglPasang(Object tglPasang){
        this.tglPasang = tglPasang;
    }

    public Object getTglPasang(){
        return tglPasang;
    }

    public void setDiameter(String diameter){
        this.diameter = diameter;
    }

    public String getDiameter(){
        return diameter;
    }

    public void setRlDtBacaPeriodeSkrg(List<RlDtBacaPeriodeSkrgItem> rlDtBacaPeriodeSkrg){
        this.rlDtBacaPeriodeSkrg = rlDtBacaPeriodeSkrg;
    }

    public List<RlDtBacaPeriodeSkrgItem> getRlDtBacaPeriodeSkrg(){
        return rlDtBacaPeriodeSkrg;
    }

    public void setTlp(String tlp){
        this.tlp = tlp;
    }

    public String getTlp(){
        return tlp;
    }

    public void setTglTutup(Object tglTutup){
        this.tglTutup = tglTutup;
    }

    public Object getTglTutup(){
        return tglTutup;
    }

    public void setNomormtr(String nomormtr){
        this.nomormtr = nomormtr;
    }

    public String getNomormtr(){
        return nomormtr;
    }

    public void setTanggal(Object tanggal){
        this.tanggal = tanggal;
    }

    public Object getTanggal(){
        return tanggal;
    }
}