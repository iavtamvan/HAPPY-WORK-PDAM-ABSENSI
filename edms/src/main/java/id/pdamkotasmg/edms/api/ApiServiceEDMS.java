package id.pdamkotasmg.edms.api;

import id.pdamkotasmg.edms.fitur.suratMasuk.model.SuratMasukRootModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface ApiServiceEDMS {

    // TODO Mulai EDMS

    @Headers({"Content-Type: application/json;charset=UTF-8"})
    @GET("edms/api/android-surat-masuk/list-all-data")
    Call<SuratMasukRootModel> getEDMSSuratMasuk(
            @Header("Authorization") String auth
    );

    // TODO Selesai EDMS


}