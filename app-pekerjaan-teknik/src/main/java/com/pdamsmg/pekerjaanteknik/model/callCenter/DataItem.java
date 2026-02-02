package com.pdamsmg.pekerjaanteknik.model.callCenter;

import com.google.gson.annotations.SerializedName;

public class DataItem{

    @SerializedName("no")
    private Integer no;

    @SerializedName("sub_bagian")
    private Integer subBagian;

    @SerializedName("penyelesaian")
    private String penyelesaian;

    @SerializedName("tgl_selesai")
    private String tglSelesai;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("no_pengaduan_andro")
    private Object noPengaduanAndro;

    @SerializedName("disposisi")
    private String disposisi;

    @SerializedName("no_pengaduan")
    private String noPengaduan;

    @SerializedName("id_media_cc")
    private Integer idMediaCc;

    @SerializedName("id_cabang")
    private Integer idCabang;

    @SerializedName("dilihat_waktu")
    private Object dilihatWaktu;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("role_id")
    private Integer roleId;

    @SerializedName("id_media")
    private String idMedia;

    @SerializedName("tagihan_bln_ini")
    private Object tagihanBlnIni;

    @SerializedName("stand_lalu")
    private Object standLalu;

    @SerializedName("petugas_entry")
    private String petugasEntry;

    @SerializedName("jml_tunggakan")
    private Object jmlTunggakan;

    @SerializedName("penyebab")
    private String penyebab;

    @SerializedName("petugas_proses")
    private String petugasProses;

    @SerializedName("id")
    private Integer id;

    @SerializedName("alamat_pelanggan")
    private Object alamatPelanggan;

    @SerializedName("notifikasi")
    private Integer notifikasi;

    @SerializedName("nama_pengadu")
    private String namaPengadu;

    @SerializedName("khusus")
    private Integer khusus;

    @SerializedName("tgl_recek")
    private Object tglRecek;

    @SerializedName("no_pengaduan_cc")
    private String noPengaduanCc;

    @SerializedName("id_kategori")
    private String idKategori;

    @SerializedName("tgl_aduan")
    private String tglAduan;

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

    @SerializedName("tarif_pelanggan")
    private Object tarifPelanggan;

    @SerializedName("petugas_recek")
    private String petugasRecek;

    @SerializedName("alamat")
    private String alamat;

    @SerializedName("dilihat_oleh")
    private Object dilihatOleh;

    @SerializedName("status_pelanggan")
    private Object statusPelanggan;

    @SerializedName("pemakaian")
    private Object pemakaian;

    @SerializedName("nama_pelanggan")
    private Object namaPelanggan;

    @SerializedName("stand_kini")
    private Object standKini;

    @SerializedName("user_id")
    private Integer userId;

    @SerializedName("cabang_pelanggan")
    private Object cabangPelanggan;

    @SerializedName("telp_pengadu")
    private String telpPengadu;

    @SerializedName("hasil_recek")
    private String hasilRecek;

    @SerializedName("status")
    private Integer status;

    @SerializedName("no_spk")
    private Integer noSpk;

    public Integer getNoSpk() {
        return noSpk;
    }

    public void setNoSpk(Integer noSpk) {
        this.noSpk = noSpk;
    }

    public void setNo(Integer no){
        this.no = no;
    }

    public Integer getNo(){
        return no;
    }

    public void setSubBagian(Integer subBagian){
        this.subBagian = subBagian;
    }

    public Integer getSubBagian(){
        return subBagian;
    }

    public void setPenyelesaian(String penyelesaian){
        this.penyelesaian = penyelesaian;
    }

    public String getPenyelesaian(){
        return penyelesaian;
    }

    public void setTglSelesai(String tglSelesai){
        this.tglSelesai = tglSelesai;
    }

    public String getTglSelesai(){
        return tglSelesai;
    }

    public void setCreatedAt(String createdAt){
        this.createdAt = createdAt;
    }

    public String getCreatedAt(){
        return createdAt;
    }

    public void setNoPengaduanAndro(Object noPengaduanAndro){
        this.noPengaduanAndro = noPengaduanAndro;
    }

    public Object getNoPengaduanAndro(){
        return noPengaduanAndro;
    }

    public void setDisposisi(String disposisi){
        this.disposisi = disposisi;
    }

    public String getDisposisi(){
        return disposisi;
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

    public void setIdCabang(Integer idCabang){
        this.idCabang = idCabang;
    }

    public Integer getIdCabang(){
        return idCabang;
    }

    public void setDilihatWaktu(Object dilihatWaktu){
        this.dilihatWaktu = dilihatWaktu;
    }

    public Object getDilihatWaktu(){
        return dilihatWaktu;
    }

    public void setUpdatedAt(String updatedAt){
        this.updatedAt = updatedAt;
    }

    public String getUpdatedAt(){
        return updatedAt;
    }

    public void setRoleId(Integer roleId){
        this.roleId = roleId;
    }

    public Integer getRoleId(){
        return roleId;
    }

    public void setIdMedia(String idMedia){
        this.idMedia = idMedia;
    }

    public String getIdMedia(){
        return idMedia;
    }

    public void setTagihanBlnIni(Object tagihanBlnIni){
        this.tagihanBlnIni = tagihanBlnIni;
    }

    public Object getTagihanBlnIni(){
        return tagihanBlnIni;
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

    public void setAlamatPelanggan(Object alamatPelanggan){
        this.alamatPelanggan = alamatPelanggan;
    }

    public Object getAlamatPelanggan(){
        return alamatPelanggan;
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

    public void setTglAduan(String tglAduan){
        this.tglAduan = tglAduan;
    }

    public String getTglAduan(){
        return tglAduan;
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

    public void setDilihatOleh(Object dilihatOleh){
        this.dilihatOleh = dilihatOleh;
    }

    public Object getDilihatOleh(){
        return dilihatOleh;
    }

    public void setStatusPelanggan(Object statusPelanggan){
        this.statusPelanggan = statusPelanggan;
    }

    public Object getStatusPelanggan(){
        return statusPelanggan;
    }

    public void setPemakaian(Object pemakaian){
        this.pemakaian = pemakaian;
    }

    public Object getPemakaian(){
        return pemakaian;
    }

    public void setNamaPelanggan(Object namaPelanggan){
        this.namaPelanggan = namaPelanggan;
    }

    public Object getNamaPelanggan(){
        return namaPelanggan;
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

    public void setCabangPelanggan(Object cabangPelanggan){
        this.cabangPelanggan = cabangPelanggan;
    }

    public Object getCabangPelanggan(){
        return cabangPelanggan;
    }

    public void setTelpPengadu(String telpPengadu){
        this.telpPengadu = telpPengadu;
    }

    public String getTelpPengadu(){
        return telpPengadu;
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
}