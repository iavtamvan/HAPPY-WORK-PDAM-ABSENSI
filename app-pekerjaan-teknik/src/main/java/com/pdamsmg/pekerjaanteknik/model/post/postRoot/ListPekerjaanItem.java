package com.pdamsmg.pekerjaanteknik.model.post.postRoot;

import com.google.gson.annotations.SerializedName;

public class ListPekerjaanItem{

    @SerializedName("jns_pekrj")
    private String jnsPekrj;

    @SerializedName("jns_pekrj_lain")
    private String jnsPekrjLain;

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
}