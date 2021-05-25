package com.pdamkotasmg.happywork.fitur.absensi.model.checkLocationModel;

import com.google.gson.annotations.SerializedName;

public class AppliesLocationSetting{

    @SerializedName("code")
    private String code;

    @SerializedName("address")
    private Object address;

    @SerializedName("radius_m")
    private Integer radiusM;

    @SerializedName("latitude")
    private String latitude;

    @SerializedName("name")
    private String name;

    @SerializedName("longitude")
    private String longitude;

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

    public void setLatitude(String latitude){
        this.latitude = latitude;
    }

    public String getLatitude(){
        return latitude;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setLongitude(String longitude){
        this.longitude = longitude;
    }

    public String getLongitude(){
        return longitude;
    }
}