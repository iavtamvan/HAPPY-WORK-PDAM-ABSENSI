package com.pdamkotasmg.happywork.fitur.authentication.login.model

import com.google.gson.annotations.SerializedName

data class Device(

    @field:SerializedName("product")
    val product: String,

    @field:SerializedName("build_incremental")
    val buildIncremental: String,

    @field:SerializedName("connection_type")
    val connectionType: String,

    @field:SerializedName("app_version")
    val appVersion: String,

    @field:SerializedName("build_brand")
    val buildBrand: String,

    @field:SerializedName("os_version")
    val osVersion: String,

    @field:SerializedName("latitude")
    val latitude: String,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("ip_address")
    val ipAddress: String,

    @field:SerializedName("ssid")
    val ssid: String,

    @field:SerializedName("npp")
    val npp: String,

    @field:SerializedName("session_token")
    val sessionToken: String,

    @field:SerializedName("user_id")
    val userId: Int,

    @field:SerializedName("location_city")
    val locationCity: String,

    @field:SerializedName("sdk_version")
    val sdkVersion: String,

    @field:SerializedName("build_number")
    val buildNumber: String,

    @field:SerializedName("model")
    val model: String,

    @field:SerializedName("hwid")
    val hwid: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("device")
    val device: String,

    @field:SerializedName("longitude")
    val longitude: String
)