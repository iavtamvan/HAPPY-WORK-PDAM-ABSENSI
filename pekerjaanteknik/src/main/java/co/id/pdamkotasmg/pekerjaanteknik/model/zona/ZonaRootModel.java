package co.id.pdamkotasmg.pekerjaanteknik.model.zona;

import com.google.gson.annotations.SerializedName;

import java.util.List;

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