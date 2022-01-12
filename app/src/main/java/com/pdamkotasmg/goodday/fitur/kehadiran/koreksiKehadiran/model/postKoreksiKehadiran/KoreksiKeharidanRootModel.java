package com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran.model.postKoreksiKehadiran;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class KoreksiKeharidanRootModel{

    @SerializedName("end_date")
    private String endDate;

    @SerializedName("requested_for_npp")
    private String requestedForNpp;

    @SerializedName("request_type_code")
    private String requestTypeCode;

    @SerializedName("details")
    private List<DetailsItem> details;

    @SerializedName("start_date")
    private String startDate;

    public void setEndDate(String endDate){
        this.endDate = endDate;
    }

    public String getEndDate(){
        return endDate;
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

    public void setDetails(List<DetailsItem> details){
        this.details = details;
    }

    public List<DetailsItem> getDetails(){
        return details;
    }

    public void setStartDate(String startDate){
        this.startDate = startDate;
    }

    public String getStartDate(){
        return startDate;
    }
}