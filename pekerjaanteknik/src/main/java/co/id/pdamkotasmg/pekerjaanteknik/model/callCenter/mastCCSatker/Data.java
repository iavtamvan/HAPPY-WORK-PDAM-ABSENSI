package co.id.pdamkotasmg.pekerjaanteknik.model.callCenter.mastCCSatker;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Data{

    @SerializedName("m_cc_satker")
    private List<MCcSatkerItem> mCcSatker;

    @SerializedName("m_kategori")
    private List<MKategoriItem> mKategori;

    public void setMCcSatker(List<MCcSatkerItem> mCcSatker){
        this.mCcSatker = mCcSatker;
    }

    public List<MCcSatkerItem> getMCcSatker(){
        return mCcSatker;
    }

    public void setMKategori(List<MKategoriItem> mKategori){
        this.mKategori = mKategori;
    }

    public List<MKategoriItem> getMKategori(){
        return mKategori;
    }
}