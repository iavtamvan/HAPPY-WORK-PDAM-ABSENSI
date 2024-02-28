package com.pdamkotasmg.goodday.fitur.menuLainnya.profilePelanggan.model.kardek;

import com.google.gson.annotations.SerializedName;

public class FotoMeter{

    @SerializedName("maret")
    private Maret maret;

    @SerializedName("mei")
    private Mei mei;

    @SerializedName("november")
    private November november;

    @SerializedName("september")
    private September september;

    @SerializedName("januari")
    private Januari januari;

    @SerializedName("februari")
    private Februari februari;

    @SerializedName("juni")
    private Juni juni;

    @SerializedName("agustus")
    private Agustus agustus;

    @SerializedName("oktober")
    private Oktober oktober;

    @SerializedName("juli")
    private Juli juli;

    @SerializedName("april")
    private April april;

    @SerializedName("desember")
    private Desember desember;

    public void setMaret(Maret maret){
        this.maret = maret;
    }

    public Maret getMaret(){
        return maret;
    }

    public void setMei(Mei mei){
        this.mei = mei;
    }

    public Mei getMei(){
        return mei;
    }

    public void setNovember(November november){
        this.november = november;
    }

    public November getNovember(){
        return november;
    }

    public void setSeptember(September september){
        this.september = september;
    }

    public September getSeptember(){
        return september;
    }

    public void setJanuari(Januari januari){
        this.januari = januari;
    }

    public Januari getJanuari(){
        return januari;
    }

    public void setFebruari(Februari februari){
        this.februari = februari;
    }

    public Februari getFebruari(){
        return februari;
    }

    public void setJuni(Juni juni){
        this.juni = juni;
    }

    public Juni getJuni(){
        return juni;
    }

    public void setAgustus(Agustus agustus){
        this.agustus = agustus;
    }

    public Agustus getAgustus(){
        return agustus;
    }

    public void setOktober(Oktober oktober){
        this.oktober = oktober;
    }

    public Oktober getOktober(){
        return oktober;
    }

    public void setJuli(Juli juli){
        this.juli = juli;
    }

    public Juli getJuli(){
        return juli;
    }

    public void setApril(April april){
        this.april = april;
    }

    public April getApril(){
        return april;
    }

    public void setDesember(Desember desember){
        this.desember = desember;
    }

    public Desember getDesember(){
        return desember;
    }
}