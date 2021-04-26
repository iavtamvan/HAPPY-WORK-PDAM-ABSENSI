package com.pdamkotasmg.happywork.fitur.authentication.login.model

import com.google.gson.annotations.SerializedName

data class Session(

    @field:SerializedName("is_expired")
    val isExpired: Boolean,

    @field:SerializedName("session_id")
    val sessionId: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("npp")
    val npp: String
)