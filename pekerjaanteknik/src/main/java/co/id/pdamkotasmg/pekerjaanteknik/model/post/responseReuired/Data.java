package co.id.pdamkotasmg.pekerjaanteknik.model.post.responseReuired;

import com.google.gson.annotations.SerializedName;

public class Data{

    @SerializedName("error")
    private java.lang.Error error;

    public void setError(java.lang.Error error){
        this.error = error;
    }

    public java.lang.Error getError(){
        return error;
    }
}