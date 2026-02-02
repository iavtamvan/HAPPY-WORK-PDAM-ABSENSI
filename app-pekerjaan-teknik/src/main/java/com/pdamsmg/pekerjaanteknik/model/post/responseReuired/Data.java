package com.pdamsmg.pekerjaanteknik.model.post.responseReuired;

import com.google.gson.annotations.SerializedName;

public class Data{

    @SerializedName("error")
    private Error error;

    public void setError(Error error){
        this.error = error;
    }

    public Error getError(){
        return error;
    }
}