package com.pdamkotasmg.goodday.fitur.kehadiran.home.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class DataItem{

    @SerializedName("record_date")
    private Date recordDate;

    @SerializedName("in")
    private In in;

    @SerializedName("npp")
    private String npp;

    @SerializedName("out")
    private Out out;

    public void setRecordDate(Date recordDate){
        this.recordDate = recordDate;
    }

    public Date getRecordDate(){
        return recordDate;
    }

    public void setIn(In in){
        this.in = in;
    }

    public In getIn(){
        return in;
    }

    public void setNpp(String npp){
        this.npp = npp;
    }

    public String getNpp(){
        return npp;
    }

    public void setOut(Out out){
        this.out = out;
    }

    public Out getOut(){
        return out;
    }
}