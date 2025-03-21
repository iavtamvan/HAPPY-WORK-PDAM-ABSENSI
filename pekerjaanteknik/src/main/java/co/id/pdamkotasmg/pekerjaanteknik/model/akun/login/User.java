package co.id.pdamkotasmg.pekerjaanteknik.model.akun.login;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class User{

    @SerializedName("is_admin")
    private String isAdmin;

    @SerializedName("id_cabang")
    private String idCabang;

    @SerializedName("rl_pegawai")
    private RlPegawai rlPegawai;

    @SerializedName("permissions")
    private List<PermissionsItem> permissions;

    @SerializedName("roles")
    private List<RolesItem> roles;

    @SerializedName("name")
    private String name;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("id")
    private String id;

    @SerializedName("avatar")
    private String avatar;

    @SerializedName("npp")
    private String npp;

    public void setIsAdmin(String isAdmin){
        this.isAdmin = isAdmin;
    }

    public String getIsAdmin(){
        return isAdmin;
    }

    public void setIdCabang(String idCabang){
        this.idCabang = idCabang;
    }

    public String getIdCabang(){
        return idCabang;
    }

    public void setRlPegawai(RlPegawai rlPegawai){
        this.rlPegawai = rlPegawai;
    }

    public RlPegawai getRlPegawai(){
        return rlPegawai;
    }

    public void setPermissions(List<PermissionsItem> permissions){
        this.permissions = permissions;
    }

    public List<PermissionsItem> getPermissions(){
        return permissions;
    }

    public void setRoles(List<RolesItem> roles){
        this.roles = roles;
    }

    public List<RolesItem> getRoles(){
        return roles;
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

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
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