package com.pdamkotasmg.happywork.fitur.presensi.model.historyPresensiModel;

import com.google.gson.annotations.SerializedName;

public class AttendanceLocationData{

    @SerializedName("location_name")
    private String locationName;

    @SerializedName("location_code")
    private String locationCode;

    @SerializedName("location_latitude")
    private Double locationLatitude;

    @SerializedName("location_longitude")
    private Double locationLongitude;

    @SerializedName("location_radius")
    private Integer locationRadius;

    public void setLocationName(String locationName){
        this.locationName = locationName;
    }

    public String getLocationName(){
        return locationName;
    }

    public void setLocationCode(String locationCode){
        this.locationCode = locationCode;
    }

    public String getLocationCode(){
        return locationCode;
    }

    public void setLocationLatitude(Double locationLatitude){
        this.locationLatitude = locationLatitude;
    }

    public Double getLocationLatitude(){
        return locationLatitude;
    }

    public void setLocationLongitude(Double locationLongitude){
        this.locationLongitude = locationLongitude;
    }

    public Double getLocationLongitude(){
        return locationLongitude;
    }

    public void setLocationRadius(Integer locationRadius){
        this.locationRadius = locationRadius;
    }

    public Integer getLocationRadius(){
        return locationRadius;
    }
}