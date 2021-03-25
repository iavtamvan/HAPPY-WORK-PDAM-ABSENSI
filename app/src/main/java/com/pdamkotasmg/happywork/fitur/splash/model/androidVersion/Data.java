package com.pdamkotasmg.happywork.fitur.splash.model.androidVersion;

import com.google.gson.annotations.SerializedName;

public class Data{

    @SerializedName("val")
    private String val;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("id")
    private Integer id;

    @SerializedName("deleted_at")
    private Object deletedAt;

    @SerializedName("key")
    private String key;

    @SerializedName("error")
    private String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setVal(String val){
        this.val = val;
    }

    public String getVal(){
        return val;
    }

    public void setUpdatedAt(String updatedAt){
        this.updatedAt = updatedAt;
    }

    public String getUpdatedAt(){
        return updatedAt;
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

    public void setKey(String key){
        this.key = key;
    }

    public String getKey(){
        return key;
    }
}