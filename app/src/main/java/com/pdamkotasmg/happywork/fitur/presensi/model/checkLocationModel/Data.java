package com.pdamkotasmg.happywork.fitur.presensi.model.checkLocationModel;

import com.google.gson.annotations.SerializedName;

public class Data{

    @SerializedName("hwname")
    private String hwname;

    @SerializedName("check_result")
    private CheckResult checkResult;

    @SerializedName("name")
    private String name;

    @SerializedName("applies_shift_setting")
    private AppliesShiftSetting appliesShiftSetting;

    @SerializedName("map_url")
    private String mapUrl;

    @SerializedName("applies_location_setting")
    private AppliesLocationSetting appliesLocationSetting;

    @SerializedName("npp")
    private String npp;

    public void setHwname(String hwname){
        this.hwname = hwname;
    }

    public String getHwname(){
        return hwname;
    }

    public void setCheckResult(CheckResult checkResult){
        this.checkResult = checkResult;
    }

    public CheckResult getCheckResult(){
        return checkResult;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setAppliesShiftSetting(AppliesShiftSetting appliesShiftSetting){
        this.appliesShiftSetting = appliesShiftSetting;
    }

    public AppliesShiftSetting getAppliesShiftSetting(){
        return appliesShiftSetting;
    }

    public void setMapUrl(String mapUrl){
        this.mapUrl = mapUrl;
    }

    public String getMapUrl(){
        return mapUrl;
    }

    public void setAppliesLocationSetting(AppliesLocationSetting appliesLocationSetting){
        this.appliesLocationSetting = appliesLocationSetting;
    }

    public AppliesLocationSetting getAppliesLocationSetting(){
        return appliesLocationSetting;
    }

    public void setNpp(String npp){
        this.npp = npp;
    }

    public String getNpp(){
        return npp;
    }
}