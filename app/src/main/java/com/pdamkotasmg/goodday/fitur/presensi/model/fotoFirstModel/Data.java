package com.pdamkotasmg.goodday.fitur.presensi.model.fotoFirstModel;

import com.google.gson.annotations.SerializedName;

public class Data{

    @SerializedName("is_match")
    private Boolean isMatch;

    @SerializedName("photo")
    private String photo;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("_id")
    private String id;

    @SerializedName("face_detected")
    private Boolean faceDetected;

    @SerializedName("match_percent")
    private Integer matchPercent;

    @SerializedName("npp")
    private String npp;

    public void setIsMatch(Boolean isMatch){
        this.isMatch = isMatch;
    }

    public Boolean isIsMatch(){
        return isMatch;
    }

    public void setPhoto(String photo){
        this.photo = photo;
    }

    public String getPhoto(){
        return photo;
    }

    public void setCreatedAt(String createdAt){
        this.createdAt = createdAt;
    }

    public String getCreatedAt(){
        return createdAt;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }

    public void setFaceDetected(Boolean faceDetected){
        this.faceDetected = faceDetected;
    }

    public Boolean isFaceDetected(){
        return faceDetected;
    }

    public void setMatchPercent(Integer matchPercent){
        this.matchPercent = matchPercent;
    }

    public Integer getMatchPercent(){
        return matchPercent;
    }

    public void setNpp(String npp){
        this.npp = npp;
    }

    public String getNpp(){
        return npp;
    }
}