package co.id.pdamkotasmg.model.singleManometer;

import com.google.gson.annotations.SerializedName;

public class SingleManomterRoot{

    @SerializedName("data")
    private String data;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private String status;

    public void setData(String data){
        this.data = data;
    }

    public String getData(){
        return data;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }
}