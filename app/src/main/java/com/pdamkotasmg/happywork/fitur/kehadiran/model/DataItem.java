package com.pdamkotasmg.happywork.fitur.kehadiran.model;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

public class DataItem{

    @SerializedName("record_date")
    private String recordDate;

    @SerializedName("in")
    private In in = null;

    @SerializedName("npp")
    private String npp;

    @SerializedName("out")
    private Out out = null;

    public void setRecordDate(String recordDate){
        this.recordDate = recordDate;
    }

    public String getRecordDate(){
        return recordDate;
    }

    public void setIn(In in){
        this.in = in;
        Log.d("debug", "setIn: " + in);
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
        Log.d("debug", "seOt: " + out);
    }

    public Out getOut(){
        return out;
    }
}