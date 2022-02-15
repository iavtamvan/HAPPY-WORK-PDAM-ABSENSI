package com.pdamkotasmg.goodday.fitur.kehadiran.cuti.model.tipeCuti;

import com.google.gson.annotations.SerializedName;

public class DataItem{

    @SerializedName("balance")
    private String balance;

    @SerializedName("validity_datetime_start")
    private String validityDatetimeStart;

    @SerializedName("id")
    private String id;

    @SerializedName("request_leave_type_code")
    private String requestLeaveTypeCode;

    @SerializedName("validity_datetime_end")
    private String validityDatetimeEnd;

    @SerializedName("request_leave_type_name")
    private String requestLeaveTypeName;

    public void setBalance(String balance){
        this.balance = balance;
    }

    public String getBalance(){
        return balance;
    }

    public void setValidityDatetimeStart(String validityDatetimeStart){
        this.validityDatetimeStart = validityDatetimeStart;
    }

    public String getValidityDatetimeStart(){
        return validityDatetimeStart;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }

    public void setRequestLeaveTypeCode(String requestLeaveTypeCode){
        this.requestLeaveTypeCode = requestLeaveTypeCode;
    }

    public String getRequestLeaveTypeCode(){
        return requestLeaveTypeCode;
    }

    public void setValidityDatetimeEnd(String validityDatetimeEnd){
        this.validityDatetimeEnd = validityDatetimeEnd;
    }

    public String getValidityDatetimeEnd(){
        return validityDatetimeEnd;
    }

    public void setRequestLeaveTypeName(String requestLeaveTypeName){
        this.requestLeaveTypeName = requestLeaveTypeName;
    }

    public String getRequestLeaveTypeName(){
        return requestLeaveTypeName;
    }
}