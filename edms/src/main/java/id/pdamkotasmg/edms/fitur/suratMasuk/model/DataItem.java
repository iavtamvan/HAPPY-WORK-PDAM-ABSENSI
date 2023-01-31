package id.pdamkotasmg.edms.fitur.suratMasuk.model;

import com.google.gson.annotations.SerializedName;

public class DataItem{

    @SerializedName("sm_tujuan_nama")
    private String smTujuanNama;

    @SerializedName("sm_jenis_surat")
    private String smJenisSurat;

    @SerializedName("sm_arsip_surat")
    private Integer smArsipSurat;

    @SerializedName("sm_file2_ext")
    private Object smFile2Ext;

    @SerializedName("sm_file2")
    private String smFile2;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("sm_file1")
    private String smFile1;

    @SerializedName("sm_sifat")
    private String smSifat;

    @SerializedName("sm_created_by")
    private String smCreatedBy;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("sm_file1_ext")
    private Object smFile1Ext;

    @SerializedName("sm_asal_kode")
    private Object smAsalKode;

    @SerializedName("sm_status_surat")
    private Integer smStatusSurat;

    @SerializedName("id")
    private Integer id;

    @SerializedName("sm_tujuan_id")
    private Integer smTujuanId;

    @SerializedName("sm_status_disposisi")
    private Integer smStatusDisposisi;

    @SerializedName("sm_tujuan_kode")
    private Object smTujuanKode;

    @SerializedName("sm_tujuan_kode_parent")
    private Object smTujuanKodeParent;

    @SerializedName("sm_index")
    private Integer smIndex;

    @SerializedName("sm_kode_agenda")
    private Object smKodeAgenda;

    @SerializedName("sm_no_surat")
    private String smNoSurat;

    @SerializedName("sm_tgl_surat")
    private String smTglSurat;

    @SerializedName("sm_asal_id")
    private Integer smAsalId;

    @SerializedName("sm_no_urut")
    private Integer smNoUrut;

    @SerializedName("sm_no_agenda")
    private String smNoAgenda;

    @SerializedName("sm_tgl_terima")
    private String smTglTerima;

    @SerializedName("sm_asal_alamat")
    private String smAsalAlamat;

    @SerializedName("sm_asal_kode_parent")
    private Object smAsalKodeParent;

    @SerializedName("sm_perihal")
    private String smPerihal;

    @SerializedName("sm_asal_nama")
    private String smAsalNama;

    @SerializedName("sm_trx")
    private String smTrx;

    public void setSmTujuanNama(String smTujuanNama){
        this.smTujuanNama = smTujuanNama;
    }

    public String getSmTujuanNama(){
        return smTujuanNama;
    }

    public void setSmJenisSurat(String smJenisSurat){
        this.smJenisSurat = smJenisSurat;
    }

    public String getSmJenisSurat(){
        return smJenisSurat;
    }

    public void setSmArsipSurat(Integer smArsipSurat){
        this.smArsipSurat = smArsipSurat;
    }

    public Integer getSmArsipSurat(){
        return smArsipSurat;
    }

    public void setSmFile2Ext(Object smFile2Ext){
        this.smFile2Ext = smFile2Ext;
    }

    public Object getSmFile2Ext(){
        return smFile2Ext;
    }

    public void setSmFile2(String smFile2){
        this.smFile2 = smFile2;
    }

    public String getSmFile2(){
        return smFile2;
    }

    public void setCreatedAt(String createdAt){
        this.createdAt = createdAt;
    }

    public String getCreatedAt(){
        return createdAt;
    }

    public void setSmFile1(String smFile1){
        this.smFile1 = smFile1;
    }

    public String getSmFile1(){
        return smFile1;
    }

    public void setSmSifat(String smSifat){
        this.smSifat = smSifat;
    }

    public String getSmSifat(){
        return smSifat;
    }

    public void setSmCreatedBy(String smCreatedBy){
        this.smCreatedBy = smCreatedBy;
    }

    public String getSmCreatedBy(){
        return smCreatedBy;
    }

    public void setUpdatedAt(String updatedAt){
        this.updatedAt = updatedAt;
    }

    public String getUpdatedAt(){
        return updatedAt;
    }

    public void setSmFile1Ext(Object smFile1Ext){
        this.smFile1Ext = smFile1Ext;
    }

    public Object getSmFile1Ext(){
        return smFile1Ext;
    }

    public void setSmAsalKode(Object smAsalKode){
        this.smAsalKode = smAsalKode;
    }

    public Object getSmAsalKode(){
        return smAsalKode;
    }

    public void setSmStatusSurat(Integer smStatusSurat){
        this.smStatusSurat = smStatusSurat;
    }

    public Integer getSmStatusSurat(){
        return smStatusSurat;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public Integer getId(){
        return id;
    }

    public void setSmTujuanId(Integer smTujuanId){
        this.smTujuanId = smTujuanId;
    }

    public Integer getSmTujuanId(){
        return smTujuanId;
    }

    public void setSmStatusDisposisi(Integer smStatusDisposisi){
        this.smStatusDisposisi = smStatusDisposisi;
    }

    public Integer getSmStatusDisposisi(){
        return smStatusDisposisi;
    }

    public void setSmTujuanKode(Object smTujuanKode){
        this.smTujuanKode = smTujuanKode;
    }

    public Object getSmTujuanKode(){
        return smTujuanKode;
    }

    public void setSmTujuanKodeParent(Object smTujuanKodeParent){
        this.smTujuanKodeParent = smTujuanKodeParent;
    }

    public Object getSmTujuanKodeParent(){
        return smTujuanKodeParent;
    }

    public void setSmIndex(Integer smIndex){
        this.smIndex = smIndex;
    }

    public Integer getSmIndex(){
        return smIndex;
    }

    public void setSmKodeAgenda(Object smKodeAgenda){
        this.smKodeAgenda = smKodeAgenda;
    }

    public Object getSmKodeAgenda(){
        return smKodeAgenda;
    }

    public void setSmNoSurat(String smNoSurat){
        this.smNoSurat = smNoSurat;
    }

    public String getSmNoSurat(){
        return smNoSurat;
    }

    public void setSmTglSurat(String smTglSurat){
        this.smTglSurat = smTglSurat;
    }

    public String getSmTglSurat(){
        return smTglSurat;
    }

    public void setSmAsalId(Integer smAsalId){
        this.smAsalId = smAsalId;
    }

    public Integer getSmAsalId(){
        return smAsalId;
    }

    public void setSmNoUrut(Integer smNoUrut){
        this.smNoUrut = smNoUrut;
    }

    public Integer getSmNoUrut(){
        return smNoUrut;
    }

    public void setSmNoAgenda(String smNoAgenda){
        this.smNoAgenda = smNoAgenda;
    }

    public String getSmNoAgenda(){
        return smNoAgenda;
    }

    public void setSmTglTerima(String smTglTerima){
        this.smTglTerima = smTglTerima;
    }

    public String getSmTglTerima(){
        return smTglTerima;
    }

    public void setSmAsalAlamat(String smAsalAlamat){
        this.smAsalAlamat = smAsalAlamat;
    }

    public String getSmAsalAlamat(){
        return smAsalAlamat;
    }

    public void setSmAsalKodeParent(Object smAsalKodeParent){
        this.smAsalKodeParent = smAsalKodeParent;
    }

    public Object getSmAsalKodeParent(){
        return smAsalKodeParent;
    }

    public void setSmPerihal(String smPerihal){
        this.smPerihal = smPerihal;
    }

    public String getSmPerihal(){
        return smPerihal;
    }

    public void setSmAsalNama(String smAsalNama){
        this.smAsalNama = smAsalNama;
    }

    public String getSmAsalNama(){
        return smAsalNama;
    }

    public void setSmTrx(String smTrx){
        this.smTrx = smTrx;
    }

    public String getSmTrx(){
        return smTrx;
    }
}