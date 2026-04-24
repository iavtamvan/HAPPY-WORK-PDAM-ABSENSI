package id.pdamkotasmg.edms.fitur.suratMasuk.model;

import com.google.gson.annotations.SerializedName;

public class PostSuratMasukDetailRootModel {

    @SerializedName("data")
    private DataItem data;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private Integer status;

    public DataItem getData() {
        return data;
    }

    public void setData(DataItem data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }
}