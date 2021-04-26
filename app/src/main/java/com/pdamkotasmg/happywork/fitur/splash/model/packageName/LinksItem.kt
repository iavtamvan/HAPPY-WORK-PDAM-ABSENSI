package com.pdamkotasmg.happywork.fitur.splash.model.packageName

import com.google.gson.annotations.SerializedName

data class LinksItem(

    @field:SerializedName("active")
    val active: Boolean? = null,

    @field:SerializedName("label")
    val label: String? = null,

    @field:SerializedName("url")
    val url: String? = null
)