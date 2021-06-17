package com.pdamkotasmg.goodday.fitur.authentication.login.model;

import com.google.gson.annotations.SerializedName;

public class Device{

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

    @SerializedName("ip_address")
    private String ipAddress;

    @SerializedName("ssid")
    private String ssid;

    @SerializedName("npp")
    private String npp;

    @SerializedName("user_id")
    private Integer userId;

    @SerializedName("location_city")
    private String locationCity;

    @SerializedName("sdk_version")
    private String sdkVersion;

    @SerializedName("build_number")
    private String buildNumber;

    @SerializedName("model")
    private String model;

    @SerializedName("hwid")
    private String hwid;

    @SerializedName("id")
    private Integer id;

    @SerializedName("device")
    private String device;

    @SerializedName("longitude")
    private String longitude;

    @SerializedName("app_version")
    private String appVersion;

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public void setProduct(String product){
        this.product = product;
    }

    public String getProduct(){
        return product;
    }

    public void setBuildIncremental(String buildIncremental){
        this.buildIncremental = buildIncremental;
    }

    public String getBuildIncremental(){
        return buildIncremental;
    }

    public void setConnectionType(String connectionType){
        this.connectionType = connectionType;
    }

    public String getConnectionType(){
        return connectionType;
    }

    public void setBuildBrand(String buildBrand){
        this.buildBrand = buildBrand;
    }

    public String getBuildBrand(){
        return buildBrand;
    }

    public void setOsVersion(String osVersion){
        this.osVersion = osVersion;
    }

    public String getOsVersion(){
        return osVersion;
    }

    public void setLatitude(String latitude){
        this.latitude = latitude;
    }

    public String getLatitude(){
        return latitude;
    }

    public void setIpAddress(String ipAddress){
        this.ipAddress = ipAddress;
    }

    public String getIpAddress(){
        return ipAddress;
    }

    public void setSsid(String ssid){
        this.ssid = ssid;
    }

    public String getSsid(){
        return ssid;
    }

    public void setNpp(String npp){
        this.npp = npp;
    }

    public String getNpp(){
        return npp;
    }

    public void setUserId(Integer userId){
        this.userId = userId;
    }

    public Integer getUserId(){
        return userId;
    }

    public void setLocationCity(String locationCity){
        this.locationCity = locationCity;
    }

    public String getLocationCity(){
        return locationCity;
    }

    public void setSdkVersion(String sdkVersion){
        this.sdkVersion = sdkVersion;
    }

    public String getSdkVersion(){
        return sdkVersion;
    }

    public void setBuildNumber(String buildNumber){
        this.buildNumber = buildNumber;
    }

    public String getBuildNumber(){
        return buildNumber;
    }

    public void setModel(String model){
        this.model = model;
    }

    public String getModel(){
        return model;
    }

    public void setHwid(String hwid){
        this.hwid = hwid;
    }

    public String getHwid(){
        return hwid;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public Integer getId(){
        return id;
    }

    public void setDevice(String device){
        this.device = device;
    }

    public String getDevice(){
        return device;
    }

    public void setLongitude(String longitude){
        this.longitude = longitude;
    }

    public String getLongitude(){
        return longitude;
    }
}