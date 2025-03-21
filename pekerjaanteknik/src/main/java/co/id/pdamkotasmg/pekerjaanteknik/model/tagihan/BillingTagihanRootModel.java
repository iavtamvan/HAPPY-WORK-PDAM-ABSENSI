package co.id.pdamkotasmg.pekerjaanteknik.model.tagihan;

import com.google.gson.annotations.SerializedName;

public class BillingTagihanRootModel{

    @SerializedName("data")
    private Data data;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private int status;

    public void setData(Data data){
        this.data = data;
    }

    public Data getData(){
        return data;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    public void setStatus(int status){
        this.status = status;
    }

    public int getStatus(){
        return status;
    }
}