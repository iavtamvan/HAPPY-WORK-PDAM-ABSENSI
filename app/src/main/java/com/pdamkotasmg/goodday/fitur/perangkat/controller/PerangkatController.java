package com.pdamkotasmg.goodday.fitur.perangkat.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pdamkotasmg.goodday.api.server.ApiConfig;
import com.pdamkotasmg.goodday.api.server.ApiService;
import com.pdamkotasmg.goodday.fitur.perangkat.PerangkatActivity;
import com.pdamkotasmg.goodday.fitur.perangkat.adapter.PerangkatAdapter;
import com.pdamkotasmg.goodday.fitur.perangkat.model.DataItem;
import com.pdamkotasmg.goodday.fitur.perangkat.model.PerangkatRootModel;
import com.pdamkotasmg.goodday.fitur.splash.SplashScreenActivity;
import com.pdamkotasmg.goodday.utils.Config;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerangkatController {
    private static final String TAG = "debug";
    private PerangkatAdapter perangkatAdapter;
    private List<DataItem> dataItem;

    public void perangkatOnline(Context context, RecyclerView rv) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        dataItem = new ArrayList<>();
        Log.d(TAG, "tokenActive: " + sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, ""));
        ApiService apiService = ApiConfig.getApiService();
        apiService.getHistoryAktifDevice(sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "")).enqueue(new Callback<PerangkatRootModel>() {
            @Override
            public void onResponse(Call<PerangkatRootModel> call, Response<PerangkatRootModel> response) {
                Log.d(TAG, "onResponse: " + response.message());
                if (response.isSuccessful()) {
                    dataItem = response.body().getData();
                    perangkatAdapter = new PerangkatAdapter(context, dataItem);
                    rv.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
                    rv.setAdapter(perangkatAdapter);
                    perangkatAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "" + Config.ERROR_SESSION, Toast.LENGTH_SHORT).show();
                    ((PerangkatActivity) context).finishAffinity();
                    context.startActivity(new Intent(context, SplashScreenActivity.class));
                }
            }

            @Override
            public void onFailure(Call<PerangkatRootModel> call, Throwable t) {
                Toast.makeText(context, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void deleteAllSession(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        ApiService apiService = ApiConfig.getApiService();
        apiService.deleteAllSession(sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "")).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Sukses hapus sesi", Toast.LENGTH_SHORT).show();
                    ((PerangkatActivity) context).finishAffinity();
                    context.startActivity(new Intent(context, SplashScreenActivity.class));
                } else {
                    Toast.makeText(context, "Gagal hapus sesi" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
