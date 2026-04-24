package co.id.pdamkotasmg.model.updatePembacaMeter;

import com.google.gson.annotations.SerializedName;

public class UpdateDataLog{

    @SerializedName("st")
    private String st;

    @SerializedName("cabang")
    private String cabang;

    @SerializedName("jam_baca")
    private String jamBaca;

    @SerializedName("m3")
    private int m3;

    @SerializedName("latitude")
    private Object latitude;

    @SerializedName("kt")
    private String kt;

    @SerializedName("stver")
    private int stver;

    @SerializedName("kini")
    private String kini;

    @SerializedName("lalu")
    private int lalu;

    @SerializedName("pc_entry")
    private String pcEntry;

    @SerializedName("ip_entry")
    private Object ipEntry;

    @SerializedName("nolangg")
    private String nolangg;

    @SerializedName("periode")
    private String periode;

    @SerializedName("petugas")
    private String petugas;

    @SerializedName("tgl_data")
    private String tglData;

    @SerializedName("alamat")
    private Object alamat;

    @SerializedName("dism")
    private String dism;

    @SerializedName("dt")
    private String dt;

    @SerializedName("file")
    private String file;

    @SerializedName("user_entry")
    private String userEntry;

    @SerializedName("ke")
    private int ke;

    @SerializedName("tgl_baca")
    private String tglBaca;

    @SerializedName("longitude")
    private Object longitude;

    public String getSt(){
        return st;
    }

    public String getCabang(){
        return cabang;
    }

    public String getJamBaca(){
        return jamBaca;
    }

    public int getM3(){
        return m3;
    }

    public Object getLatitude(){
        return latitude;
    }

    public String getKt(){
        return kt;
    }

    public int getStver(){
        return stver;
    }

    public String getKini(){
        return kini;
    }

    public int getLalu(){
        return lalu;
    }

    public String getPcEntry(){
        return pcEntry;
    }

    public Object getIpEntry(){
        return ipEntry;
    }

    public String getNolangg(){
        return nolangg;
    }

    public String getPeriode(){
        return periode;
    }

    public String getPetugas(){
        return petugas;
    }

    public String getTglData(){
        return tglData;
    }

    public Object getAlamat(){
        return alamat;
    }

    public String getDism(){
        return dism;
    }

    public String getDt(){
        return dt;
    }

    public String getFile(){
        return file;
    }

    public String getUserEntry(){
        return userEntry;
    }

    public int getKe(){
        return ke;
    }

    public String getTglBaca(){
        return tglBaca;
    }

    public Object getLongitude(){
        return longitude;
    }
}