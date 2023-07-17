package co.id.pdamkotasmg.model.pelanggan

import com.google.gson.annotations.SerializedName

 class PelangganModel(

    @field:SerializedName("data")
    val data: List<DataItem?>? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Int? = null
)

 class DataItem(

    @field:SerializedName("user_transfer")
    val userTransfer: Any? = null,

    @field:SerializedName("jam_baca")
    val jamBaca: Any? = null,

    @field:SerializedName("tgl_ver")
    val tglVer: Any? = null,

    @field:SerializedName("urut")
    val urut: Any? = null,

    @field:SerializedName("lalu")
    val lalu: Int? = null,

    @field:SerializedName("user_ver")
    val userVer: String? = null,

    @field:SerializedName("pc_entry")
    val pcEntry: Any? = null,

    @field:SerializedName("periode")
    val periode: String? = null,

    @field:SerializedName("petugas")
    val petugas: Any? = null,

    @field:SerializedName("tgl_data")
    val tglData: String? = null,

    @field:SerializedName("dt")
    val dt: Int? = null,

    @field:SerializedName("file")
    val file: Any? = null,

    @field:SerializedName("ke")
    val ke: Int? = null,

    @field:SerializedName("kever")
    val kever: Int? = null,

    @field:SerializedName("tgl_baca")
    val tglBaca: Any? = null,

    @field:SerializedName("st")
    val st: String? = null,

    @field:SerializedName("cabang")
    val cabang: String? = null,

    @field:SerializedName("m3")
    val m3: Int? = null,

    @field:SerializedName("kt")
    val kt: Any? = null,

    @field:SerializedName("stver")
    val stver: Int? = null,

    @field:SerializedName("kini")
    val kini: Int? = null,

    @field:SerializedName("tgl_transfer")
    val tglTransfer: Any? = null,

    @field:SerializedName("ip_entry")
    val ipEntry: Any? = null,

    @field:SerializedName("nolangg")
    val nolangg: String? = null,

    @field:SerializedName("dism")
    val dism: String? = null,

    @field:SerializedName("user_entry")
    val userEntry: Any? = null,

    @field:SerializedName("catatanver")
    val catatanver: Any? = null,

    @field:SerializedName("tanggal")
    val tanggal: Any? = null,

    @field:SerializedName("jam_ver")
    val jamVer: Any? = null
)
