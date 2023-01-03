package id.pdamkotasmg.pekerjaan_teknik_feature.model.listAll;

import com.google.gson.annotations.SerializedName;

public class AsalItem{

    @SerializedName("kd_asal")
    private String kdAsal;

    @SerializedName("nm_asal")
    private String nmAsal;

    public void setKdAsal(String kdAsal){
        this.kdAsal = kdAsal;
    }

    public String getKdAsal(){
        return kdAsal;
    }

    public void setNmAsal(String nmAsal){
        this.nmAsal = nmAsal;
    }

    public String getNmAsal(){
        return nmAsal;
    }
}