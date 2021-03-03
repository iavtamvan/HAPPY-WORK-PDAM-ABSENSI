package com.pdamkotasmg.happywork.fitur.feeds.model;

import com.google.gson.annotations.SerializedName;

public class DataItem{

    @SerializedName("hits")
    private String hits;

    @SerializedName("fav")
    private String fav;

    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("category")
    private String category;

    @SerializedName("tgl_publish")
    private String tglPublish;

    @SerializedName("url")
    private String url;

    @SerializedName("content")
    private String content;

    @SerializedName("desc")
    private String desc;

    public void setHits(String hits){
        this.hits = hits;
    }

    public String getHits(){
        return hits;
    }

    public void setFav(String fav){
        this.fav = fav;
    }

    public String getFav(){
        return fav;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getTitle(){
        return title;
    }

    public void setCategory(String category){
        this.category = category;
    }

    public String getCategory(){
        return category;
    }

    public void setTglPublish(String tglPublish){
        this.tglPublish = tglPublish;
    }

    public String getTglPublish(){
        return tglPublish;
    }

    public void setUrl(String url){
        this.url = url;
    }

    public String getUrl(){
        return url;
    }

    public void setContent(String content){
        this.content = content;
    }

    public String getContent(){
        return content;
    }

    public void setDesc(String desc){
        this.desc = desc;
    }

    public String getDesc(){
        return desc;
    }
}