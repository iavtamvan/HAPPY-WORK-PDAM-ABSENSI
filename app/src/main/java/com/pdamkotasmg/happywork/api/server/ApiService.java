package com.pdamkotasmg.happywork.api.server;


import com.pdamkotasmg.myapplication.fitur.authentication.akun.model.AkunRootModel;
import com.pdamkotasmg.myapplication.fitur.authentication.akun.model.RegisterModel;
import com.pdamkotasmg.myapplication.fitur.berita.model.BeritaRootModel;
import com.pdamkotasmg.myapplication.fitur.billingTagihan.model.BillingTagihanRootModel;
import com.pdamkotasmg.myapplication.fitur.dashboard.model.RefreshTokenRoot;
import com.pdamkotasmg.myapplication.fitur.pengaduan.model.PengaduanRootModel;
import com.pdamkotasmg.myapplication.fitur.pengaduan.model.kategori.KategoriRootModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    // google
    @GET("siteverify")
    Call<ResponseBody> getCaptcha(@Query("secret") String secretKey, @Query("response") String responseToken);

    // akun
    @FormUrlEncoded
    @POST("auth-pelanggan/send-otp")
    Call<AkunRootModel> sendOTP(
            @Field("phone") String phone
    );

    @FormUrlEncoded
    @POST("auth-pelanggan/login")
    Call<AkunRootModel> login(
            @Field("phone") String noHp,
            @Field("otp") String otp
    );

    @POST("auth-pelanggan/register")
    Call<RegisterModel> register(
            @Body RegisterModel registerModel
    );

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("billing/tagihan/air/{nolangg}")
    Call<BillingTagihanRootModel> getBillingTagihan(@Path("nolangg") String nolangg, @Header("Authorization") String auth);

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("auth-pelanggan/refresh-token")
    Call<RefreshTokenRoot> refreshToken(@Header("Authorization") String auth);

    @GET("portal-web/berita")
    Call<BeritaRootModel> getNews();

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("pengaduan/mobile/aduanku")
    Call<PengaduanRootModel> getPengaduan(@Header("Authorization") String auth);

    @Multipart
    @POST("pengaduan/mobile")
    Call<PengaduanRootModel> sendPengaduanFix(
            @Header("Authorization") String auth,
            @Part("nama_pengadu") RequestBody nama_pengadu,
            @Part("uraian") RequestBody uraian,
            @Part("telp_pengadu") RequestBody telp_pengadu,
            @Part("alamat_pengadu") RequestBody alamat_pengadu,
            @Part("latitude") RequestBody latitude,
            @Part("longitude") RequestBody longitude,
            @Part MultipartBody.Part filePartAduan,
            @Part MultipartBody.Part filePartPengadu,
            @Part("kategori_aduan") RequestBody kategori
    );

    @Headers({ "Content-Type: application/json;charset=UTF-8"})
    @GET("pengaduan/mobile/daftar-kategori")
    Call<KategoriRootModel> getKategori(@Header("Authorization") String auth);

}