package co.id.pdamkotasmg.pekerjaanteknik.model.riwayatAmbilPekerjaan;

import com.google.gson.annotations.SerializedName;

public class RiwayatAmbilPekerjaanRootModel{

    @SerializedName("data")
    private Data data;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private Integer status;

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

    public void setStatus(Integer status){
        this.status = status;
    }

    public Integer getStatus(){
        return status;
    }
}