package com.pdamkotasmg.happywork.fitur.feeds.model

import com.google.gson.annotations.SerializedName

data class BeritaRootModel(
        @SerializedName("data")
        var data: List<DataItem>,

        @SerializedName("message")
        var message: String? = null,

        @SerializedName("status")
        var status: Int? = null
)