package co.id.pdamkotasmg.model.bendel.tandaiPlg;

import com.google.gson.annotations.SerializedName;

public class Data{

    @SerializedName("tandai_plg")
    private Integer tandaiPlg;

    public void setTandaiPlg(Integer tandaiPlg){
        this.tandaiPlg = tandaiPlg;
    }

    public Integer getTandaiPlg(){
        return tandaiPlg;
    }
}