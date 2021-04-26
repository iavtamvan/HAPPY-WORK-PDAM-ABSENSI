package com.pdamkotasmg.happywork.api.google

import com.pdamkotasmg.happywork.api.server.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfigGoogle {
    fun getApiService(): ApiService? {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://www.google.com/recaptcha/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        return retrofit.create(ApiService::class.java)
    }
}