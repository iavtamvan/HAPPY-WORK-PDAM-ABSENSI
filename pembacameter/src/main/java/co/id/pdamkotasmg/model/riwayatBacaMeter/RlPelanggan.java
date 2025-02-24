package co.id.pdamkotasmg.model.riwayatBacaMeter;

import com.google.gson.annotations.SerializedName;

public class RlPelanggan{

    @SerializedName("nama")
    private String nama;

    @SerializedName("nolangg")
    private String nolangg;

    @SerializedName("alamat")
    private String alamat;

    public void setNama(String nama){
        this.nama = nama;
    }

    public String getNama(){
        return nama;
    }

    public void setNolangg(String nolangg){
        this.nolangg = nolangg;
    }

    public String getNolangg(){
        return nolangg;
    }

    public void setAlamat(String alamat){
        this.alamat = alamat;
    }

    public String getAlamat(){
        return alamat;
    }
}