package co.id.pdamkotasmg.api;

import co.id.pdamkotasmg.model.pelanggan.PelangganByNolanggRootModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ApiService {

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("pembaca-meter/api/m/plg-nolangg")
    Call<PelangganByNolanggRootModel> getPelanggan(
            @Header("Authorization") String auth,
            @Query("nolangg") String nolangg
    );


}