package com.pdamkotasmg.goodday.fitur.kehadiran.lembur.model.postLembur;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DetailsItem{

    @SerializedName("record_date")
    private String recordDate;

    @SerializedName("items")
    private List<ItemsItem> items;

    public void setRecordDate(String recordDate){
        this.recordDate = recordDate;
    }

    public String getRecordDate(){
        return recordDate;
    }

    public void setItems(List<ItemsItem> items){
        this.items = items;
    }

    public List<ItemsItem> getItems(){
        return items;
    }
}