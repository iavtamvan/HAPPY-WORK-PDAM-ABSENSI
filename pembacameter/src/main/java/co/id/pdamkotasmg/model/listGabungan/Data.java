package co.id.pdamkotasmg.model.listGabungan;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Data{

    @SerializedName("stBaca")
    private List<StBacaItem> stBaca;

    @SerializedName("cabang")
    private List<CabangItem> cabang;

    @SerializedName("merk")
    private List<MerkItem> merk;

    @SerializedName("stPasang")
    private List<StPasangItem> stPasang;

    @SerializedName("stVer")
    private List<StVerItem> stVer;

    @SerializedName("statusMeter")
    private List<StatusMeterItem> statusMeter;

    @SerializedName("bagian")
    private List<BagianItem> bagian;

    public void setStBaca(List<StBacaItem> stBaca){
        this.stBaca = stBaca;
    }

    public List<StBacaItem> getStBaca(){
        return stBaca;
    }

    public void setCabang(List<CabangItem> cabang){
        this.cabang = cabang;
    }

    public List<CabangItem> getCabang(){
        return cabang;
    }

    public void setMerk(List<MerkItem> merk){
        this.merk = merk;
    }

    public List<MerkItem> getMerk(){
        return merk;
    }

    public void setStPasang(List<StPasangItem> stPasang){
        this.stPasang = stPasang;
    }

    public List<StPasangItem> getStPasang(){
        return stPasang;
    }

    public void setStVer(List<StVerItem> stVer){
        this.stVer = stVer;
    }

    public List<StVerItem> getStVer(){
        return stVer;
    }

    public void setStatusMeter(List<StatusMeterItem> statusMeter){
        this.statusMeter = statusMeter;
    }

    public List<StatusMeterItem> getStatusMeter(){
        return statusMeter;
    }

    public void setBagian(List<BagianItem> bagian){
        this.bagian = bagian;
    }

    public List<BagianItem> getBagian(){
        return bagian;
    }
}