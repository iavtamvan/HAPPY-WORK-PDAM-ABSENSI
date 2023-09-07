package co.id.pdamkotasmg.model.checkPelangganSudahDibaca;

import com.google.gson.annotations.SerializedName;

public class RlStatusMeter{

    @SerializedName("no")
    private String no;

    @SerializedName("st")
    private String st;

    @SerializedName("gol")
    private String gol;

    @SerializedName("kode")
    private String kode;

    @SerializedName("status")
    private String status;

    public void setNo(String no){
        this.no = no;
    }

    public String getNo(){
        return no;
    }

    public void setSt(String st){
        this.st = st;
    }

    public String getSt(){
        return st;
    }

    public void setGol(String gol){
        this.gol = gol;
    }

    public String getGol(){
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