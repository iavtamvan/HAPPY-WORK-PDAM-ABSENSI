package com.pdamkotasmg.goodday.fitur.kehadiran.home.model;

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

    @SerializedName("shift_code")
    private String shiftCode;

    @SerializedName("shift_remark")
    private String shiftRemark;

    public String getIsPulangAwal() {
        return isPulangAwal;
    }

    public String getIsShiftIn() {
        return isShiftIn;
    }

    public String getIsTelat() {
        return isTelat;
    }

    public String getShiftCode() {
        return shiftCode;
    }

    public void setShiftCode(String shiftCode) {
        this.shiftCode = shiftCode;
    }

    public String getShiftRemark() {
        return shiftRemark;
    }

    public void setShiftRemark(String shiftRemark) {
        this.shiftRemark = shiftRemark;
    }

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