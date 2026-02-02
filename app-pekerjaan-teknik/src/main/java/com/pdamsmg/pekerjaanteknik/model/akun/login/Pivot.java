package com.pdamsmg.pekerjaanteknik.model.akun.login;

import com.google.gson.annotations.SerializedName;

public class Pivot{

    @SerializedName("model_type")
    private String modelType;

    @SerializedName("model_id")
    private String modelId;

    @SerializedName("permission_id")
    private String permissionId;

    @SerializedName("role_id")
    private String roleId;

    public void setModelType(String modelType){
        this.modelType = modelType;
    }

    public String getModelType(){
        return modelType;
    }

    public void setModelId(String modelId){
        this.modelId = modelId;
    }

    public String getModelId(){
        return modelId;
    }

    public void setPermissionId(String permissionId){
        this.permissionId = permissionId;
    }

    public String getPermissionId(){
        return permissionId;
    }

    public void setRoleId(String roleId){
        this.roleId = roleId;
    }

    public String getRoleId(){
        return roleId;
    }
}