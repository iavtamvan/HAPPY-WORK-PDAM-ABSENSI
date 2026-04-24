package com.pdamkotasmg.goodday.fitur.payslip.model;

import com.google.gson.annotations.SerializedName;

public class DataItem{

    @SerializedName("pay_date")
    private String payDate;

    @SerializedName("period_year")
    private String periodYear;

    @SerializedName("period_month")
    private String periodMonth;

    @SerializedName("payroll_period_remark")
    private String payrollPeriodRemark;

    @SerializedName("payroll_period")
    private String payrollPeriod;

    public void setPayDate(String payDate){
        this.payDate = payDate;
    }

    public String getPayDate(){
        return payDate;
    }

    public void setPeriodYear(String periodYear){
        this.periodYear = periodYear;
    }

    public String getPeriodYear(){
        return periodYear;
    }

    public void setPeriodMonth(String periodMonth){
        this.periodMonth = periodMonth;
    }

    public String getPeriodMonth(){
        return periodMonth;
    }

    public void setPayrollPeriodRemark(String payrollPeriodRemark){
        this.payrollPeriodRemark = payrollPeriodRemark;
    }

    public String getPayrollPeriodRemark(){
        return payrollPeriodRemark;
    }

    public void setPayrollPeriod(String payrollPeriod){
        this.payrollPeriod = payrollPeriod;
    }

    public String getPayrollPeriod(){
        return payrollPeriod;
    }
}