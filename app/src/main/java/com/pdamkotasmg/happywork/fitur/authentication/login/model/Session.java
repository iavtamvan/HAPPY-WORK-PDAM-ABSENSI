package com.pdamkotasmg.happywork.fitur.authentication.login.model;

import com.google.gson.annotations.SerializedName;

public class Session{

    @SerializedName("is_expired")
    private Boolean isExpired;

    @SerializedName("session_id")
    private String sessionId;

    @SerializedName("id")
    private Integer id;

    @SerializedName("npp")
    private String npp;

    public void setIsExpired(Boolean isExpired){
        this.isExpired = isExpired;
    }

    public boolean isIsExpired(){
        return isExpired;
    }

    public void setSessionId(String sessionId){
        this.sessionId = sessionId;
    }

    public String getSessionId(){
        return sessionId;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public Integer getId(){
        return id;
    }

    public void setNpp(String npp){
        this.npp = npp;
    }

    public String getNpp(){
        return npp;
    }
}