package com.pdamsmg.pekerjaanteknik.model.zona;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ZonaRootModel{

    @SerializedName("ZonaRootModel")
    private List<ZonaRootModelItem> zonaRootModel;

    public void setZonaRootModel(List<ZonaRootModelItem> zonaRootModel){
        this.zonaRootModel = zonaRootModel;
    }

    public List<ZonaRootModelItem> getZonaRootModel(){
        return zonaRootModel;
    }
}