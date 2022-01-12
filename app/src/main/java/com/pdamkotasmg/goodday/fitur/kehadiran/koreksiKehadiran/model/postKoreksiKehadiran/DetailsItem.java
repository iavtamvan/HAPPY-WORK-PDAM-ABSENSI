package com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran.model.postKoreksiKehadiran;

import com.google.gson.annotations.SerializedName;

public class DetailsItem{

    @SerializedName("record_date_before")
    private String recordDateBefore;

    @SerializedName("reason")
    private String reason;

    @SerializedName("actual_time_out_after")
    private String actualTimeOutAfter;

    @SerializedName("actual_time_in_after")
    private String actualTimeInAfter;

    public void setRecordDateBefore(String recordDateBefore){
        this.recordDateBefore = recordDateBefore;
    }

    public String getRecordDateBefore(){
        return recordDateBefore;
    }

    public void setReason(String reason){
        this.reason = reason;
    }

    public String getReason(){
        return reason;
    }

    public void setActualTimeOutAfter(String actualTimeOutAfter){
        this.actualTimeOutAfter = actualTimeOutAfter;
    }

    public String getActualTimeOutAfter(){
        return actualTimeOutAfter;
    }

    public void setActualTimeInAfter(String actualTimeInAfter){
        this.actualTimeInAfter = actualTimeInAfter;
    }

    public String getActualTimeInAfter(){
        return actualTimeInAfter;
    }
}