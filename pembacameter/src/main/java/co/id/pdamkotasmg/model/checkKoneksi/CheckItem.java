package co.id.pdamkotasmg.model.checkKoneksi;

import com.google.gson.annotations.SerializedName;

public class CheckItem{

    @SerializedName("endpoint")
    private String endpoint;

    @SerializedName("status_code")
    private String statusCode;

    @SerializedName("status_desc")
    private String statusDesc;

    @SerializedName("name")
    private String name;

    @SerializedName("speed_latency")
    private String speedLatency;

    @SerializedName("durasi")
    private String durasi;

    public void setEndpoint(String endpoint){
        this.endpoint = endpoint;
    }

    public String getEndpoint(){
        return endpoint;
    }

    public void setStatusCode(String statusCode){
        this.statusCode = statusCode;
    }

    public String getStatusCode(){
        return statusCode;
    }

    public void setStatusDesc(String statusDesc){
        this.statusDesc = statusDesc;
    }

    public String getStatusDesc(){
        return statusDesc;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setSpeedLatency(String speedLatency){
        this.speedLatency = speedLatency;
    }

    public String getSpeedLatency(){
        return speedLatency;
    }

    public void setDurasi(String durasi){
        this.durasi = durasi;
    }

    public String getDurasi(){
        return durasi;
    }
}