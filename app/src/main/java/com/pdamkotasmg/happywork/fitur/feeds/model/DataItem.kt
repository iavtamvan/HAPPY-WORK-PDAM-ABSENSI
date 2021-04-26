package com.pdamkotasmg.happywork.fitur.feeds.model

import com.google.gson.annotations.SerializedName

data class DataItem(
        @SerializedName("hits")
        var hits: String? = null,

        @SerializedName("fav")
        var fav: String? = null,

        @SerializedName("id")
        var id: String? = null,

        @SerializedName("title")
        var title: String? = null,

        @SerializedName("category")
        var category: String? = null,

        @SerializedName("tgl_publish")
        var tglPublish: String? = null,

        @SerializedName("url")
        var url: String? = null,

        @SerializedName("content")
        var content: String? = null,

        @SerializedName("desc")
        var desc: String? = null
)