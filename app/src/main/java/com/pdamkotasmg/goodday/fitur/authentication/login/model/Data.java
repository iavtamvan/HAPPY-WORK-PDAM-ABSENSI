package com.pdamkotasmg.goodday.fitur.authentication.login.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Data{

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("session")
    private Session session;

    @SerializedName("token_type")
    private String tokenType;

    @SerializedName("refresh_token")
    private String refreshToken;

    @SerializedName("expires_in")
    private Integer expiresIn;

    @SerializedName("user")
    private User user;

    @SerializedName("device")
    private Device device;

    @SerializedName("roles")
    private ArrayList<String> roles;

    public ArrayList<String> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<String> roles) {
        this.roles = roles;
    }

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

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
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