package co.id.pdamkotasmg.pekerjaanteknik.model.post.responseReuired;

import com.google.gson.annotations.SerializedName;

public class Data{

    // Sebelumnya bertipe java.lang.Error (kelas Throwable bawaan Java). Gson menolak
    // memetakan JSON ke kelas java.*, sehingga pesan validasi dari server saat simpan
    // SPK gagal selalu berubah jadi error parsing. Kelas Error di paket ini yang benar.
    @SerializedName("error")
    private Error error;

    public void setError(Error error){
        this.error = error;
    }

    public Error getError(){
        return error;
    }
}