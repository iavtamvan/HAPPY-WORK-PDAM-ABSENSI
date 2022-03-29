package com.pdamkotasmg.goodday.fitur.kehadiran.lembur.model.postLembur;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class PostLemburRootModel{

    @SerializedName("requested_for_npp")
    private String requestedForNpp;

    @SerializedName("date_start")
    private String dateStart;

    @SerializedName("request_type_code")
    private String requestTypeCode;

    @SerializedName("request_overtime_reason_id")
    private Integer requestOvertimeReasonId;

    @SerializedName("date_end")
    private String dateEnd;

    @SerializedName("remark")
    private String remark;

    @SerializedName("details")
    private List<DetailsItem> details;

    @SerializedName("request_overtime_type_id")
    private Integer requestOvertimeTypeId;

    public void setRequestedForNpp(String requestedForNpp){
        this.requestedForNpp = requestedForNpp;
    }

    public String getRequestedForNpp(){
        return requestedForNpp;
    }

    public void setDateStart(String dateStart){
        this.dateStart = dateStart;
    }

    public String getDateStart(){
        return dateStart;
    }

    public void setRequestTypeCode(String requestTypeCode){
        this.requestTypeCode = requestTypeCode;
    }

    public String getRequestTypeCode(){
        return requestTypeCode;
    }

    public void setRequestOvertimeReasonId(Integer requestOvertimeReasonId){
        this.requestOvertimeReasonId = requestOvertimeReasonId;
    }

    public Integer getRequestOvertimeReasonId(){
        return requestOvertimeReasonId;
    }

    public void setDateEnd(String dateEnd){
        this.dateEnd = dateEnd;
    }

    public String getDateEnd(){
        return dateEnd;
    }

    public void setRemark(String remark){
        this.remark = remark;
    }

    public String getRemark(){
        return remark;
    }

    public void setDetails(List<DetailsItem> details){
        this.details = details;
    }

    public List<DetailsItem> getDetails(){
        return details;
    }

    public void setRequestOvertimeTypeId(Integer requestOvertimeTypeId){
        this.requestOvertimeTypeId = requestOvertimeTypeId;
    }

    public Integer getRequestOvertimeTypeId(){
        return requestOvertimeTypeId;
    }
}