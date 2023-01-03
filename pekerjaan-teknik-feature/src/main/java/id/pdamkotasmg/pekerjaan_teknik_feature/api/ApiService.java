package id.pdamkotasmg.pekerjaan_teknik_feature.api;

import java.util.ArrayList;

import id.pdamkotasmg.pekerjaan_teknik_feature.model.akun.login.LoginRootModel;
import id.pdamkotasmg.pekerjaan_teknik_feature.model.barang.BarangRootModel;
import id.pdamkotasmg.pekerjaan_teknik_feature.model.callCenter.diposisi.DisposisiRootModel;
import id.pdamkotasmg.pekerjaan_teknik_feature.model.callCenter.mastCCSatker.MastCCSatkerModel;
import id.pdamkotasmg.pekerjaan_teknik_feature.model.listAll.ListAllGabunganRootModel;
import id.pdamkotasmg.pekerjaan_teknik_feature.model.monitoring.MonitoringSPKRootModel;
import id.pdamkotasmg.pekerjaan_teknik_feature.model.pegawai.PegawaiRootModel;
import id.pdamkotasmg.pekerjaan_teknik_feature.model.post.responseReuired.ResponseReqRootModel;
import id.pdamkotasmg.pekerjaan_teknik_feature.model.progressMandor.ProgressMandorRootModel;
import id.pdamkotasmg.pekerjaan_teknik_feature.model.riwayatSpk.mandor.RiwayatSPKMandorRootModel;
import id.pdamkotasmg.pekerjaan_teknik_feature.model.riwayatSpk.verifikator.VerifikatorRootModel;
import id.pdamkotasmg.pekerjaan_teknik_feature.model.spk.SPKSebelumRootModel;
import id.pdamkotasmg.pekerjaan_teknik_feature.model.tagihan.BillingTagihanRootModel;
import id.pdamkotasmg.pekerjaan_teknik_feature.model.verifikasiSPK.statusVerifikasi.StatusVerifikasiRootModel;
import id.pdamkotasmg.pekerjaan_teknik_feature.model.warning.WarningRootModel;
import id.pdamkotasmg.pekerjaan_teknik_feature.model.zona.ZonaRootModelItem;
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
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @FormUrlEncoded
    @POST("api/auth/login")
    Call<LoginRootModel> login(@Field("npp") String npp, @Field("password") String password, @Field("hwid") String hwid);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("api/auth/me")
    Call<LoginRootModel> me(@Header("Authorization") String auth);

    @POST("api/auth/logout")
    Call<ResponseBody> logout(@Header("Authorization") String auth);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("pekerjaan-teknik/api/m/list-all")
    Call<ListAllGabunganRootModel> listAll(@Header("Authorization") String auth);

    @FormUrlEncoded
    @POST("pekerjaan-teknik/api/m/search-pegawai")
    Call<PegawaiRootModel> searchPegawai(@Header("Authorization") String auth, @Field("npp") String npp, @Field("name") String name);

    @FormUrlEncoded
    @POST("pekerjaan-teknik/api/m/search-no-spk")
    Call<SPKSebelumRootModel> searchNoSPK(@Header("Authorization") String auth, @Field("no_spk") String npp);

    @FormUrlEncoded
    @POST("app10-proxy/pdam-smg/api-pdam/api-andro/api-zona-cr.php")
    Call<ArrayList<ZonaRootModelItem>> searchZona(@Header("Authorization") String auth, @Field("zona") String zona);

    @Multipart
    @POST("pekerjaan-teknik/api/p/spk")
    Call<ResponseReqRootModel> postDatasSPK2(@Header("Authorization") String auth,
                                             @Part("tgl_pelaksana") RequestBody tgl_pelaksana,
                                             @Part("jam1") RequestBody jam1,
                                             @Part("jam2") RequestBody jam2,
                                             @Part("jenis_pipa") RequestBody jenis_pipa,
                                             @Part("diameter") RequestBody diameter,
                                             @Part("tekanan") RequestBody tekanan,
                                             @Part("uraian") RequestBody uraian,
                                             @Part("jml_tenaga") RequestBody jml_tenaga,
                                             @Part("jml_tenaga_ket") RequestBody jml_tenaga_ket,
                                             @Part("kasub") RequestBody kasub,
                                             @Part("pengawas") RequestBody pengawas,
                                             @Part("ip_entry") RequestBody ip_entry,
                                             @Part("pc_entry") RequestBody pc_entry,
//                                             @Part("tanggal") RequestBody tanggal,
                                             @Part("status") RequestBody status,
                                             @Part("no_spk_sbl") RequestBody no_spk_sbl,
                                             @Part("x") RequestBody x,
                                             @Part("y") RequestBody y,
                                             @Part("kd_penangan") RequestBody kd_penangan,
                                             @Part("tka") RequestBody tka,
                                             @Part("kd_perbaikan") RequestBody kd_perbaikan,
                                             @Part("kd_zona") RequestBody kd_zona,
                                             @Part("tipe_zona") RequestBody tipe_zona,
                                             @Part("ket_zona") RequestBody ket_zona,
                                             @Part("nm_lapor") RequestBody nm_lapor,
                                             @Part("alamat_lapor") RequestBody alamat_lapor,
                                             @Part("nolangg_lapor") RequestBody nolangg_lapor,
                                             @Part("asal_lapor") RequestBody asal_lapor,
                                             @Part("nama") RequestBody nama,
                                             @Part("alamat") RequestBody alamat,
                                             @Part("lokasi") RequestBody lokasi,
                                             @Part("list_barang") RequestBody listBarangItems,
                                             @Part("list_pekerjaan") RequestBody listPekerjaanItems,
                                             @Part MultipartBody.Part foto1FilePart,
                                             @Part MultipartBody.Part foto2FilePart,
                                             @Part MultipartBody.Part foto3FilePart,
                                             @Part MultipartBody.Part foto4FilePart,
                                             @Part("kd_pb") RequestBody kd_pb,
                                             @Part("no_pengaduan_cc") RequestBody no_pengaduan_cc,
                                             @Part("no_aduan") RequestBody no_aduan,
                                             @Part("penyelesaian") RequestBody penyelesaian,
//                                             @Part("petugas") RequestBody petugas,
                                             @Part("status_kode_cc") RequestBody status_kode_cc,
                                             @Part("stlembur") RequestBody stlembur,
                                             @Part("user_ver") RequestBody user_ver
                                             );

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("pengaduan/api/billing/tagihan/air/{nolangg}")
    Call<BillingTagihanRootModel> getBillingTagihan(@Path("nolangg") String nolangg, @Header("Authorization") String auth);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("pekerjaan-teknik/api/m/search-jenis-pipa")
    Call<BarangRootModel> listBarang(@Header("Authorization") String auth, @Query("nm_brg") String nm_brg);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("pekerjaan-teknik/api/m/riwayat-spk")
    Call<RiwayatSPKMandorRootModel> riwayatSPK(@Header("Authorization") String auth);


    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("pekerjaan-teknik/api/m/cc/aduan/search/disposisi")
    Call<DisposisiRootModel> getCallCenterAduanByDisposisi(@Header("Authorization") String auth,
                                                           @Query("kode_disposisi") String kode_disposisi);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("pekerjaan-teknik/api/m/cc/aduan/search/tenaga-npp")
    Call<DisposisiRootModel> getDataCallCenterAduanByTenagaNPP(@Header("Authorization") String auth);

    @FormUrlEncoded
    @POST("pekerjaan-teknik/api/u/cc/ambil-pekerjaan")
    Call<ResponseBody> ambilDanBatalPekerjaanCC(@Header("Authorization") String auth,
                                                      @Field("no_pengaduan_cc") String no_pengaduan_cc,
                                                      @Field("tenaga_name_npp") String tenaga_name_npp
    );

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("pekerjaan-teknik/api/m/cc/mast-satker")
    Call<MastCCSatkerModel> getMastCCSatker(@Header("Authorization") String auth);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("pekerjaan-teknik/api/m/riwayat-spk-kasub")
    Call<VerifikatorRootModel> riwayatSPKKasub(@Header("Authorization") String auth,
                                               @Query("user_ver") String user_ver);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("pekerjaan-teknik/api/m/list-verif-spk-kasub")
    Call<VerifikatorRootModel> listVerifSPKVerifikator(@Header("Authorization") String auth);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("pekerjaan-teknik/api/m/progress/mandor")
    Call<ProgressMandorRootModel> progressMandor(@Header("Authorization") String auth);


    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("pekerjaan-teknik/api/m/progress/monitoring")
    Call<ProgressMandorRootModel> progressMonitoring(@Header("Authorization") String auth);

//    @FormUrlEncoded
//    @POST("pekerjaan-teknik/api/u/verif-spk")
//    Call<StatusVerifikasiRootModel> updateStatusVerifikasi(@Header("Authorization") String auth, @Field("no_spk") String no_spk);

    @FormUrlEncoded
    @POST("gis-api-lara/api/pekerjaan-teknik/spk/verifikasi-koordinat")
    Call<StatusVerifikasiRootModel> updateStatusVerifikasi(@Header("Authorization") String auth, @Field("no_spk") String no_spk);

    @FormUrlEncoded
    @POST("pekerjaan-teknik/api/d/delete-spk")
    Call<ResponseBody> deleteSPK(@Header("Authorization") String auth, @Field("no_spk") String no_spk);

    @FormUrlEncoded
    @POST("pekerjaan-teknik/api/u/edit-spk")
    Call<ResponseBody> updateNoCC(@Header("Authorization") String auth,
                                  @Field("no_spk") String no_spk,
                                  @Field("nocc") String nocc,
                                  @Field("no_aduan") String no_aduan,
                                  @Field("petugas") String petugas
    );

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("pengaduan/api/webhook/sync-pengaduan-cc")
    Call<ResponseBody> syncPengaduan(@Query("date") String date,
                                     @Query("force") String force);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("pekerjaan-teknik/api/auth/warning")
    Call<WarningRootModel> warning();

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("pekerjaan-teknik/api/m/progress/monitoring")
    Call<MonitoringSPKRootModel> getMonitoring(@Header("Authorization") String auth);

}