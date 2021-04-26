package com.pdamkotasmg.happywork.fitur.splash.model.androidVersion

import com.google.gson.annotations.SerializedName

data class AndroidVersionModel(

    @field:SerializedName("data")
    val data: Data? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Int? = null
)