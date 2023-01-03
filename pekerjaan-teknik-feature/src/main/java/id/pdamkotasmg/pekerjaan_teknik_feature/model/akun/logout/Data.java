package id.pdamkotasmg.pekerjaan_teknik_feature.model.akun.logout;

import com.google.gson.annotations.SerializedName;

public class Data{

    @SerializedName("message")
    private String message;

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}