package com.pdamkotasmg.happywork.fitur.splash.model.packageName

import com.google.gson.annotations.SerializedName

data class DataItem(

    @field:SerializedName("app_name")
    val appName: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("user_id")
    val userId: String? = null,

    @field:SerializedName("package_name")
    val packageName: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("deleted_at")
    val deletedAt: String? = null
)