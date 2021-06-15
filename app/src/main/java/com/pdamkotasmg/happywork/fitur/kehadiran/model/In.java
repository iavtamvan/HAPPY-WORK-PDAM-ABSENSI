package com.pdamkotasmg.happywork.fitur.kehadiran.model;

import com.google.gson.annotations.SerializedName;

public class In{

    @SerializedName("is_pulang_awal")
    private String isPulangAwal;

    @SerializedName("is_shift_in")
    private String isShiftIn;

    @SerializedName("photo")
    private String photo;

    @SerializedName("record_time")
    private String recordTime;

    @SerializedName("is_telat")
    private String isTelat;

    public void setIsPulangAwal(String isPulangAwal){
        this.isPulangAwal = isPulangAwal;
    }

    public String isIsPulangAwal(){
        return isPulangAwal;
    }

    public void setIsShiftIn(String isShiftIn){
        this.isShiftIn = isShiftIn;
    }

    public String isIsShiftIn(){
        return isShiftIn;
    }

    public void setPhoto(String photo){
        this.photo = photo;
    }

    public String getPhoto(){
        return photo;
    }

    public void setRecordTime(String recordTime){
        this.recordTime = recordTime;
    }

    public String getRecordTime(){
        return recordTime;
    }

    public void setIsTelat(String isTelat){
        this.isTelat = isTelat;
    }

    public String isIsTelat(){
        return isTelat;
    }
}