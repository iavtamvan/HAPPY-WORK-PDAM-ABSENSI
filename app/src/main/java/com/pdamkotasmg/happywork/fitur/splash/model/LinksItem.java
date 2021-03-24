package com.pdamkotasmg.happywork.fitur.splash.model;

import com.google.gson.annotations.SerializedName;

public class LinksItem{

    @SerializedName("active")
    private Boolean active;

    @SerializedName("label")
    private String label;

    @SerializedName("url")
    private Object url;

    public void setActive(Boolean active){
        this.active = active;
    }

    public boolean isActive(){
        return active;
    }

    public void setLabel(String label){
        this.label = label;
    }

    public String getLabel(){
        return label;
    }

    public void setUrl(Object url){
        this.url = url;
    }

    public Object getUrl(){
        return url;
    }
}