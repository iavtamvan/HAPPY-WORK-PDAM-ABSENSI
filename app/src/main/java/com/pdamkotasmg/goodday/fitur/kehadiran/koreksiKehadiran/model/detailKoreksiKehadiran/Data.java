package com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran.model.detailKoreksiKehadiran;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data{

    @SerializedName("request_status")
    private String requestStatus;

    @SerializedName("requested_for_name")
    private String requestedForName;

    @SerializedName("requested_for_npp")
    private String requestedForNpp;

    @SerializedName("request_type")
    private String requestType;

    @SerializedName("request_attachment")
    private Object requestAttachment;

    @SerializedName("request_start_date")
    private String requestStartDate;

    @SerializedName("requested_by_npp")
    private String requestedByNpp;

    @SerializedName("request_details")
    private List<RequestDetailsItem> requestDetails;

    @SerializedName("list_of_approvals")
    private List<ListOfApprovalsItem> listOfApprovals;

    @SerializedName("request_number")
    private String requestNumber;

    @SerializedName("requested_by_name")
    private String requestedByName;

    @SerializedName("request_end_date")
    private String requestEndDate;

    @SerializedName("requested_at")
    private String requestedAt;

    public void setRequestStatus(String requestStatus){
        this.requestStatus = requestStatus;
    }

    public String getRequestStatus(){
        return requestStatus;
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

    public void setRequestType(String requestType){
        this.requestType = requestType;
    }

    public String getRequestType(){
        return requestType;
    }

    public void setRequestAttachment(Object requestAttachment){
        this.requestAttachment = requestAttachment;
    }

    public Object getRequestAttachment(){
        return requestAttachment;
    }

    public void setRequestStartDate(String requestStartDate){
        this.requestStartDate = requestStartDate;
    }

    public String getRequestStartDate(){
        return requestStartDate;
    }

    public void setRequestedByNpp(String requestedByNpp){
        this.requestedByNpp = requestedByNpp;
    }

    public String getRequestedByNpp(){
        return requestedByNpp;
    }

    public void setRequestDetails(List<RequestDetailsItem> requestDetails){
        this.requestDetails = requestDetails;
    }

    public List<RequestDetailsItem> getRequestDetails(){
        return requestDetails;
    }

    public void setListOfApprovals(List<ListOfApprovalsItem> listOfApprovals){
        this.listOfApprovals = listOfApprovals;
    }

    public List<ListOfApprovalsItem> getListOfApprovals(){
        return listOfApprovals;
    }

    public void setRequestNumber(String requestNumber){
        this.requestNumber = requestNumber;
    }

    public String getRequestNumber(){
        return requestNumber;
    }

    public void setRequestedByName(String requestedByName){
        this.requestedByName = requestedByName;
    }

    public String getRequestedByName(){
        return requestedByName;
    }

    public void setRequestEndDate(String requestEndDate){
        this.requestEndDate = requestEndDate;
    }

    public String getRequestEndDate(){
        return requestEndDate;
    }

    public void setRequestedAt(String requestedAt){
        this.requestedAt = requestedAt;
    }

    public String getRequestedAt(){
        return requestedAt;
    }
}