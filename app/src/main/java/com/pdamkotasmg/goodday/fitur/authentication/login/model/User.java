package com.pdamkotasmg.goodday.fitur.authentication.login.model;

import com.google.gson.annotations.SerializedName;

public class User{

    @SerializedName("is_admin")
    private Integer isAdmin;

    @SerializedName("rl_pegawai")
    private RlPegawai rlPegawai;

    @SerializedName("name")
    private String name;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("id")
    private Integer id;

    @SerializedName("avatar")
    private String avatar;

    @SerializedName("npp")
    private String npp;

    public void setIsAdmin(Integer isAdmin){
        this.isAdmin = isAdmin;
    }

    public Integer getIsAdmin(){
        return isAdmin;
    }

    public void setRlPegawai(RlPegawai rlPegawai){
        this.rlPegawai = rlPegawai;
    }

    public RlPegawai getRlPegawai(){
        return rlPegawai;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setCreatedAt(String createdAt){
        this.createdAt = createdAt;
    }

    public String getCreatedAt(){
        return createdAt;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public Integer getId(){
        return id;
    }

    public void setAvatar(String avatar){
        this.avatar = avatar;
    }

    public String getAvatar(){
        return avatar;
    }

    public void setNpp(String npp){
        this.npp = npp;
    }

    public String getNpp(){
        return npp;
    }
}