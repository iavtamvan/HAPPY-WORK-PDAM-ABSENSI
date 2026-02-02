package com.pdamsmg.pekerjaanteknik.model.riwayatSpk.mandor;

import com.google.gson.annotations.SerializedName;

public class RlPelaksanaDtItem{

    @SerializedName("rl_pelaksana_dt_to_m_brg")
    private RlPelaksanaDtToMBrg rlPelaksanaDtToMBrg;

    @SerializedName("kd_brg")
    private String kdBrg;

    @SerializedName("no_spk")
    private String noSpk;

    public void setRlPelaksanaDtToMBrg(RlPelaksanaDtToMBrg rlPelaksanaDtToMBrg){
        this.rlPelaksanaDtToMBrg = rlPelaksanaDtToMBrg;
    }

    public RlPelaksanaDtToMBrg getRlPelaksanaDtToMBrg(){
        return rlPelaksanaDtToMBrg;
    }

    public void setKdBrg(String kdBrg){
        this.kdBrg = kdBrg;
    }

    public String getKdBrg(){
        return kdBrg;
    }

    public void setNoSpk(String noSpk){
        this.noSpk = noSpk;
    }

    public String getNoSpk(){
        return noSpk;
    }
}