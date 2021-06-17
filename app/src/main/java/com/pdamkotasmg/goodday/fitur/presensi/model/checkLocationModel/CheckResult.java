package com.pdamkotasmg.goodday.fitur.presensi.model.checkLocationModel;

import com.google.gson.annotations.SerializedName;

public class CheckResult{

    @SerializedName("is_in_radius")
    private Boolean isInRadius;

    @SerializedName("distance_m")
    private Double distanceM;

    public void setIsInRadius(Boolean isInRadius){
        this.isInRadius = isInRadius;
    }

    public boolean isIsInRadius(){
        return isInRadius;
    }

    public void setDistanceM(Double distanceM){
        this.distanceM = distanceM;
    }

    public Double getDistanceM(){
        return distanceM;
    }
}