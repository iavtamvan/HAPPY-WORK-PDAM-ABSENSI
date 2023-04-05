package com.pdamkotasmg.goodday.fitur.payslip.model.paySlip;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Data{

    @SerializedName("terbilang")
    private String terbilang;

    @SerializedName("transfered_amount")
    private Integer transferedAmount;

    @SerializedName("tax_status")
    private String taxStatus;

    @SerializedName("employee_no")
    private String employeeNo;

    @SerializedName("tax")
    private Integer tax;

    @SerializedName("deductions")
    private List<DeductionsItem> deductions;

    @SerializedName("subtotal_earnings")
    private Integer subtotalEarnings;

    @SerializedName("net_pay")
    private Integer netPay;

    @SerializedName("transfered_to")
    private String transferedTo;

    @SerializedName("cost_center")
    private String costCenter;

    @SerializedName("total_earnings")
    private Integer totalEarnings;

    @SerializedName("name")
    private String name;

    @SerializedName("tax_ref_no")
    private String taxRefNo;

    @SerializedName("subtotal_deductions")
    private Integer subtotalDeductions;

    @SerializedName("position")
    private String position;

    @SerializedName("allowances")
    private List<AllowancesItem> allowances;

    @SerializedName("total_deductions")
    private Integer totalDeductions;

    public void setTerbilang(String terbilang){
        this.terbilang = terbilang;
    }

    public String getTerbilang(){
        return terbilang;
    }

    public void setTransferedAmount(Integer transferedAmount){
        this.transferedAmount = transferedAmount;
    }

    public Integer getTransferedAmount(){
        return transferedAmount;
    }

    public void setTaxStatus(String taxStatus){
        this.taxStatus = taxStatus;
    }

    public String getTaxStatus(){
        return taxStatus;
    }

    public void setEmployeeNo(String employeeNo){
        this.employeeNo = employeeNo;
    }

    public String getEmployeeNo(){
        return employeeNo;
    }

    public void setTax(Integer tax){
        this.tax = tax;
    }

    public Integer getTax(){
        return tax;
    }

    public void setDeductions(List<DeductionsItem> deductions){
        this.deductions = deductions;
    }

    public List<DeductionsItem> getDeductions(){
        return deductions;
    }

    public void setSubtotalEarnings(Integer subtotalEarnings){
        this.subtotalEarnings = subtotalEarnings;
    }

    public Integer getSubtotalEarnings(){
        return subtotalEarnings;
    }

    public void setNetPay(Integer netPay){
        this.netPay = netPay;
    }

    public Integer getNetPay(){
        return netPay;
    }

    public void setTransferedTo(String transferedTo){
        this.transferedTo = transferedTo;
    }

    public String getTransferedTo(){
        return transferedTo;
    }

    public void setCostCenter(String costCenter){
        this.costCenter = costCenter;
    }

    public String getCostCenter(){
        return costCenter;
    }

    public void setTotalEarnings(Integer totalEarnings){
        this.totalEarnings = totalEarnings;
    }

    public Integer getTotalEarnings(){
        return totalEarnings;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setTaxRefNo(String taxRefNo){
        this.taxRefNo = taxRefNo;
    }

    public String getTaxRefNo(){
        return taxRefNo;
    }

    public void setSubtotalDeductions(Integer subtotalDeductions){
        this.subtotalDeductions = subtotalDeductions;
    }

    public Integer getSubtotalDeductions(){
        return subtotalDeductions;
    }

    public void setPosition(String position){
        this.position = position;
    }

    public String getPosition(){
        return position;
    }

    public void setAllowances(List<AllowancesItem> allowances){
        this.allowances = allowances;
    }

    public List<AllowancesItem> getAllowances(){
        return allowances;
    }

    public void setTotalDeductions(Integer totalDeductions){
        this.totalDeductions = totalDeductions;
    }

    public Integer getTotalDeductions(){
        return totalDeductions;
    }
}