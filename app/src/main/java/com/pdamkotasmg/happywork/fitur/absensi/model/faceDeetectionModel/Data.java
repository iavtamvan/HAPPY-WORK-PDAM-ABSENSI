package com.pdamkotasmg.happywork.fitur.absensi.model.faceDeetectionModel;

import com.google.gson.annotations.SerializedName;

public class Data{

    @SerializedName("is_match")
    private Boolean isMatch;

    @SerializedName("photo")
    private String photo;

    @SerializedName("face_detected")
    private Boolean faceDetected;

    @SerializedName("match_percent")
    private Integer matchPercent;

    public void setIsMatch(Boolean isMatch){
        this.isMatch = isMatch;
    }

    public boolean isIsMatch(){
        return isMatch;
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

    public void setMatchPercent(Integer matchPercent){
        this.matchPercent = matchPercent;
    }

    public Integer getMatchPercent(){
        return matchPercent;
    }
}