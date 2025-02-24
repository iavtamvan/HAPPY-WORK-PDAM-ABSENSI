package co.id.pdamkotasmg.model.updatePembacaMeter;

import com.google.gson.annotations.SerializedName;

public class Data{

    @SerializedName("update_data_log")
    private UpdateDataLog updateDataLog;

    @SerializedName("action_codes")
    private String actionCodes;

    @SerializedName("update_data")
    private String updateData;

    @SerializedName("insert_cu")
    private InsertCu insertCu;

    public UpdateDataLog getUpdateDataLog(){
        return updateDataLog;
    }

    public String getActionCodes(){
        return actionCodes;
    }

    public String getUpdateData(){
        return updateData;
    }

    public InsertCu getInsertCu(){
        return insertCu;
    }
}