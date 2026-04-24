package id.pdamkotasmg.edms.api;

import android.content.Context;

import com.chuckerteam.chucker.api.ChuckerCollector;
import com.chuckerteam.chucker.api.ChuckerInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiConfigEDMS {

    public static ApiServiceEDMS getApiService(Context context) {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        ChuckerCollector chuckerCollector = new ChuckerCollector(context, true);
        ChuckerInterceptor chuckerInterceptor = new ChuckerInterceptor(context, chuckerCollector);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(chuckerInterceptor)
                .addInterceptor(httpLoggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://gateway.pdamkotasmg.co.id/api-gw-dev/")
//                .baseUrl("https://app.pdamkotasmg.co.id/api-gw-dev/")
//                .baseUrl("https://tirta.pdamkotasmg.co.id/api-gw-dev/")
//                .baseUrl("https://pdam-lb.herokuapp.com/api-gw-dev/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit.create(ApiServiceEDMS.class);
    }
}
