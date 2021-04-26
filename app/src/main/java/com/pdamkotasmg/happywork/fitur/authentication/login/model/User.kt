package com.pdamkotasmg.happywork.fitur.authentication.login.model

import com.google.gson.annotations.SerializedName

data class User(

    @field:SerializedName("is_admin")
    val isAdmin: Int,

    @field:SerializedName("rl_pegawai")
    val rlPegawai: RlPegawai,

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("created_at")
    val createdAt: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("avatar")
    val avatar: String,

    @field:SerializedName("npp")
    val npp: String
)