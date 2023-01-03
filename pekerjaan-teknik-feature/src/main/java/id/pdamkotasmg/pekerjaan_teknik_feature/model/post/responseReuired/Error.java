package id.pdamkotasmg.pekerjaan_teknik_feature.model.post.responseReuired;

import com.google.gson.annotations.SerializedName;

import java.util.List;

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