package com.pdamkotasmg.happywork.api.server

import com.pdamkotasmg.happywork.fitur.authentication.login.model.AkunRootModel
import com.pdamkotasmg.happywork.fitur.feeds.model.BeritaRootModel
import com.pdamkotasmg.happywork.fitur.perangkat.model.PerangkatRootModel
import com.pdamkotasmg.happywork.fitur.splash.model.androidVersion.AndroidVersionModel
import com.pdamkotasmg.happywork.fitur.splash.model.packageName.PackageNameRootModel
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
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
    fun login(
            @Field("npp") npp: String?,
            @Field("password") password: String?,
            @Field("hwid") hwid: String?,
            @Field("model") model: String?,
            @Field("product") product: String?,
            @Field("device") device: String?,
            @Field("build_brand") build_brand: String?,
            @Field("os_version") os_version: String?,
            @Field("sdk_version") sdk_version: String?,
            @Field("build_number") build_number: String?,
            @Field("build_incremental") build_incremental: String?,
            @Field("ip_address") ip_address: String?,
            @Field("connection_type") connection_type: String?,
            @Field("ssid") ssid: String?,
            @Field("location_city") location_city: String?,
            @Field("latitude") latitude: String?,
            @Field("longitude") longitude: String?,
            @Field("app_version") app_version: String?
    ): Call<AkunRootModel>

    @POST("auth/logout")
    fun logout(@Header("Authorization") auth: String?): Call<ResponseBody?>?
    @POST("auth/delete-all-session")
    fun deleteAllSession(@Header("Authorization") auth: String?): Call<ResponseBody?>?
    @GET("masterdata/forbid-app")
    fun getPackageName(): Call<PackageNameRootModel?>?
    @GET("masterdata/config/all")
    fun getAndroidVersion(): Call<AndroidVersionModel?>?
    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET("auth/active-session")
    fun getHistoryAktifDevice(@Header("Authorization") auth: String?): Call<PerangkatRootModel?>?

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
    fun getNews(): Call<BeritaRootModel?>? //
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