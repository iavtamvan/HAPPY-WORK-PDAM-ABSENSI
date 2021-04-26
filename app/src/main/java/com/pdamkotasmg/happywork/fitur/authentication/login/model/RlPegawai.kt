package com.pdamkotasmg.happywork.fitur.authentication.login.model

import com.google.gson.annotations.SerializedName

data class RlPegawai(

    @field:SerializedName("st_data")
    val stData: Int,

    @field:SerializedName("rt")
    val rt: String,

    @field:SerializedName("rw")
    val rw: String,

    @field:SerializedName("tmpt_lahir")
    val tmptLahir: String,

    @field:SerializedName("jabatan")
    val jabatan: String,

    @field:SerializedName("agama")
    val agama: String,

    @field:SerializedName("tgl_masuk")
    val tglMasuk: String,

    @field:SerializedName("created_at")
    val createdAt: Any,

    @field:SerializedName("kd_satker")
    val kdSatker: Any,

    @field:SerializedName("updated_at")
    val updatedAt: Any,

    @field:SerializedName("pek")
    val pek: String,

    @field:SerializedName("jenis_kel")
    val jenisKel: String,

    @field:SerializedName("ket")
    val ket: String,

    @field:SerializedName("satus_peg")
    val satusPeg: String,

    @field:SerializedName("kota")
    val kota: String,

    @field:SerializedName("subsatker")
    val subsatker: String,

    @field:SerializedName("kd_subsatker")
    val kdSubsatker: Any,

    @field:SerializedName("deleted_at")
    val deletedAt: Any,

    @field:SerializedName("kelurahan")
    val kelurahan: String,

    @field:SerializedName("tgl_lahir")
    val tglLahir: String,

    @field:SerializedName("npp")
    val npp: String,

    @field:SerializedName("alamat")
    val alamat: String,

    @field:SerializedName("pktgol")
    val pktgol: String,

    @field:SerializedName("nama")
    val nama: String,

    @field:SerializedName("namasusi")
    val namasusi: String,

    @field:SerializedName("kecamatan")
    val kecamatan: String,

    @field:SerializedName("satker")
    val satker: String,

    @field:SerializedName("tlp")
    val tlp: String,

    @field:SerializedName("satker_formatted")
    val satkerFormatted: String,

    @field:SerializedName("subsatker_formatted")
    val subsatkerFormatted: String
)