package co.id.pdamkotasmg.model.riwayatBacaMeter;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class RiwayatBacaMeterRootModel{

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