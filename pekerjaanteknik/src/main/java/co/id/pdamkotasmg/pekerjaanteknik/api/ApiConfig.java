package co.id.pdamkotasmg.pekerjaanteknik.api;

import android.content.Context;

import com.chuckerteam.chucker.api.ChuckerCollector;
import com.chuckerteam.chucker.api.ChuckerInterceptor;
import com.pdamkotasmg.goodday.BuildConfig;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiConfig {

    // Satu OkHttpClient dipakai ulang untuk seluruh modul. Sebelumnya client
    // dibuat baru tiap request, sehingga tiap upload foto membuka koneksi +
    // TLS handshake baru dan tidak pernah memakai keep-alive.
    private static volatile OkHttpClient okHttpClient;

    // ApiService di-cache per baseUrl (portal-pegawai dan gateway umum).
    private static final Map<String, ApiService> API_SERVICES = new ConcurrentHashMap<>();

    public static ApiService getApiService(Context context, String baseUrl) {
        ApiService cached = API_SERVICES.get(baseUrl);
        if (cached != null) {
            return cached;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient(context))
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        ApiService existing = API_SERVICES.putIfAbsent(baseUrl, apiService);
        return existing != null ? existing : apiService;
    }

    private static OkHttpClient getClient(Context context) {
        OkHttpClient client = okHttpClient;
        if (client == null) {
            synchronized (ApiConfig.class) {
                client = okHttpClient;
                if (client == null) {
                    // applicationContext supaya interceptor tidak menahan Activity.
                    client = buildClient(context.getApplicationContext());
                    okHttpClient = client;
                }
            }
        }
        return client;
    }

    private static OkHttpClient buildClient(Context appContext) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                // Timeout lama 300 detik: kalau server/jaringan menggantung,
                // dialog "Mengirim data..." ikut menggantung 5 menit.
                // 30 detik connect masih aman untuk petugas di area sinyal 2G/3G.
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true);

        // Chucker dan log level BODY menyalin seluruh isi request/response,
        // termasuk byte foto, jadi hanya dipasang pada build debug.
        if (BuildConfig.DEBUG) {
            ChuckerCollector chuckerCollector = new ChuckerCollector(appContext, true);
            builder.addInterceptor(new ChuckerInterceptor(appContext, chuckerCollector));

            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(httpLoggingInterceptor);
        }

        return builder.build();
    }
}
