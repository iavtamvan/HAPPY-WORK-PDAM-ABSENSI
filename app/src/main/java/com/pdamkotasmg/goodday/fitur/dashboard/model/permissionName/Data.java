package com.pdamkotasmg.goodday.fitur.dashboard.model.permissionName;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Data{

    @SerializedName("permissions")
    private List<String> permissions;

    public void setPermissions(List<String> permissions){
        this.permissions = permissions;
    }

    public List<String> getPermissions(){
        return permissions;
    }
}