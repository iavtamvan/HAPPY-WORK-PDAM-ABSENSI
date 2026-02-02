package com.pdamsmg.pekerjaanteknik.model.riwayatSpk.mandor;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DataItem{

    @SerializedName("rl_pekerjaan")
    private List<RlPekerjaanItem> rlPekerjaan;

    @SerializedName("bagian")
    private String bagian;

    @SerializedName("pc_entry")
    private String pcEntry;

    @SerializedName("no_spk")
    private String noSpk;

    @SerializedName("tgl_update")
    private String tglUpdate;

    @SerializedName("nocc")
    private String nocc;

    @SerializedName("nolangg_lapor")
    private String nolanggLapor;

    @SerializedName("status_verif")
    private String statusVerif;

    @SerializedName("rl_user_entry")
    private RlUserEntry rlUserEntry;

    @SerializedName("asal_lapor")
    private String asalLapor;

    @SerializedName("bagian_sub")
    private String bagianSub;

    @SerializedName("rl_pelaksana_dt")
    private List<RlPelaksanaDtItem> rlPelaksanaDt;

    @SerializedName("cabang")
    private String cabang;

    @SerializedName("rl_pelaksana")
    private RlPelaksana rlPelaksana;

    @SerializedName("rl_pengawas")
    private RlPengawas rlPengawas;

    @SerializedName("tgl_verifikasi")
    private String tglVerifikasi;

    @SerializedName("uraian")
    private String uraian;

    @SerializedName("alamat_lapor")
    private String alamatLapor;

    @SerializedName("ip_entry")
    private String ipEntry;

    @SerializedName("alamat")
    private String alamat;

    @SerializedName("rl_kasub")
    private RlKasub rlKasub;

    @SerializedName("pengawas")
    private String pengawas;

    @SerializedName("nama")
    private String nama;

    @SerializedName("user_entry")
    private String userEntry;

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

    public void setRlPekerjaan(List<RlPekerjaanItem> rlPekerjaan){
        this.rlPekerjaan = rlPekerjaan;
    }

    public List<RlPekerjaanItem> getRlPekerjaan(){
        return rlPekerjaan;
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

    public void setTglUpdate(String tglUpdate){
        this.tglUpdate = tglUpdate;
    }

    public String getTglUpdate(){
        return tglUpdate;
    }

    public void setNocc(String nocc){
        this.nocc = nocc;
    }

    public String getNocc(){
        return nocc;
    }

    public void setNolanggLapor(String nolanggLapor){
        this.nolanggLapor = nolanggLapor;
    }

    public String getNolanggLapor(){
        return nolanggLapor;
    }

    public void setStatusVerif(String statusVerif){
        this.statusVerif = statusVerif;
    }

    public String getStatusVerif(){
        return statusVerif;
    }

    public void setRlUserEntry(RlUserEntry rlUserEntry){
        this.rlUserEntry = rlUserEntry;
    }

    public RlUserEntry getRlUserEntry(){
        return rlUserEntry;
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

    public void setRlPelaksanaDt(List<RlPelaksanaDtItem> rlPelaksanaDt){
        this.rlPelaksanaDt = rlPelaksanaDt;
    }

    public List<RlPelaksanaDtItem> getRlPelaksanaDt(){
        return rlPelaksanaDt;
    }

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

    public void setRlPengawas(RlPengawas rlPengawas){
        this.rlPengawas = rlPengawas;
    }

    public RlPengawas getRlPengawas(){
        return rlPengawas;
    }

    public void setTglVerifikasi(String tglVerifikasi){
        this.tglVerifikasi = tglVerifikasi;
    }

    public String getTglVerifikasi(){
        return tglVerifikasi;
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

    public void setRlKasub(RlKasub rlKasub){
        this.rlKasub = rlKasub;
    }

    public RlKasub getRlKasub(){
        return rlKasub;
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
}