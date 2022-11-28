package com.pdamkotasmg.goodday.fitur.permintaan.model;

import com.google.gson.annotations.SerializedName;

public class DataItem{

    @SerializedName("request_status_code")
    private String requestStatusCode;

    @SerializedName("requested_by_bagian")
    private String requestedByBagian;

    @SerializedName("requested_by_jabatan")
    private String requestedByJabatan;

    @SerializedName("requested_for_name")
    private String requestedForName;

    @SerializedName("requested_for_npp")
    private String requestedForNpp;

    @SerializedName("request_status_name")
    private String requestStatusName;

    @SerializedName("requested_by_npp")
    private String requestedByNpp;

    @SerializedName("request_number")
    private String requestNumber;

    @SerializedName("request_type_code")
    private String requestTypeCode;

    @SerializedName("requested_by_name")
    private String requestedByName;

    @SerializedName("request_type_name")
    private String requestTypeName;

    @SerializedName("requested_for_bagian")
    private String requestedForBagian;

    @SerializedName("requested_for_jabatan")
    private String requestedForJabatan;

    @SerializedName("requested_at")
    private String requestedAt;

    @SerializedName("approval_status_code")
    private String approvalStatusCode;

    @SerializedName("approval_status_name")
    private String approvalStatusName;

    @SerializedName("need_approval_by_npp")
    private String needApprovalByNpp;

    @SerializedName("need_approval_by_name")
    private String needApprovalByName;

    public String getApprovalStatusCode() {
        return approvalStatusCode;
    }

    public void setApprovalStatusCode(String approvalStatusCode) {
        this.approvalStatusCode = approvalStatusCode;
    }

    public String getApprovalStatusName() {
        return approvalStatusName;
    }

    public void setApprovalStatusName(String approvalStatusName) {
        this.approvalStatusName = approvalStatusName;
    }

    public String getNeedApprovalByNpp() {
        return needApprovalByNpp;
    }

    public void setNeedApprovalByNpp(String needApprovalByNpp) {
        this.needApprovalByNpp = needApprovalByNpp;
    }

    public String getNeedApprovalByName() {
        return needApprovalByName;
    }

    public void setNeedApprovalByName(String needApprovalByName) {
        this.needApprovalByName = needApprovalByName;
    }

    public void setRequestStatusCode(String requestStatusCode){
        this.requestStatusCode = requestStatusCode;
    }

    public String getRequestStatusCode(){
        return requestStatusCode;
    }

    public void setRequestedByBagian(String requestedByBagian){
        this.requestedByBagian = requestedByBagian;
    }

    public String getRequestedByBagian(){
        return requestedByBagian;
    }

    public void setRequestedByJabatan(String requestedByJabatan){
        this.requestedByJabatan = requestedByJabatan;
    }

    public String getRequestedByJabatan(){
        return requestedByJabatan;
    }

    public void setRequestedForName(String requestedForName){
        this.requestedForName = requestedForName;
    }

    public String getRequestedForName(){
        return requestedForName;
    }

    public void setRequestedForNpp(String requestedForNpp){
        this.requestedForNpp = requestedForNpp;
    }

    public String getRequestedForNpp(){
        return requestedForNpp;
    }

    public void setRequestStatusName(String requestStatusName){
        this.requestStatusName = requestStatusName;
    }

    public String getRequestStatusName(){
        return requestStatusName;
    }

    public void setRequestedByNpp(String requestedByNpp){
        this.requestedByNpp = requestedByNpp;
    }

    public String getRequestedByNpp(){
        return requestedByNpp;
    }

    public void setRequestNumber(String requestNumber){
        this.requestNumber = requestNumber;
    }

    public String getRequestNumber(){
        return requestNumber;
    }

    public void setRequestTypeCode(String requestTypeCode){
        this.requestTypeCode = requestTypeCode;
    }

    public String getRequestTypeCode(){
        return requestTypeCode;
    }

    public void setRequestedByName(String requestedByName){
        this.requestedByName = requestedByName;
    }

    public String getRequestedByName(){
        return requestedByName;
    }

    public void setRequestTypeName(String requestTypeName){
        this.requestTypeName = requestTypeName;
    }

    public String getRequestTypeName(){
        return requestTypeName;
    }

    public void setRequestedForBagian(String requestedForBagian){
        this.requestedForBagian = requestedForBagian;
    }

    public String getRequestedForBagian(){
        return requestedForBagian;
    }

    public void setRequestedForJabatan(String requestedForJabatan){
        this.requestedForJabatan = requestedForJabatan;
    }

    public String getRequestedForJabatan(){
        return requestedForJabatan;
    }

    public void setRequestedAt(String requestedAt){
        this.requestedAt = requestedAt;
    }

    public String getRequestedAt(){
        return requestedAt;
    }
}