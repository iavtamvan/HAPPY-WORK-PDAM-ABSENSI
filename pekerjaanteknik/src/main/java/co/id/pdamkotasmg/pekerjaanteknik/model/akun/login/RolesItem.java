package co.id.pdamkotasmg.pekerjaanteknik.model.akun.login;

import com.google.gson.annotations.SerializedName;

public class RolesItem{

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("name")
    private String name;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("pivot")
    private Pivot pivot;

    @SerializedName("id")
    private String id;

    @SerializedName("guard_name")
    private String guardName;

    public void setUpdatedAt(String updatedAt){
        this.updatedAt = updatedAt;
    }

    public String getUpdatedAt(){
        return updatedAt;
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

    public void setPivot(Pivot pivot){
        this.pivot = pivot;
    }

    public Pivot getPivot(){
        return pivot;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }

    public void setGuardName(String guardName){
        this.guardName = guardName;
    }

    public String getGuardName(){
        return guardName;
    }
}