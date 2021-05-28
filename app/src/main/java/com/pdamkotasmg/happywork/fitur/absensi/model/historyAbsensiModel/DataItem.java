package com.pdamkotasmg.happywork.fitur.absensi.model.absensiModel;

import com.google.gson.annotations.SerializedName;

public class DataItem{

    @SerializedName("coord")
    private String coord;

    @SerializedName("record_date")
    private String recordDate;

    @SerializedName("attendance_location_data")
    private AttendanceLocationData attendanceLocationData;

    @SerializedName("is_shift_in")
    private Integer isShiftIn;

    @SerializedName("photo")
    private String photo;

    @SerializedName("remark")
    private Object remark;

    @SerializedName("_id")
    private String id;

    @SerializedName("record_time")
    private String recordTime;

    @SerializedName("npp")
    private String npp;

    @SerializedName("shift_daily_data")
    private ShiftDailyData shiftDailyData;

    public void setCoord(String coord){
        this.coord = coord;
    }

    public String getCoord(){
        return coord;
    }

    public void setRecordDate(String recordDate){
        this.recordDate = recordDate;
    }

    public String getRecordDate(){
        return recordDate;
    }

    public void setAttendanceLocationData(AttendanceLocationData attendanceLocationData){
        this.attendanceLocationData = attendanceLocationData;
    }

    public AttendanceLocationData getAttendanceLocationData(){
        return attendanceLocationData;
    }

    public void setIsShiftIn(Integer isShiftIn){
        this.isShiftIn = isShiftIn;
    }

    public Integer getIsShiftIn(){
        return isShiftIn;
    }

    public void setPhoto(String photo){
        this.photo = photo;
    }

    public String getPhoto(){
        return photo;
    }

    public void setRemark(Object remark){
        this.remark = remark;
    }

    public Object getRemark(){
        return remark;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }

    public void setRecordTime(String recordTime){
        this.recordTime = recordTime;
    }

    public String getRecordTime(){
        return recordTime;
    }

    public void setNpp(String npp){
        this.npp = npp;
    }

    public String getNpp(){
        return npp;
    }

    public void setShiftDailyData(ShiftDailyData shiftDailyData){
        this.shiftDailyData = shiftDailyData;
    }

    public ShiftDailyData getShiftDailyData(){
        return shiftDailyData;
    }
}