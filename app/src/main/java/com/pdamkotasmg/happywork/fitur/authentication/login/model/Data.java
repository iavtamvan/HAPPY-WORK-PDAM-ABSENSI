package com.pdamkotasmg.happywork.fitur.authentication.login.model;

import com.google.gson.annotations.SerializedName;

public class Data{

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("session")
    private Session session;

    @SerializedName("token_type")
    private String tokenType;

    @SerializedName("expires_in")
    private Integer expiresIn;

    @SerializedName("user")
    private User user;

    @SerializedName("device")
    private Device device;

    public void setAccessToken(String accessToken){
        this.accessToken = accessToken;
    }

    public String getAccessToken(){
        return accessToken;
    }

    public void setSession(Session session){
        this.session = session;
    }

    public Session getSession(){
        return session;
    }

    public void setTokenType(String tokenType){
        this.tokenType = tokenType;
    }

    public String getTokenType(){
        return tokenType;
    }

    public void setExpiresIn(Integer expiresIn){
        this.expiresIn = expiresIn;
    }

    public Integer getExpiresIn(){
        return expiresIn;
    }

    public void setUser(User user){
        this.user = user;
    }

    public User getUser(){
        return user;
    }

    public void setDevice(Device device){
        this.device = device;
    }

    public Device getDevice(){
        return device;
    }
}