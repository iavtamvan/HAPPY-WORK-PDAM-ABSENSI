package id.pdamkotasmg.pekerjaan_teknik_feature.model.tagihan;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Data{

    @SerializedName("suplisi")
    private List<Object> suplisi;

    @SerializedName("tunggakan")
    private List<TunggakanItem> tunggakan;

    @SerializedName("angsuran_rek")
    private List<AngsuranRekItem> angsuranRek;

    @SerializedName("flat")
    private List<FlatItem> flat;

    @SerializedName("rutin")
    private Rutin rutin;

    @SerializedName("pelanggan")
    private Pelanggan pelanggan;

    public void setSuplisi(List<Object> suplisi){
        this.suplisi = suplisi;
    }

    public List<Object> getSuplisi(){
        return suplisi;
    }

    public void setTunggakan(List<TunggakanItem> tunggakan){
        this.tunggakan = tunggakan;
    }

    public List<TunggakanItem> getTunggakan(){
        return tunggakan;
    }

    public void setAngsuranRek(List<AngsuranRekItem> angsuranRek){
        this.angsuranRek = angsuranRek;
    }

    public List<AngsuranRekItem> getAngsuranRek(){
        return angsuranRek;
    }

    public void setFlat(List<FlatItem> flat){
        this.flat = flat;
    }

    public List<FlatItem> getFlat(){
        return flat;
    }

    public void setRutin(Rutin rutin){
        this.rutin = rutin;
    }

    public Rutin getRutin(){
        return rutin;
    }

    public void setPelanggan(Pelanggan pelanggan){
        this.pelanggan = pelanggan;
    }

    public Pelanggan getPelanggan(){
        return pelanggan;
    }
}