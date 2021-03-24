package com.pdamkotasmg.happywork.fitur.perangkat.model;

import com.google.gson.annotations.SerializedName;

public class DataItem{

    @SerializedName("product")
    private String product;

    @SerializedName("build_incremental")
    private String buildIncremental;

    @SerializedName("connection_type")
    private String connectionType;

    @SerializedName("build_brand")
    private String buildBrand;

    @SerializedName("os_version")
    private String osVersion;

    @SerializedName("latitude")
    private String latitude;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("ip_address")
    private String ipAddress;

    @SerializedName("ssid")
    private String ssid;

    @SerializedName("npp")
    private String npp;

    @SerializedName("session_token")
    private String sessionToken;

    @SerializedName("user_id")
    private int userId;

    @SerializedName("location_city")
    private String locationCity;

    @SerializedName("sdk_version")
    private String sdkVersion;

    @SerializedName("build_number")
    private String buildNumber;

    @SerializedName("model")
    private String model;

    @SerializedName("id")
    private int id;

    @SerializedName("hwid")
    private String hwid;

    @SerializedName("device")
    private String device;

    @SerializedName("longitude")
    private String longitude;

    public String getProduct(){
        return product;
    }

    public String getBuildIncremental(){
        return buildIncremental;
    }

    public String getConnectionType(){
        return connectionType;
    }

    public String getBuildBrand(){
        return buildBrand;
    }

    public String getOsVersion(){
        return osVersion;
    }

    public String getLatitude(){
        return latitude;
    }

    public String getCreatedAt(){
        return createdAt;
    }

    public String getIpAddress(){
        return ipAddress;
    }

    public String getSsid(){
        return ssid;
    }

    public String getNpp(){
        return npp;
    }

    public String getSessionToken(){
        return sessionToken;
    }

    public int getUserId(){
        return userId;
    }

    public String getLocationCity(){
        return locationCity;
    }

    public String getSdkVersion(){
        return sdkVersion;
    }

    public String getBuildNumber(){
        return buildNumber;
    }

    public String getModel(){
        return model;
    }

    public int getId(){
        return id;
    }

    public String getHwid(){
        return hwid;
    }

    public String getDevice(){
        return device;
    }

    public String getLongitude(){
        return longitude;
    }
}