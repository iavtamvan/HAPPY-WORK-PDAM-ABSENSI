package com.pdamkotasmg.happywork.fitur.absensi.model.checkLocationModel;

import com.google.gson.annotations.SerializedName;

public class Data{

    @SerializedName("check_result")
    private CheckResult checkResult;

    @SerializedName("map_url")
    private String mapUrl;

    @SerializedName("applies_location_setting")
    private AppliesLocationSetting appliesLocationSetting;

    public void setCheckResult(CheckResult checkResult){
        this.checkResult = checkResult;
    }

    public CheckResult getCheckResult(){
        return checkResult;
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
}