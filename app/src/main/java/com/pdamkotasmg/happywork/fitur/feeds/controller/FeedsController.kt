package com.pdamkotasmg.happywork.fitur.feeds.controller

import android.content.Context
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pdamkotasmg.happywork.api.server.ApiConfig
import com.pdamkotasmg.happywork.fitur.feeds.adapter.FeedsAdapter
import com.pdamkotasmg.happywork.fitur.feeds.model.BeritaRootModel
import com.pdamkotasmg.happywork.fitur.feeds.model.DataItem
import com.pdamkotasmg.happywork.utils.Config
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class FeedsController {
    private var dataItems: List<DataItem?>? = null
    private var feedsAdapter: FeedsAdapter? = null
    fun getFeeds(context: Context, rv: RecyclerView) {
        dataItems = ArrayList()
        val apiService = ApiConfig.getApiService()
        apiService?.getNews()?.enqueue(object : Callback<BeritaRootModel?> {
            override fun onResponse(call: Call<BeritaRootModel?>?, response: Response<BeritaRootModel?>?) {
                if (response?.isSuccessful() == true) {
                    dataItems = response.body()?.data
                    feedsAdapter = FeedsAdapter(context, dataItems)
                    rv.adapter = feedsAdapter
                    rv.layoutManager = LinearLayoutManager(context)
                    feedsAdapter?.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BeritaRootModel?>?, t: Throwable?) {
                Toast.makeText(context, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show()
            }
        })
    }
}