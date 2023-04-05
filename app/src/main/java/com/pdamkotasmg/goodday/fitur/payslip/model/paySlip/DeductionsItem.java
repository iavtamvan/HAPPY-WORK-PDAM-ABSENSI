package com.pdamkotasmg.goodday.fitur.payslip.model.paySlip;

import com.google.gson.annotations.SerializedName;

public class DeductionsItem{

    @SerializedName("nominal")
    private Integer nominal;

    @SerializedName("component_name")
    private String componentName;

    @SerializedName("component_code")
    private String componentCode;

    public void setNominal(Integer nominal){
        this.nominal = nominal;
    }

    public Integer getNominal(){
        return nominal;
    }

    public void setComponentName(String componentName){
        this.componentName = componentName;
    }

    public String getComponentName(){
        return componentName;
    }

    public void setComponentCode(String componentCode){
        this.componentCode = componentCode;
    }

    public String getComponentCode(){
        return componentCode;
    }
}