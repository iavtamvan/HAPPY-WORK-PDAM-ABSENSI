package co.id.pdamkotasmg.pekerjaanteknik.model.akun.refreshToken;

import com.google.gson.annotations.SerializedName;

public class Data{

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("refresh_token")
    private String refreshToken;

    @SerializedName("token_type")
    private String tokenType;

    @SerializedName("expires_in")
    private Integer expiresIn;

    public void setAccessToken(String accessToken){
        this.accessToken = accessToken;
    }

    public String getAccessToken(){
        return accessToken;
    }

    public void setRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken(){
        return refreshToken;
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
}