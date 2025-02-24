package co.id.pdamkotasmg.model.home;

import com.google.gson.annotations.SerializedName;

public class JamAwal{

    @SerializedName("jam_baca")
    private String jamBaca;

    @SerializedName("tgl_baca")
    private String tglBaca;

    public void setJamBaca(String jamBaca){
        this.jamBaca = jamBaca;
    }

    public String getJamBaca(){
        return jamBaca;
    }

    public void setTglBaca(String tglBaca){
        this.tglBaca = tglBaca;
    }

    public String getTglBaca(){
        return tglBaca;
    }
}