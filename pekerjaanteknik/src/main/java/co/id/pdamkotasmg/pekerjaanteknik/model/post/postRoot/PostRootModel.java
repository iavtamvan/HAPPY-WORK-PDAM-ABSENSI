package co.id.pdamkotasmg.pekerjaanteknik.model.post.postRoot;

import java.util.List;
import com.google.gson.annotations.SerializedName;

import okhttp3.MultipartBody;

public class PostRootModel{

    @SerializedName("tgl_pelaksana")
    private String tglPelaksana;

    @SerializedName("no_spk_sbl")
    private String noSpkSbl;

    @SerializedName("tekanan")
    private String tekanan;

    @SerializedName("tka")
    private String tka;

    @SerializedName("pc_entry")
    private String pcEntry;

    @SerializedName("no_spk")
    private String noSpk;

    @SerializedName("diameter")
    private String diameter;

    @SerializedName("nolangg_lapor")
    private String nolanggLapor;

    @SerializedName("list_pekerjaan")
    private List<ListPekerjaanItem> listPekerjaan;

    @SerializedName("jml_tenaga")
    private String jmlTenaga;

    @SerializedName("asal_lapor")
    private String asalLapor;

    @SerializedName("kd_zona")
    private String kdZona;

    @SerializedName("kd_perbaikan")
    private String kdPerbaikan;

    @SerializedName("jenis_pipa")
    private String jenisPipa;

    @SerializedName("uraian")
    private String uraian;

    @SerializedName("jam1")
    private String jam1;

    @SerializedName("alamat_lapor")
    private String alamatLapor;

    @SerializedName("jam2")
    private String jam2;

    @SerializedName("ip_entry")
    private String ipEntry;

    @SerializedName("alamat")
    private String alamat;

    @SerializedName("kd_penangan")
    private String kdPenangan;

    @SerializedName("list_barang")
    private List<ListBarangItem> listBarang;

    @SerializedName("pengawas")
    private String pengawas;

    @SerializedName("jml_tenaga_ket")
    private String jmlTenagaKet;

    @SerializedName("ket_zona")
    private String ketZona;

    @SerializedName("nama")
    private String nama;

    @SerializedName("lokasi")
    private String lokasi;

    @SerializedName("x")
    private String X;

    @SerializedName("y")
    private String Y;

    @SerializedName("tipe_zona")
    private String tipeZona;

    @SerializedName("nm_lapor")
    private String nmLapor;

    @SerializedName("kasub")
    private String kasub;

    @SerializedName("tanggal")
    private String tanggal;

    @SerializedName("status")
    private String status;

    @SerializedName("foto1")
    private MultipartBody.Part foto1;

    public PostRootModel(String tglPelaksana, String noSpkSbl, String tekanan, String tka, String pcEntry, String noSpk, String diameter, String nolanggLapor, List<ListPekerjaanItem> listPekerjaan, String jmlTenaga, String asalLapor, String kdZona, String kdPerbaikan, String jenisPipa, String uraian, String jam1, String alamatLapor, String jam2, String ipEntry, String alamat, String kdPenangan, List<ListBarangItem> listBarang, String pengawas, String jmlTenagaKet, String ketZona, String nama, String lokasi, String x, String y, String tipeZona, String nmLapor, String kasub, String tanggal, String status, MultipartBody.Part foto1) {
        this.tglPelaksana = tglPelaksana;
        this.noSpkSbl = noSpkSbl;
        this.tekanan = tekanan;
        this.tka = tka;
        this.pcEntry = pcEntry;
        this.noSpk = noSpk;
        this.diameter = diameter;
        this.nolanggLapor = nolanggLapor;
        this.listPekerjaan = listPekerjaan;
        this.jmlTenaga = jmlTenaga;
        this.asalLapor = asalLapor;
        this.kdZona = kdZona;
        this.kdPerbaikan = kdPerbaikan;
        this.jenisPipa = jenisPipa;
        this.uraian = uraian;
        this.jam1 = jam1;
        this.alamatLapor = alamatLapor;
        this.jam2 = jam2;
        this.ipEntry = ipEntry;
        this.alamat = alamat;
        this.kdPenangan = kdPenangan;
        this.listBarang = listBarang;
        this.pengawas = pengawas;
        this.jmlTenagaKet = jmlTenagaKet;
        this.ketZona = ketZona;
        this.nama = nama;
        this.lokasi = lokasi;
        X = x;
        Y = y;
        this.tipeZona = tipeZona;
        this.nmLapor = nmLapor;
        this.kasub = kasub;
        this.tanggal = tanggal;
        this.status = status;
        this.foto1 = foto1;
    }

    public MultipartBody.Part getFoto1() {
        return foto1;
    }

    public void setFoto1(MultipartBody.Part foto1) {
        this.foto1 = foto1;
    }

    public void setTglPelaksana(String tglPelaksana){
        this.tglPelaksana = tglPelaksana;
    }

    public String getTglPelaksana(){
        return tglPelaksana;
    }

    public void setNoSpkSbl(String noSpkSbl){
        this.noSpkSbl = noSpkSbl;
    }

    public String getNoSpkSbl(){
        return noSpkSbl;
    }

    public void setTekanan(String tekanan){
        this.tekanan = tekanan;
    }

    public String getTekanan(){
        return tekanan;
    }

    public void setTka(String tka){
        this.tka = tka;
    }

    public String getTka(){
        return tka;
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

    public void setDiameter(String diameter){
        this.diameter = diameter;
    }

    public String getDiameter(){
        return diameter;
    }

    public void setNolanggLapor(String nolanggLapor){
        this.nolanggLapor = nolanggLapor;
    }

    public String getNolanggLapor(){
        return nolanggLapor;
    }

    public void setListPekerjaan(List<ListPekerjaanItem> listPekerjaan){
        this.listPekerjaan = listPekerjaan;
    }

    public List<ListPekerjaanItem> getListPekerjaan(){
        return listPekerjaan;
    }

    public void setJmlTenaga(String jmlTenaga){
        this.jmlTenaga = jmlTenaga;
    }

    public String getJmlTenaga(){
        return jmlTenaga;
    }

    public void setAsalLapor(String asalLapor){
        this.asalLapor = asalLapor;
    }

    public String getAsalLapor(){
        return asalLapor;
    }

    public void setKdZona(String kdZona){
        this.kdZona = kdZona;
    }

    public String getKdZona(){
        return kdZona;
    }

    public void setKdPerbaikan(String kdPerbaikan){
        this.kdPerbaikan = kdPerbaikan;
    }

    public String getKdPerbaikan(){
        return kdPerbaikan;
    }

    public void setJenisPipa(String jenisPipa){
        this.jenisPipa = jenisPipa;
    }

    public String getJenisPipa(){
        return jenisPipa;
    }

    public void setUraian(String uraian){
        this.uraian = uraian;
    }

    public String getUraian(){
        return uraian;
    }

    public void setJam1(String jam1){
        this.jam1 = jam1;
    }

    public String getJam1(){
        return jam1;
    }

    public void setAlamatLapor(String alamatLapor){
        this.alamatLapor = alamatLapor;
    }

    public String getAlamatLapor(){
        return alamatLapor;
    }

    public void setJam2(String jam2){
        this.jam2 = jam2;
    }

    public String getJam2(){
        return jam2;
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

    public void setKdPenangan(String kdPenangan){
        this.kdPenangan = kdPenangan;
    }

    public String getKdPenangan(){
        return kdPenangan;
    }

    public void setListBarang(List<ListBarangItem> listBarang){
        this.listBarang = listBarang;
    }

    public List<ListBarangItem> getListBarang(){
        return listBarang;
    }

    public void setPengawas(String pengawas){
        this.pengawas = pengawas;
    }

    public String getPengawas(){
        return pengawas;
    }

    public void setJmlTenagaKet(String jmlTenagaKet){
        this.jmlTenagaKet = jmlTenagaKet;
    }

    public String getJmlTenagaKet(){
        return jmlTenagaKet;
    }

    public void setKetZona(String ketZona){
        this.ketZona = ketZona;
    }

    public String getKetZona(){
        return ketZona;
    }

    public void setNama(String nama){
        this.nama = nama;
    }

    public String getNama(){
        return nama;
    }

    public void setLokasi(String lokasi){
        this.lokasi = lokasi;
    }

    public String getLokasi(){
        return lokasi;
    }

    public void setX(String X){
        this.X = X;
    }

    public String getX(){
        return X;
    }

    public void setY(String Y){
        this.Y = Y;
    }

    public String getY(){
        return Y;
    }

    public void setTipeZona(String tipeZona){
        this.tipeZona = tipeZona;
    }

    public String getTipeZona(){
        return tipeZona;
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

    public void setStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }
}