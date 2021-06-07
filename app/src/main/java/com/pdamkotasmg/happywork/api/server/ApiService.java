package com.pdamkotasmg.happywork.api.server;

import com.pdamkotasmg.happywork.fitur.presensi.model.checkLocationModel.CheckLocationRootModel;
import com.pdamkotasmg.happywork.fitur.presensi.model.faceDeetectionModel.FaceDetectionRootModel;
import com.pdamkotasmg.happywork.fitur.presensi.model.savePresensiModel.SavePresensiRootModel;
import com.pdamkotasmg.happywork.fitur.authentication.login.model.AkunRootModel;
import com.pdamkotasmg.happywork.fitur.dashboard.model.ShfitPegawaiRootModel;
import com.pdamkotasmg.happywork.fitur.feeds.model.BeritaRootModel;
import com.pdamkotasmg.happywork.fitur.kehadiran.model.RiwayatKehadiranRootModel;
import com.pdamkotasmg.happywork.fitur.perangkat.model.PerangkatRootModel;
import com.pdamkotasmg.happywork.fitur.splash.model.androidVersion.AndroidVersionModel;
import com.pdamkotasmg.happywork.fitur.splash.model.packageName.PackageNameRootModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

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
            @Field("longitude") String longitude,
            @Field("app_version") String app_version
    );

    @POST("auth/logout")
    Call<ResponseBody> logout(@Header("Authorization") String auth);

    @POST("auth/delete-all-session")
    Call<ResponseBody> deleteAllSession(@Header("Authorization") String auth);

    @FormUrlEncoded
    @POST("attendance/check-location")
    Call<CheckLocationRootModel> checkLocation(@Header("Authorization") String auth,
                                               @Field("type") String statusPresensi,
                                               @Field("npp") String npp,
                                               @Field("latitude") double latitude,
                                               @Field("longitude") double longitude);

    @FormUrlEncoded
    @POST("attendance/record")
    Call<SavePresensiRootModel> savePresensi(@Header("Authorization") String auth,
                                           @Field("latitude") double latitude,
                                           @Field("longitude") double longitude,
                                           @Field("type") String statusPresensi,
                                           @Field("npp") String npp,
                                           @Field("check_face") String check_face,
                                           @Field("photo_path") String photo_path,
                                           @Field("connection_type") String connection_type
                                           );

    @GET("masterdata/forbid-app")
    Call<PackageNameRootModel> getPackageName();

    @GET("masterdata/config/all")
    Call<AndroidVersionModel> getAndroidVersion();

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("auth/active-session")
    Call<PerangkatRootModel> getHistoryAktifDevice(@Header("Authorization") String auth);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("attendance/applies-shift")
    Call<ShfitPegawaiRootModel> getShiftPegawai(@Header("Authorization") String auth);


    //
//    @POST("auth-pelanggan/register")
//    Call<RegisterModel> register(
//            @Body RegisterModel registerModel
//    );
//
    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("attendance/history/")
    Call<RiwayatKehadiranRootModel> getHistoryPresensi(
            @Header("Authorization") String auth,
            @Query("date_from") String dateFrom,
            @Query("date_to") String dateTo,
            @Query("formatted") String formatted
            );
//
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
//    @GET("auth-pelanggan/refresh-token")
//    Call<RefreshTokenRoot> refreshToken(@Header("Authorization") String auth);
//
    @GET("kontol")
    Call<BeritaRootModel> getNews();

    //
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
//    @GET("pengaduan/mobile/aduanku")
//    Call<PengaduanRootModel> getPengaduan(@Header("Authorization") String auth);
//
    @Multipart
    @POST("attendance/face-recognize")
    Call<FaceDetectionRootModel> checkFace(
            @Header("Authorization") String auth,
            @Part("type") RequestBody statusPresensi,
            @Part("npp") RequestBody npp,
            @Part MultipartBody.Part filePartPhoto
    );
//
//    @Headers({ "Content-Type: application/json;charset=UTF-8"})
//    @GET("pengaduan/mobile/daftar-kategori")
//    Call<KategoriRootModel> getKategori(@Header("Authorization") String auth);

}