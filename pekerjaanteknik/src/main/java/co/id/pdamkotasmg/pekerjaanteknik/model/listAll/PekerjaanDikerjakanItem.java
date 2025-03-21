package co.id.pdamkotasmg.pekerjaanteknik.model.listAll;

import com.google.gson.annotations.SerializedName;

public class PekerjaanDikerjakanItem{

    @SerializedName("keterangan")
    private String keterangan;

    @SerializedName("kd")
    private String kd;

    public void setKeterangan(String keterangan){
        this.keterangan = keterangan;
    }

    public String getKeterangan(){
        return keterangan;
    }

    public void setKd(String kd){
        this.kd = kd;
    }

    public String getKd(){
        return kd;
    }
}