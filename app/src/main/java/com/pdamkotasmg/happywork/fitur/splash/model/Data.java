package com.pdamkotasmg.happywork.fitur.splash.model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Data{

    @SerializedName("per_page")
    private Integer perPage;

    @SerializedName("data")
    private List<DataItem> data;

    @SerializedName("last_page")
    private Integer lastPage;

    @SerializedName("next_page_url")
    private Object nextPageUrl;

    @SerializedName("prev_page_url")
    private Object prevPageUrl;

    @SerializedName("first_page_url")
    private String firstPageUrl;

    @SerializedName("path")
    private String path;

    @SerializedName("total")
    private Integer total;

    @SerializedName("last_page_url")
    private String lastPageUrl;

    @SerializedName("from")
    private Integer from;

    @SerializedName("links")
    private List<LinksItem> links;

    @SerializedName("to")
    private Integer to;

    @SerializedName("current_page")
    private Integer currentPage;

    public void setPerPage(Integer perPage){
        this.perPage = perPage;
    }

    public Integer getPerPage(){
        return perPage;
    }

    public void setData(List<DataItem> data){
        this.data = data;
    }

    public List<DataItem> getData(){
        return data;
    }

    public void setLastPage(Integer lastPage){
        this.lastPage = lastPage;
    }

    public Integer getLastPage(){
        return lastPage;
    }

    public void setNextPageUrl(Object nextPageUrl){
        this.nextPageUrl = nextPageUrl;
    }

    public Object getNextPageUrl(){
        return nextPageUrl;
    }

    public void setPrevPageUrl(Object prevPageUrl){
        this.prevPageUrl = prevPageUrl;
    }

    public Object getPrevPageUrl(){
        return prevPageUrl;
    }

    public void setFirstPageUrl(String firstPageUrl){
        this.firstPageUrl = firstPageUrl;
    }

    public String getFirstPageUrl(){
        return firstPageUrl;
    }

    public void setPath(String path){
        this.path = path;
    }

    public String getPath(){
        return path;
    }

    public void setTotal(Integer total){
        this.total = total;
    }

    public Integer getTotal(){
        return total;
    }

    public void setLastPageUrl(String lastPageUrl){
        this.lastPageUrl = lastPageUrl;
    }

    public String getLastPageUrl(){
        return lastPageUrl;
    }

    public void setFrom(Integer from){
        this.from = from;
    }

    public Integer getFrom(){
        return from;
    }

    public void setLinks(List<LinksItem> links){
        this.links = links;
    }

    public List<LinksItem> getLinks(){
        return links;
    }

    public void setTo(Integer to){
        this.to = to;
    }

    public Integer getTo(){
        return to;
    }

    public void setCurrentPage(Integer currentPage){
        this.currentPage = currentPage;
    }

    public Integer getCurrentPage(){
        return currentPage;
    }
}