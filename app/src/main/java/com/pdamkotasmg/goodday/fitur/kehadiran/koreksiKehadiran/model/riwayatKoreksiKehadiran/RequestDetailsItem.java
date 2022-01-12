package com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran.model.riwayatKoreksiKehadiran;

import com.google.gson.annotations.SerializedName;

public class RequestDetailsItem{

    @SerializedName("shift_before")
    private String shiftBefore;

    @SerializedName("record_date_before")
    private String recordDateBefore;

    @SerializedName("reason")
    private String reason;

    @SerializedName("time_out_before")
    private String timeOutBefore;

    @SerializedName("time_in_after")
    private String timeInAfter;

    @SerializedName("record_date_after")
    private String recordDateAfter;

    @SerializedName("shift_after")
    private String shiftAfter;

    @SerializedName("time_out_after")
    private String timeOutAfter;

    @SerializedName("time_in_before")
    private String timeInBefore;

    public void setShiftBefore(String shiftBefore){
        this.shiftBefore = shiftBefore;
    }

    public String getShiftBefore(){
        return shiftBefore;
    }

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

    public void setTimeOutBefore(String timeOutBefore){
        this.timeOutBefore = timeOutBefore;
    }

    public String getTimeOutBefore(){
        return timeOutBefore;
    }

    public void setTimeInAfter(String timeInAfter){
        this.timeInAfter = timeInAfter;
    }

    public String getTimeInAfter(){
        return timeInAfter;
    }

    public void setRecordDateAfter(String recordDateAfter){
        this.recordDateAfter = recordDateAfter;
    }

    public String getRecordDateAfter(){
        return recordDateAfter;
    }

    public void setShiftAfter(String shiftAfter){
        this.shiftAfter = shiftAfter;
    }

    public String getShiftAfter(){
        return shiftAfter;
    }

    public void setTimeOutAfter(String timeOutAfter){
        this.timeOutAfter = timeOutAfter;
    }

    public String getTimeOutAfter(){
        return timeOutAfter;
    }

    public void setTimeInBefore(String timeInBefore){
        this.timeInBefore = timeInBefore;
    }

    public String getTimeInBefore(){
        return timeInBefore;
    }
}