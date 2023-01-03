package id.pdamkotasmg.pekerjaan_teknik_feature.model.akun.login;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data{

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("permissions")
    private List<String> permissions;

    @SerializedName("session")
    private Session session;

    @SerializedName("roles")
    private List<String> roles;

    @SerializedName("token_type")
    private String tokenType;

    @SerializedName("expires_in")
    private Integer expiresIn;

    @SerializedName("user")
    private User user;

    @SerializedName("device")
    private Object device;

    public void setAccessToken(String accessToken){
        this.accessToken = accessToken;
    }

    public String getAccessToken(){
        return accessToken;
    }

    public void setPermissions(List<String> permissions){
        this.permissions = permissions;
    }

    public List<String> getPermissions(){
        return permissions;
    }

    public void setSession(Session session){
        this.session = session;
    }

    public Session getSession(){
        return session;
    }

    public void setRoles(List<String> roles){
        this.roles = roles;
    }

    public List<String> getRoles(){
        return roles;
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

    public void setDevice(Object device){
        this.device = device;
    }

    public Object getDevice(){
        return device;
    }
}