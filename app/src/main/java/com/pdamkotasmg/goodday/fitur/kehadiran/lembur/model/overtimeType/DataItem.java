package com.pdamkotasmg.goodday.fitur.kehadiran.lembur.model.overtimeType;

import com.google.gson.annotations.SerializedName;

public class DataItem{

    @SerializedName("request_overtime_type_name")
    private String requestOvertimeTypeName;

    @SerializedName("request_overtime_type_code")
    private String requestOvertimeTypeCode;

    @SerializedName("id")
    private String id;

    public void setRequestOvertimeTypeName(String requestOvertimeTypeName){
        this.requestOvertimeTypeName = requestOvertimeTypeName;
    }

    public String getRequestOvertimeTypeName(){
        return requestOvertimeTypeName;
    }

    public void setRequestOvertimeTypeCode(String requestOvertimeTypeCode){
        this.requestOvertimeTypeCode = requestOvertimeTypeCode;
    }

    public String getRequestOvertimeTypeCode(){
        return requestOvertimeTypeCode;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }
}