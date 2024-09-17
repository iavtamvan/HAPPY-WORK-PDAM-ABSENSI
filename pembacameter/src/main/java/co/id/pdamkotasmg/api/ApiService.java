package co.id.pdamkotasmg.api;

import co.id.pdamkotasmg.model.bendel.BendelRootModel;
import co.id.pdamkotasmg.model.bendel.bendelNext.BendelNextModel;
import co.id.pdamkotasmg.model.bendel.tandaiPlg.TandaiBendelRootModel;
import co.id.pdamkotasmg.model.cariData.CariDataRootModel;
import co.id.pdamkotasmg.model.checkByNolangg.CheckByNolanggRootModel;
import co.id.pdamkotasmg.model.checkKoneksi.CheckKoneksiServerRootModel;
import co.id.pdamkotasmg.model.checkPelangganSudahDibaca.CheckPelangganRootModel;
import co.id.pdamkotasmg.model.fileHandler.PostFotoUploadRootModel;
import co.id.pdamkotasmg.model.home.HomeRootModel;
import co.id.pdamkotasmg.model.listGabungan.ListGabunganRootModel;
import co.id.pdamkotasmg.model.riwayatBacaMeter.RiwayatBacaMeterRootModel;
import co.id.pdamkotasmg.model.updatePembacaMeter.UpdatePembacaMeterRootModel;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("pembaca-meter/api/m/plg-nolangg")
    Call<CheckPelangganRootModel> getPelanggan(
            @Header("Authorization") String auth,
            @Query("nolangg") String nolangg
    );

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("pembaca-meter/api/m/data-plg")
    Call<CheckPelangganRootModel> getCheckPelangganDetail(
            @Header("Authorization") String auth,
            @Query("nolangg") String nolangg
    );

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("pembaca-meter/api/m/mplg")
    Call<CheckByNolanggRootModel> getCheckPelangganStatus(
            @Header("Authorization") String auth,
            @Query("nolangg") String nolangg
    );

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("pembaca-meter/api/m/user/riwayat")
    Call<RiwayatBacaMeterRootModel> getRiwayatBacaMeter(
            @Header("Authorization") String auth,
            @Query("bendel") String bendel,
            @Query("nolangg") String nolangg
    );

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("pembaca-meter/api/m/cari-plg")
    Call<CariDataRootModel> getCariData(
            @Header("Authorization") String auth,
            @Query("nama") String nama,
            @Query("alamat") String alamat,
            @Query("nometer") String nometer
    );

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("pembaca-meter/api/m/ver/ditolak")
    Call<RiwayatBacaMeterRootModel> getVerifikasiDitolak(
            @Header("Authorization") String auth);

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("pembaca-meter/api/m/gabungan")
    Call<ListGabunganRootModel> getListGabungan(
            @Header("Authorization") String auth
    );

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("pembaca-meter/api/m/count-periode")
    Call<HomeRootModel> getCountPeriode(
            @Header("Authorization") String auth
    );

    @GET("pembaca-meter/api/n/test")
    Call<CheckKoneksiServerRootModel> getTestPing();

    @FormUrlEncoded
    @POST("pembaca-meter/api/p/bendel")
    Call<BendelRootModel> getBendel(
            @Header("Authorization") String auth,
            @Field("bendel") String bendel,
            @Field("nolangg") String nolangg
    );

    @FormUrlEncoded
    @POST("pembaca-meter/api/p/bendel-next")
    Call<BendelNextModel> getBendelNext(
            @Header("Authorization") String auth,
            @Field("bendel") String bendel
    );

    @Multipart
    @POST("file-handler/api/upload/foto")
    Call<PostFotoUploadRootModel> postUploadFoto(
            @Header("Authorization") String auth,
            @Part("path") RequestBody path,
            @Part("filename") RequestBody filename,
            @Part MultipartBody.Part photo
    );

    @FormUrlEncoded
    @POST("pembaca-meter/api/u/update-baca-meter")
    Call<UpdatePembacaMeterRootModel> postUpdatePembacaMeter(
            @Header("Authorization") String auth,
            @Field("nolangg") String nolangg,
            @Field("kini") String kini,
            @Field("foto_meter") String foto_meter,
            @Field("ip_entry") String ip_entry,
            @Field("st") String stMeter,
            @Field("keterangan") String keterangan,
            @Field("action_code") String action_code,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude,
            @Field("alamat") String alamat,
            @Field("manometer") String manometer,
            @Field("manometer_foto") String manometer_foto,
            @Field("other") String other
    );

    @FormUrlEncoded
    @POST("pembaca-meter/api/u/update-tanda-plg")
    Call<TandaiBendelRootModel> postUpdateTandaPlg(
            @Header("Authorization") String auth,
            @Field("nolangg") String nolangg,
            @Field("bendel") String bendel,
            @Field("tandai") String tandai
    );
}