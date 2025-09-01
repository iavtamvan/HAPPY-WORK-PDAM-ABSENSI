package co.id.pdamkotasmg.pekerjaanteknik.model.spk;

import com.google.gson.annotations.SerializedName;

public class DataItem{

    @SerializedName("cabang")
    private String cabang;

    @SerializedName("rl_pelaksana")
    private RlPelaksana rlPelaksana;

    @SerializedName("uraian")
    private String uraian;

    @SerializedName("alamat_lapor")
    private String alamatLapor;

    @SerializedName("bagian")
    private String bagian;

    @SerializedName("pc_entry")
    private String pcEntry;

    @SerializedName("no_spk")
    private String noSpk;

    @SerializedName("ip_entry")
    private String ipEntry;

    @SerializedName("alamat")
    private String alamat;

    @SerializedName("pengawas")
    private String pengawas;

    @SerializedName("nama")
    private String nama;

    @SerializedName("user_entry")
    private String userEntry;

    @SerializedName("nolangg_lapor")
    private String nolanggLapor;

    @SerializedName("lokasi")
    private String lokasi;

    @SerializedName("nm_lapor")
    private String nmLapor;

    @SerializedName("kasub")
    private String kasub;

    @SerializedName("tanggal")
    private String tanggal;

    @SerializedName("tgl_spk")
    private String tglSpk;

    @SerializedName("asal_lapor")
    private String asalLapor;

    @SerializedName("bagian_sub")
    private String bagianSub;

    public void setCabang(String cabang){
        this.cabang = cabang;
    }

    public String getCabang(){
        return cabang;
    }

    public void setRlPelaksana(RlPelaksana rlPelaksana){
        this.rlPelaksana = rlPelaksana;
    }

    public RlPelaksana getRlPelaksana(){
        return rlPelaksana;
    }

    public void setUraian(String uraian){
        this.uraian = uraian;
    }

    public String getUraian(){
        return uraian;
    }

    public void setAlamatLapor(String alamatLapor){
        this.alamatLapor = alamatLapor;
    }

    public String getAlamatLapor(){
        return alamatLapor;
    }

    public void setBagian(String bagian){
        this.bagian = bagian;
    }

    public String getBagian(){
        return bagian;
    }

    public void setPcEntry(String pcEntry){
        this.pcEntry = pcEntry;
    }

    public String getPcEntry(){
        return pcEntry;
    }

    public void setNoSpk(String noSpk){
        this.noSpk = noSpk;
    }

    public String getNoSpk(){
        return noSpk;
    }

    public void setIpEntry(String ipEntry){
        this.ipEntry = ipEntry;
    }

    public String getIpEntry(){
        return ipEntry;
    }

    public void setAlamat(String alamat){
        this.alamat = alamat;
    }

    public String getAlamat(){
        return alamat;
    }

    public void setPengawas(String pengawas){
        this.pengawas = pengawas;
    }

    public String getPengawas(){
        return pengawas;
    }

    public void setNama(String nama){
        this.nama = nama;
    }

    public String getNama(){
        return nama;
    }

    public void setUserEntry(String userEntry){
        this.userEntry = userEntry;
    }

    public String getUserEntry(){
        return userEntry;
    }

    public void setNolanggLapor(String nolanggLapor){
        this.nolanggLapor = nolanggLapor;
    }

    public String getNolanggLapor(){
        return nolanggLapor;
    }

    public void setLokasi(String lokasi){
        this.lokasi = lokasi;
    }

    public String getLokasi(){
        return lokasi;
    }

    public void setNmLapor(String nmLapor){
        this.nmLapor = nmLapor;
    }

    public String getNmLapor(){
        return nmLapor;
    }

    public void setKasub(String kasub){
        this.kasub = kasub;
    }

    public String getKasub(){
        return kasub;
    }

    public void setTanggal(String tanggal){
        this.tanggal = tanggal;
    }

    public String getTanggal(){
        return tanggal;
    }

    public void setTglSpk(String tglSpk){
        this.tglSpk = tglSpk;
    }

    public String getTglSpk(){
        return tglSpk;
    }

    public void setAsalLapor(String asalLapor){
        this.asalLapor = asalLapor;
    }

    public String getAsalLapor(){
        return asalLapor;
    }

    public void setBagianSub(String bagianSub){
        this.bagianSub = bagianSub;
    }

    public String getBagianSub(){
        return bagianSub;
    }
}