package com.pdamkotasmg.happywork.fitur.authentication.login.model

import com.google.gson.annotations.SerializedName

data class AkunRootModel(

    @field:SerializedName("data")
    val data: Data,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("status")
    val status: Int
)