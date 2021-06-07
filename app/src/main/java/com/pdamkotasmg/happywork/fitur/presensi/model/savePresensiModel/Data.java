package com.pdamkotasmg.happywork.fitur.presensi.model.savePresensiModel;

import com.google.gson.annotations.SerializedName;

public class Data{

    @SerializedName("shift_remark")
    private String shiftRemark;

    @SerializedName("location_is_in_radius")
    private Boolean locationIsInRadius;

    @SerializedName("face_match")
    private Boolean faceMatch;

    @SerializedName("is_telat")
    private Boolean isTelat;

    @SerializedName("shift_can_offline")
    private Boolean shiftCanOffline;

    @SerializedName("location_distance_m")
    private Double locationDistanceM;

    @SerializedName("coord")
    private String coord;

    @SerializedName("attendance_status")
    private String attendanceStatus;

    @SerializedName("location_longitude")
    private String locationLongitude;

    @SerializedName("check_face")
    private String checkFace;

    @SerializedName("hwname")
    private String hwname;

    @SerializedName("face_match_percent")
    private Integer faceMatchPercent;

    @SerializedName("record_date")
    private String recordDate;

    @SerializedName("connection_type")
    private String connectionType;

    @SerializedName("location_latitude")
    private String locationLatitude;

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

    @SerializedName("is_pulang_awal")
    private Boolean isPulangAwal;

    @SerializedName("shift_code")
    private String shiftCode;

    @SerializedName("name")
    private String name;

    @SerializedName("shift_location_detection")
    private Boolean shiftLocationDetection;

    @SerializedName("record_time")
    private String recordTime;

    @SerializedName("attendance_diff_minutes")
    private Integer attendanceDiffMinutes;

    public void setShiftRemark(String shiftRemark){
        this.shiftRemark = shiftRemark;
    }

    public String getShiftRemark(){
        return shiftRemark;
    }

    public void setLocationIsInRadius(Boolean locationIsInRadius){
        this.locationIsInRadius = locationIsInRadius;
    }

    public boolean isLocationIsInRadius(){
        return locationIsInRadius;
    }

    public void setFaceMatch(Boolean faceMatch){
        this.faceMatch = faceMatch;
    }

    public boolean isFaceMatch(){
        return faceMatch;
    }

    public void setIsTelat(Boolean isTelat){
        this.isTelat = isTelat;
    }

    public boolean isIsTelat(){
        return isTelat;
    }

    public void setShiftCanOffline(Boolean shiftCanOffline){
        this.shiftCanOffline = shiftCanOffline;
    }

    public boolean isShiftCanOffline(){
        return shiftCanOffline;
    }

    public void setLocationDistanceM(Double locationDistanceM){
        this.locationDistanceM = locationDistanceM;
    }

    public Double getLocationDistanceM(){
        return locationDistanceM;
    }

    public void setCoord(String coord){
        this.coord = coord;
    }

    public String getCoord(){
        return coord;
    }

    public void setAttendanceStatus(String attendanceStatus){
        this.attendanceStatus = attendanceStatus;
    }

    public String getAttendanceStatus(){
        return attendanceStatus;
    }

    public void setLocationLongitude(String locationLongitude){
        this.locationLongitude = locationLongitude;
    }

    public String getLocationLongitude(){
        return locationLongitude;
    }

    public void setCheckFace(String checkFace){
        this.checkFace = checkFace;
    }

    public String getCheckFace(){
        return checkFace;
    }

    public void setHwname(String hwname){
        this.hwname = hwname;
    }

    public String getHwname(){
        return hwname;
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

    public void setConnectionType(String connectionType){
        this.connectionType = connectionType;
    }

    public String getConnectionType(){
        return connectionType;
    }

    public void setLocationLatitude(String locationLatitude){
        this.locationLatitude = locationLatitude;
    }

    public String getLocationLatitude(){
        return locationLatitude;
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

    public void setIsPulangAwal(Boolean isPulangAwal){
        this.isPulangAwal = isPulangAwal;
    }

    public boolean isIsPulangAwal(){
        return isPulangAwal;
    }

    public void setShiftCode(String shiftCode){
        this.shiftCode = shiftCode;
    }

    public String getShiftCode(){
        return shiftCode;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setShiftLocationDetection(Boolean shiftLocationDetection){
        this.shiftLocationDetection = shiftLocationDetection;
    }

    public boolean isShiftLocationDetection(){
        return shiftLocationDetection;
    }

    public void setRecordTime(String recordTime){
        this.recordTime = recordTime;
    }

    public String getRecordTime(){
        return recordTime;
    }

    public void setAttendanceDiffMinutes(Integer attendanceDiffMinutes){
        this.attendanceDiffMinutes = attendanceDiffMinutes;
    }

    public Integer getAttendanceDiffMinutes(){
        return attendanceDiffMinutes;
    }
}