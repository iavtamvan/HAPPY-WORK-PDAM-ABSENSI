package co.id.pdamkotasmg.service;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.pdamkotasmg.goodday.utils.Config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PingService extends Service {

    private boolean isRunning = false;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (!isRunning) {
            isRunning = true;
            new PingTask().execute();
        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private class PingTask extends AsyncTask<Void, String, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                // Ping Google setiap 5 detik
                while (isRunning) {
                    Process process = Runtime.getRuntime().exec("ping -c 1 google.com");
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        publishProgress(line); // Menampilkan output ping
                    }
                    reader.close();
                    Thread.sleep(1000); // Jeda 1 detik
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            sendOutputToActivity(values[0]); // Mengirim output ping ke aktivitas
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            stopSelf(); // Layanan berhenti ketika tugas selesai
        }
    }
    private void sendOutputToActivity(String output) {
        Intent intent = new Intent("ping_result");
        intent.putExtra(Config.BUNDLE_PEMBACA_METER_PING_INTERNET, output);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }
}
