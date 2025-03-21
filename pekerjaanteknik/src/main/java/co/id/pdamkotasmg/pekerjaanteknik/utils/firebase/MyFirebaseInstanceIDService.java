package co.id.pdamkotasmg.pekerjaanteknik.utils.firebase;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.installations.FirebaseInstallations;

import java.util.concurrent.atomic.AtomicReference;

import co.id.pdamkotasmg.pekerjaanteknik.utils.Config;

public class MyFirebaseInstanceIDService extends IntentService {
    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();

    public MyFirebaseInstanceIDService() {
        super(TAG);
    }

    private void sendRegistrationToServer(final String token) {
        // sending gcm token to server
        Log.d("debug", "sendRegistrationToServer: " + token);
    }

    private void storeRegIdInPref(String token) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("regId", token);
        editor.apply();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        AtomicReference<String> refreshedToken = new AtomicReference<>("");

        FirebaseInstallations.getInstance().getToken(true)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        refreshedToken.set(String.valueOf(task.getResult()));
                        Log.d("FirebaseToken", "Token: " + refreshedToken.get());
                    }
                });

// Nilai refreshedToken bisa digunakan di tempat lain setelah task selesai



        // Saving reg id to shared preferences
        storeRegIdInPref(String.valueOf(refreshedToken));

        // sending reg id to your server
        sendRegistrationToServer(String.valueOf(refreshedToken));

        // Notify UI that registration has completed, so the progress indicator can be hidden.
        Intent registrationComplete = new Intent(Config.FIREBAE_REGISTRATION_COMPLETE);
        registrationComplete.putExtra("token", refreshedToken);
        LocalBroadcastManager.getInstance(this).sendBroadcast(registrationComplete);
    }
}
