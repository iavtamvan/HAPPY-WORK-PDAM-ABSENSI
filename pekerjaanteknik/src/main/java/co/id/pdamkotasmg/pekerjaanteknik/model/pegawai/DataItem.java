package co.id.pdamkotasmg.pekerjaanteknik.model.pegawai;

import com.google.gson.annotations.SerializedName;

public class DataItem{

    @SerializedName("is_admin")
    private Integer isAdmin;

    @SerializedName("name")
    private String name;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("id")
    private Integer id;

    @SerializedName("avatar")
    private Object avatar;

    @SerializedName("npp")
    private String npp;

    public void setIsAdmin(Integer isAdmin){
        this.isAdmin = isAdmin;
    }

    public Integer getIsAdmin(){
        return isAdmin;
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

    public void setAvatar(Object avatar){
        this.avatar = avatar;
    }

    public Object getAvatar(){
        return avatar;
    }

    public void setNpp(String npp){
        this.npp = npp;
    }

    public String getNpp(){
        return npp;
    }
}