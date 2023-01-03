package id.pdamkotasmg.pekerjaan_teknik_feature.model.spk;

import com.google.gson.annotations.SerializedName;

public class RlPelaksana{

    @SerializedName("tgl_pelaksana")
    private String tglPelaksana;

    @SerializedName("cabang")
    private String cabang;

    @SerializedName("kd_perbaikan")
    private String kdPerbaikan;

    @SerializedName("jenis_pipa")
    private String jenisPipa;

    @SerializedName("no_spk_sbl")
    private String noSpkSbl;

    @SerializedName("uraian")
    private String uraian;

    @SerializedName("jam1")
    private String jam1;

    @SerializedName("bagian")
    private String bagian;

    @SerializedName("jam2")
    private String jam2;

    @SerializedName("pc_entry")
    private String pcEntry;

    @SerializedName("ip_entry")
    private String ipEntry;

    @SerializedName("kd_penangan")
    private Object kdPenangan;

    @SerializedName("pengawas")
    private String pengawas;

    @SerializedName("jml_tenaga_ket")
    private String jmlTenagaKet;

    @SerializedName("user_entry")
    private String userEntry;

    @SerializedName("x")
    private String X;

    @SerializedName("y")
    private String Y;

    @SerializedName("jml_tenaga")
    private String jmlTenaga;

    @SerializedName("kasub")
    private String kasub;

    @SerializedName("tanggal")
    private String tanggal;

    @SerializedName("bagian_sub")
    private String bagianSub;

    @SerializedName("status")
    private String status;

    public void setTglPelaksana(String tglPelaksana){
        this.tglPelaksana = tglPelaksana;
    }

    public String getTglPelaksana(){
        return tglPelaksana;
    }

    public void setCabang(String cabang){
        this.cabang = cabang;
    }

    public String getCabang(){
        return cabang;
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

    public void setNoSpkSbl(String noSpkSbl){
        this.noSpkSbl = noSpkSbl;
    }

    public String getNoSpkSbl(){
        return noSpkSbl;
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

    public void setBagian(String bagian){
        this.bagian = bagian;
    }

    public String getBagian(){
        return bagian;
    }

    public void setJam2(String jam2){
        this.jam2 = jam2;
    }

    public String getJam2(){
        return jam2;
    }

    public void setPcEntry(String pcEntry){
        this.pcEntry = pcEntry;
    }

    public String getPcEntry(){
        return pcEntry;
    }

    public void setIpEntry(String ipEntry){
        this.ipEntry = ipEntry;
    }

    public String getIpEntry(){
        return ipEntry;
    }

    public void setKdPenangan(Object kdPenangan){
        this.kdPenangan = kdPenangan;
    }

    public Object getKdPenangan(){
        return kdPenangan;
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

    public void setUserEntry(String userEntry){
        this.userEntry = userEntry;
    }

    public String getUserEntry(){
        return userEntry;
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

    public void setJmlTenaga(String jmlTenaga){
        this.jmlTenaga = jmlTenaga;
    }

    public String getJmlTenaga(){
        return jmlTenaga;
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

    public void setBagianSub(String bagianSub){
        this.bagianSub = bagianSub;
    }

    public String getBagianSub(){
        return bagianSub;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }
}