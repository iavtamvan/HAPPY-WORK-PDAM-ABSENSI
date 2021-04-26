package com.pdamkotasmg.happywork.fitur.splash.model.packageName

import com.google.gson.annotations.SerializedName

data class Data(

    @field:SerializedName("per_page")
    val perPage: Int? = null,

    @field:SerializedName("data")
    val data: List<DataItem?>,

    @field:SerializedName("last_page")
    val lastPage: Int? = null,

    @field:SerializedName("next_page_url")
    val nextPageUrl: String? = null,

    @field:SerializedName("prev_page_url")
    val prevPageUrl: String? = null,

    @field:SerializedName("first_page_url")
    val firstPageUrl: String? = null,

    @field:SerializedName("path")
    val path: String? = null,

    @field:SerializedName("total")
    val total: Int? = null,

    @field:SerializedName("last_page_url")
    val lastPageUrl: String? = null,

    @field:SerializedName("from")
    val from: Int? = null,

    @field:SerializedName("links")
    val links: List<LinksItem?>? = null,

    @field:SerializedName("to")
    val to: Int? = null,

    @field:SerializedName("current_page")
    val currentPage: Int? = null
)