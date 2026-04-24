package co.id.pdamkotasmg.pekerjaanteknik.model.akun.login;

import com.google.gson.annotations.SerializedName;

public class Session{

    @SerializedName("is_expired")
    private Boolean isExpired;

    @SerializedName("session_id")
    private String sessionId;

    @SerializedName("id")
    private String id;

    @SerializedName("npp")
    private String npp;

    public void setIsExpired(Boolean isExpired){
        this.isExpired = isExpired;
    }

    public Boolean isIsExpired(){
        return isExpired;
    }

    public void setSessionId(String sessionId){
        this.sessionId = sessionId;
    }

    public String getSessionId(){
        return sessionId;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }

    public void setNpp(String npp){
        this.npp = npp;
    }

    public String getNpp(){
        return npp;
    }
}