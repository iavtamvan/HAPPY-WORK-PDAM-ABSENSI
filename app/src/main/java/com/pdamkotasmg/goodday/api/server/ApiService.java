package com.pdamkotasmg.goodday.api.server;

import com.pdamkotasmg.goodday.fitur.authentication.login.model.AkunRootModel;
import com.pdamkotasmg.goodday.fitur.dashboard.model.ShfitPegawaiRootModel;
import com.pdamkotasmg.goodday.fitur.dashboard.model.permissionName.PermissionRootModel;
import com.pdamkotasmg.goodday.fitur.feeds.model.BeritaRootModel;
import com.pdamkotasmg.goodday.fitur.kehadiran.cuti.model.detailCuti.DetailCutiRootModel;
import com.pdamkotasmg.goodday.fitur.kehadiran.cuti.model.riwayatCuti.RiwayatCutiRootModel;
import com.pdamkotasmg.goodday.fitur.kehadiran.cuti.model.tipeCuti.TipeCutiRootModel;
import com.pdamkotasmg.goodday.fitur.kehadiran.home.model.RiwayatKehadiranRootModel;
import com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran.model.detailKoreksiKehadiran.DetailKoreksiKehadiranRootModel;
import com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran.model.myStaff.GetMyStaffRootModel;
import com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran.model.postKoreksiKehadiran.KoreksiKeharidanRootModel;
import com.pdamkotasmg.goodday.fitur.kehadiran.koreksiKehadiran.model.riwayatKoreksiKehadiran.RiwayatKoreksiKehadiranRootModel;
import com.pdamkotasmg.goodday.fitur.kehadiran.perjalananDinas.model.postPerjalanDinas.PostPerjalananDinasRootModel;
import com.pdamkotasmg.goodday.fitur.perangkat.model.PerangkatRootModel;
import com.pdamkotasmg.goodday.fitur.permintaan.model.PermintaanRootModel;
import com.pdamkotasmg.goodday.fitur.presensi.model.checkLocationModel.CheckLocationRootModel;
import com.pdamkotasmg.goodday.fitur.presensi.model.faceDeetectionModel.FaceDetectionRootModel;
import com.pdamkotasmg.goodday.fitur.presensi.model.savePresensiModel.SavePresensiRootModel;
import com.pdamkotasmg.goodday.fitur.splash.model.androidVersion.AndroidVersionModel;
import com.pdamkotasmg.goodday.fitur.splash.model.packageName.PackageNameRootModel;

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
    @POST("portal-pegawai/api/auth/login")
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
            @Field("app_version") String app_version,
            @Field("fcm_token") String fcm_token
    );

    @GET("portal-pegawai/api/auth/permission-names")
    Call<PermissionRootModel> getPermissionNames(@Header("Authorization") String auth);

    @POST("portal-pegawai/api/auth/logout")
    Call<ResponseBody> logout(@Header("Authorization") String auth);

    @FormUrlEncoded
    @POST("portal-pegawai/api/auth/update-profile")
    Call<ResponseBody> updatePassword(
            @Header("Authorization") String auth,
            @Field("password") String password,
            @Field("password_confirmation") String password_confirmation);

    @POST("portal-pegawai/api/auth/delete-all-session")
    Call<ResponseBody> deleteAllSession(@Header("Authorization") String auth);

    @FormUrlEncoded
    @POST("portal-pegawai/api/attendance/check-location")
    Call<CheckLocationRootModel> checkLocation(@Header("Authorization") String auth,
                                               @Field("type") String statusPresensi,
                                               @Field("npp") String npp,
                                               @Field("latitude") double latitude,
                                               @Field("longitude") double longitude);

    @FormUrlEncoded
    @POST("portal-pegawai/api/attendance/record")
    Call<SavePresensiRootModel> savePresensi(@Header("Authorization") String auth,
                                             @Field("latitude") double latitude,
                                             @Field("longitude") double longitude,
                                             @Field("type") String statusPresensi,
                                             @Field("npp") String npp,
                                             @Field("check_face") String check_face,
                                             @Field("photo_path") String photo_path,
                                             @Field("connection_type") String connection_type,
                                             @Field("record_date") String record_date,
                                             @Field("record_time") String record_time
    );

    @GET("portal-pegawai/api/masterdata/forbid-app")
    Call<PackageNameRootModel> getPackageName();

    @GET("portal-pegawai/api/masterdata/config/all")
    Call<AndroidVersionModel> getAndroidVersion();

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("portal-pegawai/api/auth/active-session")
    Call<PerangkatRootModel> getHistoryAktifDevice(@Header("Authorization") String auth);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("portal-pegawai/api/attendance/applies-shift")
    Call<ShfitPegawaiRootModel> getShiftPegawai(@Header("Authorization") String auth);


    //
//    @POST("auth-pelanggan/register")
//    Call<RegisterModel> register(
//            @Body RegisterModel registerModel
//    );
//
    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("portal-pegawai/api/attendance/history/")
    Call<RiwayatKehadiranRootModel> getHistoryPresensi(
            @Header("Authorization") String auth,
            @Query("date_from") String dateFrom,
            @Query("date_to") String dateTo,
            @Query("formatted") String formatted
    );

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("portal-pegawai/api/employee-request/my-request-history")
    Call<RiwayatKoreksiKehadiranRootModel> getHistoryKoreksiKehadiran(
            @Header("Authorization") String auth,
            @Query("request_type_code") String request_type_code,
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
    @POST("portal-pegawai/api/attendance/face-recognize")
    Call<FaceDetectionRootModel> checkFace(
            @Header("Authorization") String auth,
            @Part("type") RequestBody statusPresensi,
            @Part("npp") RequestBody npp,
            @Part MultipartBody.Part filePartPhoto
    );

    @Multipart
    @POST("portal-pegawai/api/employee-request/send-request")
    Call<FaceDetectionRootModel> postRequestKoreksiKehadiran(
            @Header("Authorization") String auth,
            @Part("request_type_code") RequestBody request_type_code,
            @Part("start_date") RequestBody start_date,
            @Part("end_date") RequestBody end_date,
            @Part("requeste`d_for_npp") RequestBody requested_for_npp,
            @Part("details") RequestBody details
    );

    @POST("portal-pegawai/api/employee-request/send-request")
    Call<KoreksiKeharidanRootModel> postJson(
            @Header("Authorization") String auth,
            @Body KoreksiKeharidanRootModel body);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("portal-pegawai/api/auth/my-staff")
    Call<GetMyStaffRootModel> getMyStaff(
            @Header("Authorization") String auth
    );

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("portal-pegawai/api/employee-request/my-request-history")
    Call<PermintaanRootModel> getRequestHistoryAll(
            @Header("Authorization") String auth,
            @Query("request_type_code") String request_type_code,
            @Query("formatted") String formatted
    );

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("portal-pegawai/api/employee-request/by-employee-request-number")
    Call<DetailKoreksiKehadiranRootModel> getRequestByNumber(
            @Header("Authorization") String auth,
            @Query("request_type_code") String request_type_code,
            @Query("request_number") String request_number,
            @Query("formatted") String formatted
    );

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("portal-pegawai/api/employee-request/by-employee-request-number")
    Call<DetailCutiRootModel> getRequestHistoryCutiByNumber(
            @Header("Authorization") String auth,
            @Query("request_type_code") String request_type_code,
            @Query("request_number") String request_number,
            @Query("formatted") String formatted
    );

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("portal-pegawai/api/employee-request/my-request-history")
    Call<RiwayatCutiRootModel> getRequestHistoryCuti(
            @Header("Authorization") String auth,
            @Query("request_type_code") String request_type_code,
            @Query("formatted") String formatted
    );

    @POST("portal-pegawai/api/employee-request/edit-request")
    Call<ResponseBody> getRequestEdit(
            @Header("Authorization") String auth,
            @Query("request_type_code") String request_type_code,
            @Query("request_number") String request_number,
            @Query("request_status_code") String request_status_code
    );

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("portal-pegawai/api/masterdata/request-leave-type/all")
    Call<TipeCutiRootModel> getTipeCuti(
            @Header("Authorization") String auth,
            @Query("formatted") String formatted
    );

    @FormUrlEncoded
    @POST("portal-pegawai/api/employee-request/send-request")
    Call<ResponseBody> postCuti(
            @Header("Authorization") String auth,
            @Field("request_type_code") String request_type_code,
            @Field("requested_for_npp") String requested_for_npp,
            @Field("request_leave_type_id") String request_leave_type_id,
            @Field("start_date") String start_date,
            @Field("end_date") String end_date,
            @Field("remark") String remark
    );

    @POST("portal-pegawai/api/employee-request/send-request")
    Call<PostPerjalananDinasRootModel> postJsonPerjalananDinas(
            @Header("Authorization") String auth,
            @Body PostPerjalananDinasRootModel body);

}