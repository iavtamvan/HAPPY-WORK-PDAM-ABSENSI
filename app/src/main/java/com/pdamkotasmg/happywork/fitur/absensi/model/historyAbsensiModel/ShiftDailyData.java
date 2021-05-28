package com.pdamkotasmg.happywork.fitur.absensi.model.historyAbsensiModel;

import com.google.gson.annotations.SerializedName;

public class ShiftDailyData{

    @SerializedName("shift_daily_code")
    private String shiftDailyCode;

    @SerializedName("start_time")
    private String startTime;

    @SerializedName("end_time")
    private String endTime;

    @SerializedName("remark")
    private String remark;

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
}