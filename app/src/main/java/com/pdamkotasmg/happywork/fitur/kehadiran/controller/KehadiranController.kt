package com.pdamkotasmg.happywork.fitur.kehadiran.controller

import android.content.Context
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pdamkotasmg.happywork.api.server.ApiConfig
import com.pdamkotasmg.happywork.fitur.feeds.model.BeritaRootModel
import com.pdamkotasmg.happywork.fitur.feeds.model.DataItem
import com.pdamkotasmg.happywork.fitur.kehadiran.adapter.KehadiranAdapter
import com.pdamkotasmg.happywork.utils.Config
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class KehadiranController {
    private var dataItems: List<DataItem?>? = null
    private var kehadiranController: KehadiranAdapter? = null
    fun getKehadiran(context: Context, rv: RecyclerView) {
        dataItems = ArrayList()
        val apiService = ApiConfig.getApiService()
        apiService?.getNews()?.enqueue(object : Callback<BeritaRootModel?> {
            override fun onResponse(call: Call<BeritaRootModel?>?, response: Response<BeritaRootModel?>?) {
                if (response?.isSuccessful() == true) {
                    dataItems = response.body()?.data
                    kehadiranController = KehadiranAdapter(context, dataItems)
                    rv.setAdapter(kehadiranController)
                    rv.setLayoutManager(LinearLayoutManager(context))
                    kehadiranController?.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BeritaRootModel?>?, t: Throwable?) {
                Toast.makeText(context, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show()
            }
        })
    }
}