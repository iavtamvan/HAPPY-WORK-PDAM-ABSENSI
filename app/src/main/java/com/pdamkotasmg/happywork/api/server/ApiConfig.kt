package com.pdamkotasmg.happywork.api.server

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    fun getApiService(): ApiService? {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://app.pdamkotasmg.co.id/api-gw-dev/portal-pegawai/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return retrofit.create(ApiService::class.java)
    }
}