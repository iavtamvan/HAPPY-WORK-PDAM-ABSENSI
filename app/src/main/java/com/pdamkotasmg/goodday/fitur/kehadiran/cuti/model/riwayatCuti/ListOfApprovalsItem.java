package com.pdamkotasmg.goodday.fitur.kehadiran.cuti.model.riwayatCuti;

import com.google.gson.annotations.SerializedName;

public class ListOfApprovalsItem{

    @SerializedName("approver_npp")
    private String approverNpp;

    @SerializedName("approval_datetime")
    private String approvalDatetime;

    @SerializedName("approver_name")
    private String approverName;

    @SerializedName("approval_status")
    private String approvalStatus;

    @SerializedName("approver_note")
    private String approverNote;

    public void setApproverNpp(String approverNpp){
        this.approverNpp = approverNpp;
    }

    public String getApproverNpp(){
        return approverNpp;
    }

    public void setApprovalDatetime(String approvalDatetime){
        this.approvalDatetime = approvalDatetime;
    }

    public String getApprovalDatetime(){
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

    public void setApproverNote(String approverNote){
        this.approverNote = approverNote;
    }

    public String getApproverNote(){
        return approverNote;
    }
}