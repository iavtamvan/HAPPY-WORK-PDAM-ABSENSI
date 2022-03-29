package com.pdamkotasmg.goodday.fitur.kehadiran.perjalananDinas.model.detailPerjalananDinas;

import com.google.gson.annotations.SerializedName;

public class RequestDetailsItem{

    @SerializedName("date_start")
    private String dateStart;

    @SerializedName("destination")
    private String destination;

    @SerializedName("date_end")
    private String dateEnd;

    public void setDateStart(String dateStart){
        this.dateStart = dateStart;
    }

    public String getDateStart(){
        return dateStart;
    }

    public void setDestination(String destination){
        this.destination = destination;
    }

    public String getDestination(){
        return destination;
    }

    public void setDateEnd(String dateEnd){
        this.dateEnd = dateEnd;
    }

    public String getDateEnd(){
        return dateEnd;
    }
}