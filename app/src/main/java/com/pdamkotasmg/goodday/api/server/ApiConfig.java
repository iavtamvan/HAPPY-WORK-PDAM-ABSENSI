package com.pdamkotasmg.goodday.api.server;

import android.content.Context;

import com.chuckerteam.chucker.api.ChuckerCollector;
import com.chuckerteam.chucker.api.ChuckerInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiConfig {

    public static ApiService getApiService(Context context) {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        ChuckerCollector chuckerCollector = new ChuckerCollector(context, false);
        ChuckerInterceptor chuckerInterceptor = new ChuckerInterceptor(context, chuckerCollector);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(chuckerInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://gateway.pdamkotasmg.co.id/api-gw-dev/")
//                .baseUrl("https://app.pdamkotasmg.co.id/api-gw-dev/")
//                .baseUrl("https://tirta.pdamkotasmg.co.id/api-gw-dev/")
//                .baseUrl("https://pdam-lb.herokuapp.com/api-gw-dev/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit.create(ApiService.class);
    }
}
