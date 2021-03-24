package com.pdamkotasmg.happywork.fitur.splash.model;

import com.google.gson.annotations.SerializedName;

public class DataItem{

    @SerializedName("app_name")
    private String appName;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("package_name")
    private String packageName;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("id")
    private Integer id;

    @SerializedName("deleted_at")
    private Object deletedAt;

    public void setAppName(String appName){
        this.appName = appName;
    }

    public String getAppName(){
        return appName;
    }

    public void setUpdatedAt(String updatedAt){
        this.updatedAt = updatedAt;
    }

    public String getUpdatedAt(){
        return updatedAt;
    }

    public void setPackageName(String packageName){
        this.packageName = packageName;
    }

    public String getPackageName(){
        return packageName;
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

    public void setDeletedAt(Object deletedAt){
        this.deletedAt = deletedAt;
    }

    public Object getDeletedAt(){
        return deletedAt;
    }
}