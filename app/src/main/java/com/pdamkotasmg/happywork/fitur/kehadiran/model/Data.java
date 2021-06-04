package com.pdamkotasmg.happywork.fitur.kehadiran.model;

import com.google.gson.annotations.SerializedName;

public class Data{

    @SerializedName("shift_daily_code")
    private String shiftDailyCode;

    @SerializedName("start_time")
    private String startTime;

    @SerializedName("end_time")
    private String endTime;

    @SerializedName("remark")
    private String remark;

    @SerializedName("can_offline")
    private Boolean canOffline;

    @SerializedName("location_detection")
    private Boolean locationDetection;

    public void setShiftDailyCode(String shiftDailyCode){
        this.shiftDailyCode = shiftDailyCode;
    }

    public String getShiftDailyCode(){
        return shiftDailyCode;
    }

    public void setStartTime(String startTime){
        this.startTime = startTime;
    }

    public String getStartTime(){
        return startTime;
    }

    public void setEndTime(String endTime){
        this.endTime = endTime;
    }

    public String getEndTime(){
        return endTime;
    }

    public void setRemark(String remark){
        this.remark = remark;
    }

    public String getRemark(){
        return remark;
    }

    public void setCanOffline(Boolean canOffline){
        this.canOffline = canOffline;
    }

    public boolean isCanOffline(){
        return canOffline;
    }

    public void setLocationDetection(Boolean locationDetection){
        this.locationDetection = locationDetection;
    }

    public boolean isLocationDetection(){
        return locationDetection;
    }
}