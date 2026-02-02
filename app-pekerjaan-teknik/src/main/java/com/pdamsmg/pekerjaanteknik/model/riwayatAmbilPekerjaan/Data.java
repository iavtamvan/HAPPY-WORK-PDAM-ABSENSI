package com.pdamsmg.pekerjaanteknik.model.riwayatAmbilPekerjaan;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Data{

    @SerializedName("data")
    private List<DataItem> data;

    @SerializedName("tenaga_name_npp")
    private String tenagaNameNpp;

    public void setData(List<DataItem> data){
        this.data = data;
    }

    public List<DataItem> getData(){
        return data;
    }

    public void setTenagaNameNpp(String tenagaNameNpp){
        this.tenagaNameNpp = tenagaNameNpp;
    }

    public String getTenagaNameNpp(){
        return tenagaNameNpp;
    }
}