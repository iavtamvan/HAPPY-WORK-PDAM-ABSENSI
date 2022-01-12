package com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran.model.riwayatKoreksiKehadiran;

import com.google.gson.annotations.SerializedName;

public class ListOfApprovalsItem{

    @SerializedName("approver_npp")
    private String approverNpp;

    @SerializedName("approval_datetime")
    private Object approvalDatetime;

    @SerializedName("approver_name")
    private String approverName;

    @SerializedName("approval_status")
    private String approvalStatus;

    @SerializedName("approver_note")
    private Object approverNote;

    public void setApproverNpp(String approverNpp){
        this.approverNpp = approverNpp;
    }

    public String getApproverNpp(){
        return approverNpp;
    }

    public void setApprovalDatetime(Object approvalDatetime){
        this.approvalDatetime = approvalDatetime;
    }

    public Object getApprovalDatetime(){
        return approvalDatetime;
    }

    public void setApproverName(String approverName){
        this.approverName = approverName;
    }

    public String getApproverName(){
        return approverName;
    }

    public void setApprovalStatus(String approvalStatus){
        this.approvalStatus = approvalStatus;
    }

    public String getApprovalStatus(){
        return approvalStatus;
    }

    public void setApproverNote(Object approverNote){
        this.approverNote = approverNote;
    }

    public Object getApproverNote(){
        return approverNote;
    }
}