package com.pdamkotasmg.happywork.fitur.perangkat.controller

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pdamkotasmg.happywork.api.server.ApiConfig
import com.pdamkotasmg.happywork.fitur.perangkat.PerangkatActivity
import com.pdamkotasmg.happywork.fitur.perangkat.adapter.PerangkatAdapter
import com.pdamkotasmg.happywork.fitur.perangkat.model.DataItem
import com.pdamkotasmg.happywork.fitur.perangkat.model.PerangkatRootModel
import com.pdamkotasmg.happywork.fitur.splash.SplashScreenActivity
import com.pdamkotasmg.happywork.utils.Config
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class PerangkatController {
    private val TAG: String = "debug"
    private var perangkatAdapter: PerangkatAdapter? = null
    private var dataItem: List<DataItem?>? = null
    fun perangkatOnline(context: Context?, rv: RecyclerView?) {
        val sharedPreferences = context?.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE)
        dataItem = ArrayList()
        Log.d(TAG, "tokenActive: " + sharedPreferences?.getString(Config.SHARED_ACCESS_TOKEN, ""))
        val apiService = ApiConfig.getApiService()
        apiService?.getHistoryAktifDevice(sharedPreferences?.getString(Config.SHARED_ACCESS_TOKEN, ""))?.enqueue(object : Callback<PerangkatRootModel?> {
            override fun onResponse(call: Call<PerangkatRootModel?>?, response: Response<PerangkatRootModel?>?) {
                Log.d(TAG, "onResponse: " + response?.message())
                if (response?.isSuccessful() == true) {
                    dataItem = response.body()?.data
                    perangkatAdapter = PerangkatAdapter(context, dataItem)
                    rv?.setLayoutManager(LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false))
                    rv?.setAdapter(perangkatAdapter)
                    perangkatAdapter?.notifyDataSetChanged()
                } else {
                    Toast.makeText(context, "" + Config.ERROR_SESSION, Toast.LENGTH_SHORT).show()
                    (context as PerangkatActivity?)?.finishAffinity()
                    context?.startActivity(Intent(context, SplashScreenActivity::class.java))
                }
            }

            override fun onFailure(call: Call<PerangkatRootModel?>?, t: Throwable?) {
                Toast.makeText(context, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun deleteAllSession(context: Context?) {
        val sharedPreferences = context?.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val apiService = ApiConfig.getApiService()
        apiService?.deleteAllSession(sharedPreferences?.getString(Config.SHARED_ACCESS_TOKEN, ""))?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(call: Call<ResponseBody?>?, response: Response<ResponseBody?>?) {
                if (response?.isSuccessful() == true) {
                    Toast.makeText(context, "Sukses hapus sesi", Toast.LENGTH_SHORT).show()
                    (context as PerangkatActivity?)?.finishAffinity()
                    context?.startActivity(Intent(context, SplashScreenActivity::class.java))
                } else {
                    Toast.makeText(context, "Gagal hapus sesi" + response?.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody?>?, t: Throwable?) {
                Toast.makeText(context, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show()
            }
        })
    }
}