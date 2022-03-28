package com.pdamkotasmg.goodday.fitur.kehadiran.perjalananDinas.model.postPerjalanDinas;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostPerjalananDinasRootModel{

    @SerializedName("requested_for_npp")
    private String requestedForNpp;

    @SerializedName("request_type_code")
    private String requestTypeCode;

    @SerializedName("request_on_duty_type_id")
    private String requestOnDutyTypeId;

    @SerializedName("remark")
    private String remark;

    @SerializedName("details")
    private List<DetailsItem> details;

    public PostPerjalananDinasRootModel(String requestedForNpp, String requestTypeCode, String requestOnDutyTypeId, String remark, List<DetailsItem> details) {
        this.requestedForNpp = requestedForNpp;
        this.requestTypeCode = requestTypeCode;
        this.requestOnDutyTypeId = requestOnDutyTypeId;
        this.remark = remark;
        this.details = details;
    }

    public void setRequestedForNpp(String requestedForNpp){
        this.requestedForNpp = requestedForNpp;
    }

    public String getRequestedForNpp(){
        return requestedForNpp;
    }

    public void setRequestTypeCode(String requestTypeCode){
        this.requestTypeCode = requestTypeCode;
    }

    public String getRequestTypeCode(){
        return requestTypeCode;
    }

    public void setRequestOnDutyTypeId(String requestOnDutyTypeId){
        this.requestOnDutyTypeId = requestOnDutyTypeId;
    }

    public String getRequestOnDutyTypeId(){
        return requestOnDutyTypeId;
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
}