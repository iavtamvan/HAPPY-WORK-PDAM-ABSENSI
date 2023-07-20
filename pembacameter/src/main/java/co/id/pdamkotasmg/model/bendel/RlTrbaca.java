package co.id.pdamkotasmg.model.bendel;

import com.google.gson.annotations.SerializedName;

public class RlTrbaca{

    @SerializedName("lalu")
    private String lalu;

    @SerializedName("nolangg")
    private String nolangg;

    public void setLalu(String lalu){
        this.lalu = lalu;
    }

    public String getLalu(){
        return lalu;
    }

    public void setNolangg(String nolangg){
        this.nolangg = nolangg;
    }

    public String getNolangg(){
        return nolangg;
    }
}