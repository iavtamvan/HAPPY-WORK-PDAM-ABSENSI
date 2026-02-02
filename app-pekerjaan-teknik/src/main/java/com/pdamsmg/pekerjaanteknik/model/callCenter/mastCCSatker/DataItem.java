package com.pdamsmg.pekerjaanteknik.model.callCenter.mastCCSatker;

import com.google.gson.annotations.SerializedName;

public class DataItem{

    @SerializedName("satker_cbg")
    private String satkerCbg;

    @SerializedName("created_at")
    private String createdAt;

    @SerializedName("deleted_at")
    private String deletedAt;

    @SerializedName("idparent_cbg")
    private String idparentCbg;

    @SerializedName("kd_cc")
    private String kdCc;

    @SerializedName("updated_at")
    private String updatedAt;

    @SerializedName("path_cbg")
    private String pathCbg;

    @SerializedName("idsatker_cbg")
    private String idsatkerCbg;

    @SerializedName("nama_cc")
    private String namaCc;

    @SerializedName("kasatkernpp_cc")
    private String kasatkernppCc;

    @SerializedName("kasatkernm_cc")
    private String kasatkernmCc;

    @SerializedName("id")
    private Integer id;

    @SerializedName("idcabang_cbg")
    private String idcabangCbg;

    @SerializedName("jabsatker_cbg")
    private String jabsatkerCbg;

    public void setSatkerCbg(String satkerCbg){
        this.satkerCbg = satkerCbg;
    }

    public String getSatkerCbg(){
        return satkerCbg;
    }

    public void setCreatedAt(String createdAt){
        this.createdAt = createdAt;
    }

    public String getCreatedAt(){
        return createdAt;
    }

    public void setDeletedAt(String deletedAt){
        this.deletedAt = deletedAt;
    }

    public String getDeletedAt(){
        return deletedAt;
    }

    public void setIdparentCbg(String idparentCbg){
        this.idparentCbg = idparentCbg;
    }

    public String getIdparentCbg(){
        return idparentCbg;
    }

    public void setKdCc(String kdCc){
        this.kdCc = kdCc;
    }

    public String getKdCc(){
        return kdCc;
    }

    public void setUpdatedAt(String updatedAt){
        this.updatedAt = updatedAt;
    }

    public String getUpdatedAt(){
        return updatedAt;
    }

    public void setPathCbg(String pathCbg){
        this.pathCbg = pathCbg;
    }

    public String getPathCbg(){
        return pathCbg;
    }

    public void setIdsatkerCbg(String idsatkerCbg){
        this.idsatkerCbg = idsatkerCbg;
    }

    public String getIdsatkerCbg(){
        return idsatkerCbg;
    }

    public void setNamaCc(String namaCc){
        this.namaCc = namaCc;
    }

    public String getNamaCc(){
        return namaCc;
    }

    public void setKasatkernppCc(String kasatkernppCc){
        this.kasatkernppCc = kasatkernppCc;
    }

    public String getKasatkernppCc(){
        return kasatkernppCc;
    }

    public void setKasatkernmCc(String kasatkernmCc){
        this.kasatkernmCc = kasatkernmCc;
    }

    public String getKasatkernmCc(){
        return kasatkernmCc;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public Integer getId(){
        return id;
    }

    public void setIdcabangCbg(String idcabangCbg){
        this.idcabangCbg = idcabangCbg;
    }

    public String getIdcabangCbg(){
        return idcabangCbg;
    }

    public void setJabsatkerCbg(String jabsatkerCbg){
        this.jabsatkerCbg = jabsatkerCbg;
    }

    public String getJabsatkerCbg(){
        return jabsatkerCbg;
    }
}