package com.pdamkotasmg.happywork.fitur.splash.model.androidVersion;

import com.google.gson.annotations.SerializedName;

public class Data{

    @SerializedName("android-app-name")
    private String androidAppName;

    @SerializedName("android-logo-url")
    private String androidLogoUrl;

    @SerializedName("android-version-alpha")
    private String androidVersionAlpha;

    @SerializedName("android-version-beta")
    private String androidVersionBeta;

    @SerializedName("android-version-latest")
    private String androidVersionLatest;

    @SerializedName("android-version-min")
    private String androidVersionMin;

    @SerializedName("android-version-rc")
    private String androidVersionRc;

    public String getAndroidAppName() {
        return androidAppName;
    }

    public void setAndroidAppName(String androidAppName) {
        this.androidAppName = androidAppName;
    }

    public String getAndroidLogoUrl() {
        return androidLogoUrl;
    }

    public void setAndroidLogoUrl(String androidLogoUrl) {
        this.androidLogoUrl = androidLogoUrl;
    }

    public String getAndroidVersionAlpha() {
        return androidVersionAlpha;
    }

    public void setAndroidVersionAlpha(String androidVersionAlpha) {
        this.androidVersionAlpha = androidVersionAlpha;
    }

    public String getAndroidVersionBeta() {
        return androidVersionBeta;
    }

    public void setAndroidVersionBeta(String androidVersionBeta) {
        this.androidVersionBeta = androidVersionBeta;
    }

    public String getAndroidVersionLatest() {
        return androidVersionLatest;
    }

    public void setAndroidVersionLatest(String androidVersionLatest) {
        this.androidVersionLatest = androidVersionLatest;
    }

    public String getAndroidVersionMin() {
        return androidVersionMin;
    }

    public void setAndroidVersionMin(String androidVersionMin) {
        this.androidVersionMin = androidVersionMin;
    }

    public String getAndroidVersionRc() {
        return androidVersionRc;
    }

    public void setAndroidVersionRc(String androidVersionRc) {
        this.androidVersionRc = androidVersionRc;
    }
}