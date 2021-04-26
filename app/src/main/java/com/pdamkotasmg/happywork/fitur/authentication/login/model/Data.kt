package com.pdamkotasmg.happywork.fitur.authentication.login.model

import com.google.gson.annotations.SerializedName

data class Data(

    @field:SerializedName("access_token")
    val accessToken: String,

    @field:SerializedName("session")
    val session: Session,

    @field:SerializedName("token_type")
    val tokenType: String,

    @field:SerializedName("expires_in")
    val expiresIn: Int,

    @field:SerializedName("user")
    val user: User,

    @field:SerializedName("device")
    val device: Device
)