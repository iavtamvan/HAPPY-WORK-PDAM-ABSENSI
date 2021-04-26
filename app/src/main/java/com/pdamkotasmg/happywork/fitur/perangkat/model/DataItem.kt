package com.pdamkotasmg.happywork.fitur.perangkat.model

import com.google.gson.annotations.SerializedName

data class DataItem(

    @field:SerializedName("product")
    val product: String? = null,

    @field:SerializedName("build_incremental")
    val buildIncremental: String? = null,

    @field:SerializedName("connection_type")
    val connectionType: String? = null,

    @field:SerializedName("app_version")
    val appVersion: String? = null,

    @field:SerializedName("build_brand")
    val buildBrand: String? = null,

    @field:SerializedName("os_version")
    val osVersion: String? = null,

    @field:SerializedName("latitude")
    val latitude: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("ip_address")
    val ipAddress: String? = null,

    @field:SerializedName("ssid")
    val ssid: String? = null,

    @field:SerializedName("npp")
    val npp: String? = null,

    @field:SerializedName("session_token")
    val sessionToken: String? = null,

    @field:SerializedName("user_id")
    val userId: Int? = null,

    @field:SerializedName("location_city")
    val locationCity: String? = null,

    @field:SerializedName("sdk_version")
    val sdkVersion: String? = null,

    @field:SerializedName("build_number")
    val buildNumber: String? = null,

    @field:SerializedName("model")
    val model: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("hwid")
    val hwid: String? = null,

    @field:SerializedName("device")
    val device: String? = null,

    @field:SerializedName("longitude")
    val longitude: String? = null
)