package co.id.pdamkotasmg.model.checkKoneksi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data{

    @SerializedName("server_google")
    private String serverGoogle;

    @SerializedName("end")
    private String end;

    @SerializedName("check")
    private List<CheckItem> check;

    public void setServerGoogle(String serverGoogle){
        this.serverGoogle = serverGoogle;
    }

    public String getServerGoogle(){
        return serverGoogle;
    }

    public void setEnd(String end){
        this.end = end;
    }

    public String getEnd(){
        return end;
    }

    public void setCheck(List<CheckItem> check){
        this.check = check;
    }

    public List<CheckItem> getCheck(){
        return check;
    }
}