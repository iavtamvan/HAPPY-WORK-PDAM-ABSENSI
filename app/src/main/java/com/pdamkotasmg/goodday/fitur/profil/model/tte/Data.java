package com.pdamkotasmg.goodday.fitur.profil.model.tte;

import com.google.gson.annotations.SerializedName;

public class Data{

    @SerializedName("foto_ktp")
    private String fotoKtp;

    @SerializedName("foto_ttd")
    private String fotoTtd;

    public void setFotoKtp(String fotoKtp){
        this.fotoKtp = fotoKtp;
    }

    public String getFotoKtp(){
        return fotoKtp;
    }

    public void setFotoTtd(String fotoTtd){
        this.fotoTtd = fotoTtd;
    }

    public String getFotoTtd(){
        return fotoTtd;
    }
}