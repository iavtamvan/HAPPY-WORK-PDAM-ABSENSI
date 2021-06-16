package com.pdamkotasmg.happywork.fitur.profil.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.pdamkotasmg.happywork.api.server.ApiConfig;
import com.pdamkotasmg.happywork.api.server.ApiService;
import com.pdamkotasmg.happywork.utils.Config;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileController {
    public void logout(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        ApiService apiService = ApiConfig.getApiService();
        apiService.logout(sharedPreferences.getString(Config.SHARED_ACCESS_TOKEN,""))
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(context, "" + response.message().toUpperCase() + " KELUAR", Toast.LENGTH_SHORT).show();
                            Config.logout(context);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(context, "" + Config.ERROR_MSG, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
