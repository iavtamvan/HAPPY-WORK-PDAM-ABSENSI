package com.pdamkotasmg.happywork.fitur.profil.controller

import android.content.Context
import android.widget.Toast
import com.pdamkotasmg.happywork.api.server.ApiConfig
import com.pdamkotasmg.happywork.utils.Config
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileController {
    fun logout(context: Context?) {
        val sharedPreferences = context?.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val apiService = ApiConfig.getApiService()
        apiService?.logout(sharedPreferences?.getString(Config.SHARED_ACCESS_TOKEN, ""))
                ?.enqueue(object : Callback<ResponseBody?> {
                    override fun onResponse(call: Call<ResponseBody?>?, response: Response<ResponseBody?>?) {
                        if (response?.isSuccessful() == true) {
                            Toast.makeText(context, "" + response.message() + " KELUAR", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseBody?>?, t: Throwable?) {
                        Toast.makeText(context, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show()
                    }
                })
    }
}