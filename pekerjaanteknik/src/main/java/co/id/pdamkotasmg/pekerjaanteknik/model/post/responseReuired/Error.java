package co.id.pdamkotasmg.pekerjaanteknik.model.post.responseReuired;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Error{

    @SerializedName("status")
    private List<String> status;

    public void setStatus(List<String> status){
        this.status = status;
    }

    public List<String> getStatus(){
        return status;
    }
}