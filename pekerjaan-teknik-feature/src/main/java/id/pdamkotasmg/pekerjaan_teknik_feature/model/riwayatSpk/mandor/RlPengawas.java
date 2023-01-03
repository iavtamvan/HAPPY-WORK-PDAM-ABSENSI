package id.pdamkotasmg.pekerjaan_teknik_feature.model.riwayatSpk.mandor;

import com.google.gson.annotations.SerializedName;

public class RlPengawas{

    @SerializedName("nama")
    private String nama;

    @SerializedName("subsatker")
    private String subsatker;

    @SerializedName("satker")
    private String satker;

    @SerializedName("satker_formatted")
    private String satkerFormatted;

    @SerializedName("subsatker_formatted")
    private String subsatkerFormatted;

    @SerializedName("npp")
    private String npp;

    public void setNama(String nama){
        this.nama = nama;
    }

    public String getNama(){
        return nama;
    }

    public void setSubsatker(String subsatker){
        this.subsatker = subsatker;
    }

    public String getSubsatker(){
        return subsatker;
    }

    public void setSatker(String satker){
        this.satker = satker;
    }

    public String getSatker(){
        return satker;
    }

    public void setSatkerFormatted(String satkerFormatted){
        this.satkerFormatted = satkerFormatted;
    }

    public String getSatkerFormatted(){
        return satkerFormatted;
    }

    public void setSubsatkerFormatted(String subsatkerFormatted){
        this.subsatkerFormatted = subsatkerFormatted;
    }

    public String getSubsatkerFormatted(){
        return subsatkerFormatted;
    }

    public void setNpp(String npp){
        this.npp = npp;
    }

    public String getNpp(){
        return npp;
    }
}