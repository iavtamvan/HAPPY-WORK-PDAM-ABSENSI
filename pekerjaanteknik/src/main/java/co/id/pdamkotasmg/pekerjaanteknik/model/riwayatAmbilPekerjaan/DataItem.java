package co.id.pdamkotasmg.pekerjaanteknik.model.riwayatAmbilPekerjaan;

import com.google.gson.annotations.SerializedName;

public class DataItem{

    @SerializedName("sub_bagian")
    private Integer subBagian;

    @SerializedName("no_pengaduan_andro")
    private Object noPengaduanAndro;

    @SerializedName("tenaga")
    private String tenaga;

    @SerializedName("no_pengaduan")
    private String noPengaduan;

    @SerializedName("id_media_cc")
    private Integer idMediaCc;

    @SerializedName("dilihat_waktu")
    private Object dilihatWaktu;

    @SerializedName("role_id")
    private Integer roleId;

    @SerializedName("tagihan_bln_ini")
    private Object tagihanBlnIni;

    @SerializedName("penyebab")
    private String penyebab;

    @SerializedName("petugas_proses")
    private String petugasProses;

    @SerializedName("id")
    private Integer id;

    @SerializedName("alamat_pelanggan")
    private String alamatPelanggan;

    @SerializedName("khusus")
    private Integer khusus;

    @SerializedName("tgl_recek")
    private Object tglRecek;

    @SerializedName("tgl_aduan")
    private String tglAduan;

    @SerializedName("tarif_pelanggan")
    private Object tarifPelanggan;

    @SerializedName("petugas_recek")
    private String petugasRecek;

    @SerializedName("alamat")
    private String alamat;

    @SerializedName("status_pelanggan")
    private String statusPelanggan;

    @SerializedName("stand_kini")
    private Object standKini;

    @SerializedName("user_id")
    private Integer userId;

    @SerializedName("hasil_recek")
    private String hasilRecek;

    @SerializedName("status")
    private Integer status;

    @SerializedName("no")
    private Integer no;

    @SerializedName("penyelesaian")
    private String penyelesaian;

    @SerializedName("tgl_selesai")
    private Object tglSelesai;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("disposisi")
    private String disposisi;

    @SerializedName("no_spk")
    private Object noSpk;

    @SerializedName("id_cabang")
    private Integer idCabang;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("id_media")
    private String idMedia;

    @SerializedName("stand_lalu")
    private Object standLalu;

    @SerializedName("petugas_entry")
    private String petugasEntry;

    @SerializedName("jml_tunggakan")
    private Object jmlTunggakan;

    @SerializedName("notifikasi")
    private Integer notifikasi;

    @SerializedName("nama_pengadu")
    private String namaPengadu;

    @SerializedName("no_pengaduan_cc")
    private String noPengaduanCc;

    @SerializedName("id_kategori")
    private String idKategori;

    @SerializedName("id_disposisi_cc")
    private Integer idDisposisiCc;

    @SerializedName("uraian")
    private String uraian;

    @SerializedName("no_ta")
    private Object noTa;

    @SerializedName("no_langg")
    private String noLangg;

    @SerializedName("id_kategori_cc")
    private Integer idKategoriCc;

    @SerializedName("id_subbag_cc")
    private Integer idSubbagCc;

    @SerializedName("dilihat_oleh")
    private Object dilihatOleh;

    @SerializedName("pemakaian")
    private Object pemakaian;

    @SerializedName("nama_pelanggan")
    private String namaPelanggan;

    @SerializedName("cabang_pelanggan")
    private String cabangPelanggan;

    @SerializedName("telp_pengadu")
    private String telpPengadu;

    public void setSubBagian(Integer subBagian){
        this.subBagian = subBagian;
    }

    public Integer getSubBagian(){
        return subBagian;
    }

    public void setNoPengaduanAndro(Object noPengaduanAndro){
        this.noPengaduanAndro = noPengaduanAndro;
    }

    public Object getNoPengaduanAndro(){
        return noPengaduanAndro;
    }

    public void setTenaga(String tenaga){
        this.tenaga = tenaga;
    }

    public String getTenaga(){
        return tenaga;
    }

    public void setNoPengaduan(String noPengaduan){
        this.noPengaduan = noPengaduan;
    }

    public String getNoPengaduan(){
        return noPengaduan;
    }

    public void setIdMediaCc(Integer idMediaCc){
        this.idMediaCc = idMediaCc;
    }

    public Integer getIdMediaCc(){
        return idMediaCc;
    }

    public void setDilihatWaktu(Object dilihatWaktu){
        this.dilihatWaktu = dilihatWaktu;
    }

    public Object getDilihatWaktu(){
        return dilihatWaktu;
    }

    public void setRoleId(Integer roleId){
        this.roleId = roleId;
    }

    public Integer getRoleId(){
        return roleId;
    }

    public void setTagihanBlnIni(Object tagihanBlnIni){
        this.tagihanBlnIni = tagihanBlnIni;
    }

    public Object getTagihanBlnIni(){
        return tagihanBlnIni;
    }

    public void setPenyebab(String penyebab){
        this.penyebab = penyebab;
    }

    public String getPenyebab(){
        return penyebab;
    }

    public void setPetugasProses(String petugasProses){
        this.petugasProses = petugasProses;
    }

    public String getPetugasProses(){
        return petugasProses;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public Integer getId(){
        return id;
    }

    public void setAlamatPelanggan(String alamatPelanggan){
        this.alamatPelanggan = alamatPelanggan;
    }

    public String getAlamatPelanggan(){
        return alamatPelanggan;
    }

    public void setKhusus(Integer khusus){
        this.khusus = khusus;
    }

    public Integer getKhusus(){
        return khusus;
    }

    public void setTglRecek(Object tglRecek){
        this.tglRecek = tglRecek;
    }

    public Object getTglRecek(){
        return tglRecek;
    }

    public void setTglAduan(String tglAduan){
        this.tglAduan = tglAduan;
    }

    public String getTglAduan(){
        return tglAduan;
    }

    public void setTarifPelanggan(Object tarifPelanggan){
        this.tarifPelanggan = tarifPelanggan;
    }

    public Object getTarifPelanggan(){
        return tarifPelanggan;
    }

    public void setPetugasRecek(String petugasRecek){
        this.petugasRecek = petugasRecek;
    }

    public String getPetugasRecek(){
        return petugasRecek;
    }

    public void setAlamat(String alamat){
        this.alamat = alamat;
    }

    public String getAlamat(){
        return alamat;
    }

    public void setStatusPelanggan(String statusPelanggan){
        this.statusPelanggan = statusPelanggan;
    }

    public String getStatusPelanggan(){
        return statusPelanggan;
    }

    public void setStandKini(Object standKini){
        this.standKini = standKini;
    }

    public Object getStandKini(){
        return standKini;
    }

    public void setUserId(Integer userId){
        this.userId = userId;
    }

    public Integer getUserId(){
        return userId;
    }

    public void setHasilRecek(String hasilRecek){
        this.hasilRecek = hasilRecek;
    }

    public String getHasilRecek(){
        return hasilRecek;
    }

    public void setStatus(Integer status){
        this.status = status;
    }

    public Integer getStatus(){
        return status;
    }

    public void setNo(Integer no){
        this.no = no;
    }

    public Integer getNo(){
        return no;
    }

    public void setPenyelesaian(String penyelesaian){
        this.penyelesaian = penyelesaian;
    }

    public String getPenyelesaian(){
        return penyelesaian;
    }

    public void setTglSelesai(Object tglSelesai){
        this.tglSelesai = tglSelesai;
    }

    public Object getTglSelesai(){
        return tglSelesai;
    }

    public void setCreatedAt(String createdAt){
        this.createdAt = createdAt;
    }

    public String getCreatedAt(){
        return createdAt;
    }

    public void setDisposisi(String disposisi){
        this.disposisi = disposisi;
    }

    public String getDisposisi(){
        return disposisi;
    }

    public void setNoSpk(Object noSpk){
        this.noSpk = noSpk;
    }

    public Object getNoSpk(){
        return noSpk;
    }

    public void setIdCabang(Integer idCabang){
        this.idCabang = idCabang;
    }

    public Integer getIdCabang(){
        return idCabang;
    }

    public void setUpdatedAt(String updatedAt){
        this.updatedAt = updatedAt;
    }

    public String getUpdatedAt(){
        return updatedAt;
    }

    public void setIdMedia(String idMedia){
        this.idMedia = idMedia;
    }

    public String getIdMedia(){
        return idMedia;
    }

    public void setStandLalu(Object standLalu){
        this.standLalu = standLalu;
    }

    public Object getStandLalu(){
        return standLalu;
    }

    public void setPetugasEntry(String petugasEntry){
        this.petugasEntry = petugasEntry;
    }

    public String getPetugasEntry(){
        return petugasEntry;
    }

    public void setJmlTunggakan(Object jmlTunggakan){
        this.jmlTunggakan = jmlTunggakan;
    }

    public Object getJmlTunggakan(){
        return jmlTunggakan;
    }

    public void setNotifikasi(Integer notifikasi){
        this.notifikasi = notifikasi;
    }

    public Integer getNotifikasi(){
        return notifikasi;
    }

    public void setNamaPengadu(String namaPengadu){
        this.namaPengadu = namaPengadu;
    }

    public String getNamaPengadu(){
        return namaPengadu;
    }

    public void setNoPengaduanCc(String noPengaduanCc){
        this.noPengaduanCc = noPengaduanCc;
    }

    public String getNoPengaduanCc(){
        return noPengaduanCc;
    }

    public void setIdKategori(String idKategori){
        this.idKategori = idKategori;
    }

    public String getIdKategori(){
        return idKategori;
    }

    public void setIdDisposisiCc(Integer idDisposisiCc){
        this.idDisposisiCc = idDisposisiCc;
    }

    public Integer getIdDisposisiCc(){
        return idDisposisiCc;
    }

    public void setUraian(String uraian){
        this.uraian = uraian;
    }

    public String getUraian(){
        return uraian;
    }

    public void setNoTa(Object noTa){
        this.noTa = noTa;
    }

    public Object getNoTa(){
        return noTa;
    }

    public void setNoLangg(String noLangg){
        this.noLangg = noLangg;
    }

    public String getNoLangg(){
        return noLangg;
    }

    public void setIdKategoriCc(Integer idKategoriCc){
        this.idKategoriCc = idKategoriCc;
    }

    public Integer getIdKategoriCc(){
        return idKategoriCc;
    }

    public void setIdSubbagCc(Integer idSubbagCc){
        this.idSubbagCc = idSubbagCc;
    }

    public Integer getIdSubbagCc(){
        return idSubbagCc;
    }

    public void setDilihatOleh(Object dilihatOleh){
        this.dilihatOleh = dilihatOleh;
    }

    public Object getDilihatOleh(){
        return dilihatOleh;
    }

    public void setPemakaian(Object pemakaian){
        this.pemakaian = pemakaian;
    }

    public Object getPemakaian(){
        return pemakaian;
    }

    public void setNamaPelanggan(String namaPelanggan){
        this.namaPelanggan = namaPelanggan;
    }

    public String getNamaPelanggan(){
        return namaPelanggan;
    }

    public void setCabangPelanggan(String cabangPelanggan){
        this.cabangPelanggan = cabangPelanggan;
    }

    public String getCabangPelanggan(){
        return cabangPelanggan;
    }

    public void setTelpPengadu(String telpPengadu){
        this.telpPengadu = telpPengadu;
    }

    public String getTelpPengadu(){
        return telpPengadu;
    }
}