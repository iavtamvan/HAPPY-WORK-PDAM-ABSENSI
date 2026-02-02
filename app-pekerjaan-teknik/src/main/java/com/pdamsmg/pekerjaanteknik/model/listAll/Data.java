package com.pdamsmg.pekerjaanteknik.model.listAll;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Data{

    @SerializedName("asal")
    private List<AsalItem> asal;

    @SerializedName("pekerjaan_dikerjakan")
    private List<PekerjaanDikerjakanItem> pekerjaanDikerjakan;

    @SerializedName("jenis_pekerjaan")
    private List<JenisPekerjaanItem> jenisPekerjaan;

    @SerializedName("status_penyelesaian")
    private List<StatusPenyelesaianItem> statusPenyelesaian;

    @SerializedName("tenaga")
    private List<TenagaItem> tenaga;

    public void setAsal(List<AsalItem> asal){
        this.asal = asal;
    }

    public List<AsalItem> getAsal(){
        return asal;
    }

    public void setPekerjaanDikerjakan(List<PekerjaanDikerjakanItem> pekerjaanDikerjakan){
        this.pekerjaanDikerjakan = pekerjaanDikerjakan;
    }

    public List<PekerjaanDikerjakanItem> getPekerjaanDikerjakan(){
        return pekerjaanDikerjakan;
    }

    public void setJenisPekerjaan(List<JenisPekerjaanItem> jenisPekerjaan){
        this.jenisPekerjaan = jenisPekerjaan;
    }

    public List<JenisPekerjaanItem> getJenisPekerjaan(){
        return jenisPekerjaan;
    }

    public void setStatusPenyelesaian(List<StatusPenyelesaianItem> statusPenyelesaian){
        this.statusPenyelesaian = statusPenyelesaian;
    }

    public List<StatusPenyelesaianItem> getStatusPenyelesaian(){
        return statusPenyelesaian;
    }

    public void setTenaga(List<TenagaItem> tenaga){
        this.tenaga = tenaga;
    }

    public List<TenagaItem> getTenaga(){
        return tenaga;
    }
}