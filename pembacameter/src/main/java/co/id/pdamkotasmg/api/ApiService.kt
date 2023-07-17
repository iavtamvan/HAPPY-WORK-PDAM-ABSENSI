package co.id.pdamkotasmg.api

import co.id.pdamkotasmg.model.pelanggan.PelangganModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    // Get current weather data
    @GET("m/plg-nolangg")
    fun getByNolangg(
        @Query("nolangg") nolangg: String
    ): Call<PelangganModel>
}