package com.pdamkotasmg.happywork.fitur.perangkat.model

import com.google.gson.annotations.SerializedName

data class PerangkatRootModel(

    @field:SerializedName("data")
    val data: List<DataItem?>? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Int? = null
)