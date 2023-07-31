package co.id.pdamkotasmg.api;

import co.id.pdamkotasmg.model.bendel.BendelRootModel;
import co.id.pdamkotasmg.model.checkPelanggan.CheckPelangganRootModel;
import co.id.pdamkotasmg.model.listGabungan.ListGabunganRootModel;
import co.id.pdamkotasmg.model.pelanggan.PelangganByNolanggRootModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("pembaca-meter/api/m/plg-nolangg")
    Call<PelangganByNolanggRootModel> getPelanggan(
            @Header("Authorization") String auth,
            @Query("nolangg") String nolangg
    );

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("pembaca-meter/api/m/data-plg")
    Call<CheckPelangganRootModel> getCheckPelanggan(
            @Header("Authorization") String auth,
            @Query("nolangg") String nolangg
    );

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("pembaca-meter/api/m/gabungan")
    Call<ListGabunganRootModel> getListGabungan(
            @Header("Authorization") String auth
    );

    @FormUrlEncoded
    @POST("pembaca-meter/api/p/bendel")
    Call<BendelRootModel> getBendel(
            @Header("Authorization") String auth,
            @Field("bendel") String bendel,
            @Field("nolangg") String nolangg
    );


}