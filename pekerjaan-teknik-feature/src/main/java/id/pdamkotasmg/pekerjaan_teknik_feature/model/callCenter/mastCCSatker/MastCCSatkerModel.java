package id.pdamkotasmg.pekerjaan_teknik_feature.model.callCenter.mastCCSatker;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MastCCSatkerModel{

    @SerializedName("data")
    private List<DataItem> data;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private Integer status;

    public void setData(List<DataItem> data){
        this.data = data;
    }

    public List<DataItem> getData(){
        return data;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    public void setStatus(Integer status){
        this.status = status;
    }

    public Integer getStatus(){
        return status;
    }
}