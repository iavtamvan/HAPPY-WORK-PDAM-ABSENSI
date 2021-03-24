package com.pdamkotasmg.happywork.api.server;

import com.pdamkotasmg.happywork.fitur.authentication.login.model.AkunRootModel;
import com.pdamkotasmg.happywork.fitur.feeds.model.BeritaRootModel;
import com.pdamkotasmg.happywork.fitur.perangkat.model.PerangkatRootModel;
import com.pdamkotasmg.happywork.fitur.splash.model.PackageNameRootModel;
import com.pdamkotasmg.happywork.fitur.splash.model.androidVersion.AndroidVersionModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {
//    // google
//    @GET("siteverify")
//    Call<ResponseBody> getCaptcha(@Query("secret") String secretKey, @Query("response") String responseToken);
//
//    // akun
//    @FormUrlEncoded
//    @POST("auth-pelanggan/send-otp")
//    Call<AkunRootModel> sendOTP(
//            @Field("phone") String phone
//    );
//
    @FormUrlEncoded
    @POST("auth/login")
    Call<AkunRootModel> login(
            @Field("npp") String npp,
            @Field("password") String password,
            @Field("hwid") String hwid,
            @Field("model") String model,
            @Field("product") String product,
            @Field("device") String device,
            @Field("build_brand") String build_brand,
            @Field("os_version") String os_version,
            @Field("sdk_version") String sdk_version,
            @Field("build_number") String build_number,
            @Field("build_incremental") String build_incremental,
            @Field("ip_address") String ip_address,
            @Field("connection_type") String connection_type,
            @Field("ssid") String ssid,
            @Field("location_city") String location_city,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude
    );

    @GET("masterdata/forbid-app")
    Call<PackageNameRootModel> getPackageName();
    @GET("masterdata/config/by-key/android-version-latest")
    Call<AndroidVersionModel> getAndroidVersion();

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("auth/current-session")
    Call<PerangkatRootModel> getAktifDevice(@Header("Authorization") String auth);

//
//    @POST("auth-pelanggan/register")
//    Call<RegisterModel> register(
//            @Body RegisterModel registerModel
//    );
//
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
//    @GET("billing/tagihan/air/{nolangg}")
//    Call<BillingTagihanRootModel> getBillingTagihan(@Path("nolangg") String nolangg, @Header("Authorization") String auth);
//
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
//    @GET("auth-pelanggan/refresh-token")
//    Call<RefreshTokenRoot> refreshToken(@Header("Authorization") String auth);
//
    @GET("portal-web/berita")
    Call<BeritaRootModel> getNews();
//
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
//    @GET("pengaduan/mobile/aduanku")
//    Call<PengaduanRootModel> getPengaduan(@Header("Authorization") String auth);
//
//    @Multipart
//    @POST("pengaduan/mobile")
//    Call<PengaduanRootModel> sendPengaduanFix(
//            @Header("Authorization") String auth,
//            @Part("nama_pengadu") RequestBody nama_pengadu,
//            @Part("uraian") RequestBody uraian,
//            @Part("telp_pengadu") RequestBody telp_pengadu,
//            @Part("alamat_pengadu") RequestBody alamat_pengadu,
//            @Part("latitude") RequestBody latitude,
//            @Part("longitude") RequestBody longitude,
//            @Part MultipartBody.Part filePartAduan,
//            @Part MultipartBody.Part filePartPengadu,
//            @Part("kategori_aduan") RequestBody kategori
//    );
//
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
//    @GET("pengaduan/mobile/daftar-kategori")
//    Call<KategoriRootModel> getKategori(@Header("Authorization") String auth);

}