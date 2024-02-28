package com.pdamkotasmg.goodday.fitur.menuLainnya.profilePelanggan.model.kardek;

import com.google.gson.annotations.SerializedName;

public class Data{

    @SerializedName("queryKardekDataBacaanTahunLalu")
    private QueryKardekDataBacaanTahunLalu queryKardekDataBacaanTahunLalu;

    @SerializedName("queryKardekDataBacaan")
    private QueryKardekDataBacaan queryKardekDataBacaan;

    @SerializedName("foto_meter")
    private FotoMeter fotoMeter;

    public void setQueryKardekDataBacaanTahunLalu(QueryKardekDataBacaanTahunLalu queryKardekDataBacaanTahunLalu){
        this.queryKardekDataBacaanTahunLalu = queryKardekDataBacaanTahunLalu;
    }

    public QueryKardekDataBacaanTahunLalu getQueryKardekDataBacaanTahunLalu(){
        return queryKardekDataBacaanTahunLalu;
    }

    public void setQueryKardekDataBacaan(QueryKardekDataBacaan queryKardekDataBacaan){
        this.queryKardekDataBacaan = queryKardekDataBacaan;
    }

    public QueryKardekDataBacaan getQueryKardekDataBacaan(){
        return queryKardekDataBacaan;
    }

    public void setFotoMeter(FotoMeter fotoMeter){
        this.fotoMeter = fotoMeter;
    }

    public FotoMeter getFotoMeter(){
        return fotoMeter;
    }
}