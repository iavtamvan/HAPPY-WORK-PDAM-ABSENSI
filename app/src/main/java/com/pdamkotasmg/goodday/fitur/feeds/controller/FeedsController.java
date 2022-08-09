package com.pdamkotasmg.goodday.fitur.feeds.controller;

import android.content.Context;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pdamkotasmg.goodday.api.server.ApiConfig;
import com.pdamkotasmg.goodday.api.server.ApiService;
import com.pdamkotasmg.goodday.fitur.feeds.adapter.FeedsAdapter;
import com.pdamkotasmg.goodday.fitur.feeds.model.BeritaRootModel;
import com.pdamkotasmg.goodday.fitur.feeds.model.DataItem;
import com.pdamkotasmg.goodday.utils.Config;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedsController {
    private List<DataItem> dataItems;
    private FeedsAdapter feedsAdapter;

    public void getFeeds(Context context, RecyclerView rv) {
        dataItems = new ArrayList<>();
        ApiService apiService = ApiConfig.getApiService(context);
        apiService.getNews().enqueue(new Callback<BeritaRootModel>() {
            @Override
            public void onResponse(Call<BeritaRootModel> call, Response<BeritaRootModel> response) {
                if (response.isSuccessful()) {
                    dataItems = response.body().getData();
                    feedsAdapter = new FeedsAdapter(context, dataItems);
                    rv.setAdapter(feedsAdapter);
                    rv.setLayoutManager(new LinearLayoutManager(context));
                    feedsAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<BeritaRootModel> call, Throwable t) {
                Toast.makeText(context, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
