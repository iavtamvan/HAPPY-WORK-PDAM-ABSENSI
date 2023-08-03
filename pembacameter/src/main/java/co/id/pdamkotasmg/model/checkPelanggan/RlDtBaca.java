package co.id.pdamkotasmg.model.checkPelanggan;

import com.google.gson.annotations.SerializedName;

public class RlDtBaca{

    @SerializedName("kode")
    private String kode;

    @SerializedName("nm_status")
    private String nmStatus;

    public void setKode(String kode){
        this.kode = kode;
    }

    public String getKode(){
        return kode;
    }

    public void setNmStatus(String nmStatus){
        this.nmStatus = nmStatus;
    }

    public String getNmStatus(){
        return nmStatus;
    }
}