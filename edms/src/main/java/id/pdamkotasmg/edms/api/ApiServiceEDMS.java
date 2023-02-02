package id.pdamkotasmg.edms.api;

import id.pdamkotasmg.edms.fitur.suratMasuk.model.SuratMasukRootModel;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiServiceEDMS {

    // TODO Mulai EDMS

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("edms/api/android-surat-masuk/list-all-data")
    Call<SuratMasukRootModel> getSuratMasuk(
            @Header("Authorization") String auth
    );

    @FormUrlEncoded
    @POST("edms/api/android-surat-masuk/detail-data")
    Call<SuratMasukRootModel> postDetailSuratMasuk(
            @Header("Authorization") String auth,
            @Field("trx_surat") String trx_surat
    );

    // TODO Selesai EDMS


}