package co.id.pdamkotasmg.model.riwayatBacaMeter;

import com.google.gson.annotations.SerializedName;

public class RlStatusMeter{

    @SerializedName("kode")
    private String kode;

    @SerializedName("status")
    private String status;

    public void setKode(String kode){
        this.kode = kode;
    }

    public String getKode(){
        return kode;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }
}