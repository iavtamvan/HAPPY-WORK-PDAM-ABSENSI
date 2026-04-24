package co.id.pdamkotasmg.model.listGabungan;

import com.google.gson.annotations.SerializedName;

public class MerkItem{

    @SerializedName("kd_merek")
    private String kdMerek;

    @SerializedName("nm_merek")
    private String nmMerek;

    public void setKdMerek(String kdMerek){
        this.kdMerek = kdMerek;
    }

    public String getKdMerek(){
        return kdMerek;
    }

    public void setNmMerek(String nmMerek){
        this.nmMerek = nmMerek;
    }

    public String getNmMerek(){
        return nmMerek;
    }
}