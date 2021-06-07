package com.pdamkotasmg.happywork.fitur.presensi.model.checkLocationModel;

import com.google.gson.annotations.SerializedName;

public class AppliesLocationSetting{

    @SerializedName("code")
    private String code;

    @SerializedName("address")
    private Object address;

    @SerializedName("radius_m")
    private Integer radiusM;

    @SerializedName("latitude")
    private Double latitude;

    @SerializedName("name")
    private String name;

    @SerializedName("longitude")
    private Double longitude;

    public void setCode(String code){
        this.code = code;
    }

    public String getCode(){
        return code;
    }

    public void setAddress(Object address){
        this.address = address;
    }

    public Object getAddress(){
        return address;
    }

    public void setRadiusM(Integer radiusM){
        this.radiusM = radiusM;
    }

    public Integer getRadiusM(){
        return radiusM;
    }

    public void setLatitude(Double latitude){
        this.latitude = latitude;
    }

    public Double getLatitude(){
        return latitude;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setLongitude(Double longitude){
        this.longitude = longitude;
    }

    public Double getLongitude(){
        return longitude;
    }
}