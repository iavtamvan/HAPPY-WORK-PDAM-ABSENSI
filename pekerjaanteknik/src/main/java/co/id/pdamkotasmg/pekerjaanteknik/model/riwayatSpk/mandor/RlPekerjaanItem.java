package co.id.pdamkotasmg.pekerjaanteknik.model.riwayatSpk.mandor;

import com.google.gson.annotations.SerializedName;

public class RlPekerjaanItem{

    @SerializedName("jns_pekrj")
    private String jnsPekrj;

    @SerializedName("jns_pekrj_lain")
    private String jnsPekrjLain;

    @SerializedName("no_spk")
    private String noSpk;

    public void setJnsPekrj(String jnsPekrj){
        this.jnsPekrj = jnsPekrj;
    }

    public String getJnsPekrj(){
        return jnsPekrj;
    }

    public void setJnsPekrjLain(String jnsPekrjLain){
        this.jnsPekrjLain = jnsPekrjLain;
    }

    public String getJnsPekrjLain(){
        return jnsPekrjLain;
    }

    public void setNoSpk(String noSpk){
        this.noSpk = noSpk;
    }

    public String getNoSpk(){
        return noSpk;
    }
}