package com.pdamkotasmg.goodday.fitur.payslip.model;

import com.google.gson.annotations.SerializedName;

public class DataItem{

    @SerializedName("pay_date")
    private String payDate;

    @SerializedName("period_year")
    private Integer periodYear;

    @SerializedName("period_month")
    private Integer periodMonth;

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

    public void setPeriodYear(Integer periodYear){
        this.periodYear = periodYear;
    }

    public Integer getPeriodYear(){
        return periodYear;
    }

    public void setPeriodMonth(Integer periodMonth){
        this.periodMonth = periodMonth;
    }

    public Integer getPeriodMonth(){
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