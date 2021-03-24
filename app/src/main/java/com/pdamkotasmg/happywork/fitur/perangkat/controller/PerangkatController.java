package com.pdamkotasmg.happywork.fitur.perangkat.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.pdamkotasmg.happywork.api.server.ApiConfig;
import com.pdamkotasmg.happywork.api.server.ApiService;
import com.pdamkotasmg.happywork.fitur.perangkat.model.DataItem;
import com.pdamkotasmg.happywork.fitur.perangkat.model.PerangkatRootModel;
import com.pdamkotasmg.happywork.utils.Config;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerangkatController {
    private static final String TAG = "debug";
    private List<DataItem> dataItem;

    public void perangkatOnline(Context context, TextView telegramVersi, TextView hp, TextView ipCity) {
        dataItem = new ArrayList<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Log.d(TAG, "perangkatOnline: " + sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, ""));
        ApiService apiService = ApiConfig.getApiService();
        apiService.getAktifDevice(sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN, "")).enqueue(new Callback<PerangkatRootModel>() {
            @Override
            public void onResponse(Call<PerangkatRootModel> call, Response<PerangkatRootModel> response) {
                if (response.isSuccessful()) {
//                    dataItem = response.body().getData();
                    Toast.makeText(context, "" + response.body().getData(), Toast.LENGTH_SHORT).show();
//                    for (int i = 0; i < dataItem.size(); i++) {
//                        telegramVersi.setText(BuildConfig.VERSION_NAME);
//                        hp.setText(dataItem.get(i).getBuildBrand().toUpperCase() + " " + dataItem.get(i).getDevice().toUpperCase() + "Android (" + dataItem.get(i).getOsVersion() + ")");
//                        ipCity.setText(dataItem.get(i).getIpAddress() + " - " + dataItem.get(i).getLocationCity());
//                    }

                }
            }

            @Override
            public void onFailure(Call<PerangkatRootModel> call, Throwable t) {
                Toast.makeText(context, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
