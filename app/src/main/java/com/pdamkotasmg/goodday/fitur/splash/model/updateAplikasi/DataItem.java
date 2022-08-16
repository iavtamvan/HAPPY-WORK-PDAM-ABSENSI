package com.pdamkotasmg.goodday.fitur.splash.model.updateAplikasi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataItem{

	@SerializedName("image_header")
	private String imageHeader;

	@SerializedName("peringatan")
	private List<Object> peringatan;

	@SerializedName("name_dashboard")
	private String nameDashboard;

	@SerializedName("logo_apps")
	private String logoApps;

	@SerializedName("header_profil")
	private String headerProfil;

	@SerializedName("update_apk")
	private String updateApk;

	@SerializedName("warning")
	private List<String> warning;

	@SerializedName("hello")
	private String hello;

	@SerializedName("message_update")
	private List<String> messageUpdate;

	@SerializedName("info")
	private List<String> info;

	public String getHeaderProfil() {
		return headerProfil;
	}

	public void setHeaderProfil(String headerProfil) {
		this.headerProfil = headerProfil;
	}

	public String getLogoApps() {
		return logoApps;
	}

	public void setLogoApps(String logoApps) {
		this.logoApps = logoApps;
	}

	public String getNameDashboard() {
		return nameDashboard;
	}

	public void setNameDashboard(String nameDashboard) {
		this.nameDashboard = nameDashboard;
	}

	public void setImageHeader(String imageHeader){
		this.imageHeader = imageHeader;
	}

	public String getImageHeader(){
		return imageHeader;
	}

	public void setPeringatan(List<Object> peringatan){
		this.peringatan = peringatan;
	}

	public List<Object> getPeringatan(){
		return peringatan;
	}

	public void setUpdateApk(String updateApk){
		this.updateApk = updateApk;
	}

	public String getUpdateApk(){
		return updateApk;
	}

	public void setWarning(List<String> warning){
		this.warning = warning;
	}

	public List<String> getWarning(){
		return warning;
	}

	public void setHello(String hello){
		this.hello = hello;
	}

	public String getHello(){
		return hello;
	}

	public void setMessageUpdate(List<String> messageUpdate){
		this.messageUpdate = messageUpdate;
	}

	public List<String> getMessageUpdate(){
		return messageUpdate;
	}

	public void setInfo(List<String> info){
		this.info = info;
	}

	public List<String> getInfo(){
		return info;
	}
}