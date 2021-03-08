package com.pdamkotasmg.happywork.fitur.absensi;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.camerakit.CameraKitView;
import com.pdamkotasmg.happywork.R;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AbsensiActivity extends AppCompatActivity {

    private CameraKitView camera;
    private Button btnJepret;

    private int currentTime;
    private String  currentDate;
    private String  nameUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absensi);
        initView();
        nameUser = "Nama_";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        currentTime = calendar.get(Calendar.MILLISECOND);
        Date cDate = new Date();
        currentDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);
        camera.startVideo();
        btnJepret.setOnClickListener(v -> {
            camera.captureImage(new CameraKitView.ImageCallback() {
                @Override
                public void onImage(CameraKitView cameraKitView, byte[] bytes) {
                    File makeFile = new File(Environment.getExternalStorageDirectory() + "/PDAM-ABSENSI");
                    makeFile.mkdirs();
                    File savedPhoto = new File(makeFile, nameUser + currentDate + "_" + currentTime + "_FF.xxkampretnotfailable");
                    try {
                        FileOutputStream outputStream = new FileOutputStream(savedPhoto.getPath());
                        Log.d("debug", "onImage: " + savedPhoto);
                        Log.d("debug", "outStram: " + outputStream);
                        outputStream.write(bytes);
                        outputStream.close();
                    } catch (java.io.IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        camera.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        camera.onResume();
    }

    @Override
    protected void onPause() {
        camera.onPause();
        super.onPause();
    }

    @Override
    protected void onStop() {
        camera.onStop();
        super.onStop();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        camera.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void initView() {
        camera = findViewById(R.id.camera);
        btnJepret = findViewById(R.id.btn_jepret);
    }
}