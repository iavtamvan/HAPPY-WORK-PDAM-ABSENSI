package com.pdamkotasmg.goodday.fitur.menuLainnya.profilePelanggan.model.kardek;

import com.google.gson.annotations.SerializedName;

public class Oktober{

    @SerializedName("foto")
    private String foto;

    @SerializedName("m3")
    private Integer m3;

    @SerializedName("stand")
    private Integer stand;

    public void setFoto(String foto){
        this.foto = foto;
    }

    public String getFoto(){
        return foto;
    }

    public void setM3(Integer m3){
        this.m3 = m3;
    }

    public Integer getM3(){
        return m3;
    }

    public void setStand(Integer stand){
        this.stand = stand;
    }

    public Integer getStand(){
        return stand;
    }
}