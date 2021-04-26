package com.pdamkotasmg.happywork.fitur.splash.model.packageName

import com.google.gson.annotations.SerializedName

data class PackageNameRootModel(

    @field:SerializedName("data")
    val data: Data? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Int? = null
)