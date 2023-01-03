package id.pdamkotasmg.pekerjaan_teknik_feature.model.verifikasiSPK.statusVerifikasi;

import com.google.gson.annotations.SerializedName;

public class Data{

    @SerializedName("verif_spk")
    private String verifSpk;

    @SerializedName("verif_spk_pelaksana_detail")
    private String verifSpkPelaksanaDetail;

    @SerializedName("verif_spk_detail")
    private String verifSpkDetail;

    @SerializedName("verif_spk_pelaksana")
    private String verifSpkPelaksana;

    @SerializedName("status")
    private String status;

    public void setVerifSpk(String verifSpk){
        this.verifSpk = verifSpk;
    }

    public String getVerifSpk(){
        return verifSpk;
    }

    public void setVerifSpkPelaksanaDetail(String verifSpkPelaksanaDetail){
        this.verifSpkPelaksanaDetail = verifSpkPelaksanaDetail;
    }

    public String getVerifSpkPelaksanaDetail(){
        return verifSpkPelaksanaDetail;
    }

    public void setVerifSpkDetail(String verifSpkDetail){
        this.verifSpkDetail = verifSpkDetail;
    }

    public String getVerifSpkDetail(){
        return verifSpkDetail;
    }

    public void setVerifSpkPelaksana(String verifSpkPelaksana){
        this.verifSpkPelaksana = verifSpkPelaksana;
    }

    public String getVerifSpkPelaksana(){
        return verifSpkPelaksana;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }
}