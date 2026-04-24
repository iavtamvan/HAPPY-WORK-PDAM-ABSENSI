package co.id.pdamkotasmg.pekerjaanteknik.model.callCenter.mastCCSatker;

import com.google.gson.annotations.SerializedName;

public class MKategoriItem{

    @SerializedName("nama_kat")
    private String namaKat;

    @SerializedName("id")
    private String id;

    public void setNamaKat(String namaKat){
        this.namaKat = namaKat;
    }

    public String getNamaKat(){
        return namaKat;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }
}