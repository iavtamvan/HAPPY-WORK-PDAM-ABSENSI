package com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran.model.myStaff;

import com.google.gson.annotations.SerializedName;

public class DataItem{

    @SerializedName("gender")
    private String gender;

    @SerializedName("employement_status")
    private String employementStatus;

    @SerializedName("orgunit")
    private String orgunit;

    @SerializedName("name")
    private String name;

    @SerializedName("position")
    private String position;

    @SerializedName("job_grade")
    private String jobGrade;

    @SerializedName("npp")
    private String npp;

    public void setGender(String gender){
        this.gender = gender;
    }

    public String getGender(){
        return gender;
    }

    public void setEmployementStatus(String employementStatus){
        this.employementStatus = employementStatus;
    }

    public String getEmployementStatus(){
        return employementStatus;
    }

    public void setOrgunit(String orgunit){
        this.orgunit = orgunit;
    }

    public String getOrgunit(){
        return orgunit;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setPosition(String position){
        this.position = position;
    }

    public String getPosition(){
        return position;
    }

    public void setJobGrade(String jobGrade){
        this.jobGrade = jobGrade;
    }

    public String getJobGrade(){
        return jobGrade;
    }

    public void setNpp(String npp){
        this.npp = npp;
    }

    public String getNpp(){
        return npp;
    }
}