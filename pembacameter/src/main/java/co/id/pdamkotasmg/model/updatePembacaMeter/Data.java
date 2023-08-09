package co.id.pdamkotasmg.model.updatePembacaMeter;

import com.google.gson.annotations.SerializedName;

public class Data{

    @SerializedName("update_data")
    private Integer updateData;

    public void setUpdateData(Integer updateData){
        this.updateData = updateData;
    }

    public Integer getUpdateData(){
        return updateData;
    }
}