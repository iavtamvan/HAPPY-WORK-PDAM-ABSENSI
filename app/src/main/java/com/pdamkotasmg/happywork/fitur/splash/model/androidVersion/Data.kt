package com.pdamkotasmg.happywork.fitur.splash.model.androidVersion

import com.google.gson.annotations.SerializedName

data class Data(

    @field:SerializedName("android-version-latest")
    val androidVersionLatest: String? = null,

    @field:SerializedName("android-logo-url")
    val androidLogoUrl: String? = null,

    @field:SerializedName("android-version-rc")
    val androidVersionRc: String? = null,

    @field:SerializedName("android-app-name")
    val androidAppName: String? = null,

    @field:SerializedName("android-version-alpha")
    val androidVersionAlpha: String? = null,

    @field:SerializedName("android-version-min")
    val androidVersionMin: String? = null,

    @field:SerializedName("android-version-beta")
    val androidVersionBeta: String? = null
)