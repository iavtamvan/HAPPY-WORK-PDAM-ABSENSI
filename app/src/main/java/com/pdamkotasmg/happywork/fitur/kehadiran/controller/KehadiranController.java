package com.pdamkotasmg.happywork.fitur.kehadiran.controller;

import android.content.Context;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pdamkotasmg.happywork.api.server.ApiConfig;
import com.pdamkotasmg.happywork.api.server.ApiService;
import com.pdamkotasmg.happywork.fitur.feeds.model.BeritaRootModel;
import com.pdamkotasmg.happywork.fitur.feeds.model.DataItem;
import com.pdamkotasmg.happywork.fitur.kehadiran.adapter.KehadiranAdapter;
import com.pdamkotasmg.happywork.utils.Config;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KehadiranController {
    private List<DataItem> dataItems;
    private KehadiranAdapter kehadiranController;

    public void getKehadiran(Context context, RecyclerView rv) {
        dataItems = new ArrayList<>();
        ApiService apiService = ApiConfig.getApiService();
        apiService.getNews().enqueue(new Callback<BeritaRootModel>() {
            @Override
            public void onResponse(Call<BeritaRootModel> call, Response<BeritaRootModel> response) {
                if (response.isSuccessful()) {
                    dataItems = response.body().getData();
                    kehadiranController = new KehadiranAdapter(context, dataItems);
                    rv.setAdapter(kehadiranController);
                    rv.setLayoutManager(new LinearLayoutManager(context));
                    kehadiranController.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<BeritaRootModel> call, Throwable t) {
                Toast.makeText(context, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
