package com.pdamkotasmg.happywork.fitur.absensi.model.saveAbsensiModel;

import com.google.gson.annotations.SerializedName;

public class Data{

    @SerializedName("check_face")
    private String checkFace;

    @SerializedName("shift_remark")
    private String shiftRemark;

    @SerializedName("face_match_percent")
    private Integer faceMatchPercent;

    @SerializedName("record_date")
    private String recordDate;

    @SerializedName("face_match")
    private Boolean faceMatch;

    @SerializedName("is_shift_in")
    private Boolean isShiftIn;

    @SerializedName("photo")
    private String photo;

    @SerializedName("face_detected")
    private Boolean faceDetected;

    @SerializedName("npp")
    private String npp;

    @SerializedName("shift_time")
    private String shiftTime;

    @SerializedName("coord")
    private String coord;

    @SerializedName("shift_code")
    private String shiftCode;

    @SerializedName("record_time")
    private String recordTime;

    @SerializedName("attendance_status")
    private String attendanceStatus;

    public void setCheckFace(String checkFace){
        this.checkFace = checkFace;
    }

    public String getCheckFace(){
        return checkFace;
    }

    public void setShiftRemark(String shiftRemark){
        this.shiftRemark = shiftRemark;
    }

    public String getShiftRemark(){
        return shiftRemark;
    }

    public void setFaceMatchPercent(Integer faceMatchPercent){
        this.faceMatchPercent = faceMatchPercent;
    }

    public Integer getFaceMatchPercent(){
        return faceMatchPercent;
    }

    public void setRecordDate(String recordDate){
        this.recordDate = recordDate;
    }

    public String getRecordDate(){
        return recordDate;
    }

    public void setFaceMatch(Boolean faceMatch){
        this.faceMatch = faceMatch;
    }

    public boolean isFaceMatch(){
        return faceMatch;
    }

    public void setIsShiftIn(Boolean isShiftIn){
        this.isShiftIn = isShiftIn;
    }

    public boolean isIsShiftIn(){
        return isShiftIn;
    }

    public void setPhoto(String photo){
        this.photo = photo;
    }

    public String getPhoto(){
        return photo;
    }

    public void setFaceDetected(Boolean faceDetected){
        this.faceDetected = faceDetected;
    }

    public boolean isFaceDetected(){
        return faceDetected;
    }

    public void setNpp(String npp){
        this.npp = npp;
    }

    public String getNpp(){
        return npp;
    }

    public void setShiftTime(String shiftTime){
        this.shiftTime = shiftTime;
    }

    public String getShiftTime(){
        return shiftTime;
    }

    public void setCoord(String coord){
        this.coord = coord;
    }

    public String getCoord(){
        return coord;
    }

    public void setShiftCode(String shiftCode){
        this.shiftCode = shiftCode;
    }

    public String getShiftCode(){
        return shiftCode;
    }

    public void setRecordTime(String recordTime){
        this.recordTime = recordTime;
    }

    public String getRecordTime(){
        return recordTime;
    }

    public void setAttendanceStatus(String attendanceStatus){
        this.attendanceStatus = attendanceStatus;
    }

    public String getAttendanceStatus(){
        return attendanceStatus;
    }
}