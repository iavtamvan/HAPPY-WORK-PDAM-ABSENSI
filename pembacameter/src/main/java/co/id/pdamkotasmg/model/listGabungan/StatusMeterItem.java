package co.id.pdamkotasmg.model.listGabungan;

import com.google.gson.annotations.SerializedName;

public class StatusMeterItem{

    @SerializedName("no")
    private Integer no;

    @SerializedName("st")
    private Integer st;

    @SerializedName("gol")
    private Integer gol;

    @SerializedName("kode")
    private String kode;

    @SerializedName("status")
    private String status;

    public void setNo(Integer no){
        this.no = no;
    }

    public Integer getNo(){
        return no;
    }

    public void setSt(Integer st){
        this.st = st;
    }

    public Integer getSt(){
        return st;
    }

    public void setGol(Integer gol){
        this.gol = gol;
    }

    public Integer getGol(){
        return gol;
    }

    public void setKode(String kode){
        this.kode = kode;
    }

    public String getKode(){
        return kode;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }
}