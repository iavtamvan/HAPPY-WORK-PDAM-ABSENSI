package co.id.pdamkotasmg.pekerjaanteknik.model.barang;

import com.google.gson.annotations.SerializedName;

public class RlBarangJenis1{

    @SerializedName("nm_jns1")
    private String nmJns1;

    public void setNmJns1(String nmJns1){
        this.nmJns1 = nmJns1;
    }

    public String getNmJns1(){
        return nmJns1;
    }
}