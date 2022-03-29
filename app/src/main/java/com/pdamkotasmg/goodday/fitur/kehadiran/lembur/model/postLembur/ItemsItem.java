package com.pdamkotasmg.goodday.fitur.kehadiran.lembur.model.postLembur;

import com.google.gson.annotations.SerializedName;

public class ItemsItem{

    @SerializedName("overtime_start")
    private String overtimeStart;

    @SerializedName("overtime_end")
    private String overtimeEnd;

    public void setOvertimeStart(String overtimeStart){
        this.overtimeStart = overtimeStart;
    }

    public String getOvertimeStart(){
        return overtimeStart;
    }

    public void setOvertimeEnd(String overtimeEnd){
        this.overtimeEnd = overtimeEnd;
    }

    public String getOvertimeEnd(){
        return overtimeEnd;
    }
}