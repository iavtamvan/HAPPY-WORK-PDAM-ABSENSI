package com.pdamkotasmg.goodday.fitur.kehadiran.perjalananDinas.model.postPerjalanDinas;

import com.google.gson.annotations.SerializedName;

public class DetailsItem{

    @SerializedName("request_on_duty_destination_id")
    private String requestOnDutyDestinationId;

    @SerializedName("date_start")
    private String dateStart;

    @SerializedName("date_end")
    private String dateEnd;

    public DetailsItem(String requestOnDutyDestinationId, String dateStart, String dateEnd) {
        this.requestOnDutyDestinationId = requestOnDutyDestinationId;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    public void setRequestOnDutyDestinationId(String requestOnDutyDestinationId){
        this.requestOnDutyDestinationId = requestOnDutyDestinationId;
    }

    public String getRequestOnDutyDestinationId(){
        return requestOnDutyDestinationId;
    }

    public void setDateStart(String dateStart){
        this.dateStart = dateStart;
    }

    public String getDateStart(){
        return dateStart;
    }

    public void setDateEnd(String dateEnd){
        this.dateEnd = dateEnd;
    }

    public String getDateEnd(){
        return dateEnd;
    }
}