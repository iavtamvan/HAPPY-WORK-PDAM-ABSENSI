package com.pdamkotasmg.happywork.fitur.absensi;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.camerakit.CameraKitView;
import com.pdamkotasmg.happywork.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AbsensiActivity extends AppCompatActivity {

    private int currentTime;
    private String currentDate;
    private String nameUser;

    private CameraKitView cameraBack;
    private CameraKitView cameraFront;
    private Button btnJepret;
    private LinearLayout divCamera;
    private ImageView ivHeaderBackArrow;
    private TextView tvHeaderJudul;
    private ImageView ivHeaderInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absensi);
        getSupportActionBar().hide();
        initView();
        nameUser = "Nama_";
        tvHeaderJudul.setText("REKAM RUPAMU");

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        currentTime = calendar.get(Calendar.MILLISECOND);
        Date cDate = new Date();
        currentDate = new SimpleDateFormat("yyyy-MM-dd").format(cDate);

        cameraBack.setVisibility(View.GONE);
        cameraFront.startVideo();
        divCamera.setOnClickListener(v -> {
            cameraFront.captureImage((cameraKitView, bytes) -> {
                File makeFile = new File(Environment.getExternalStorageDirectory() + "/PDAM-ABSENSI");
                makeFile.mkdirs();
                File savedPhoto = new File(makeFile, nameUser + currentDate + "_" + currentTime + "_FF.xxkampretnotfailable");
                try {
                    FileOutputStream outputStream = new FileOutputStream(savedPhoto.getPath());
                    outputStream.write(bytes);
                    outputStream.close();
                    cameraFront.onStop();
                    cameraFront.stopVideo();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });
    }

    @Override
    protected void onStart() {
        cameraFront.onStart();
        super.onStart();
    }
//
//    @Override
//    protected void onPause() {
//        cameraFront.onPause();
//        super.onPause();
//    }
//
//    @Override
//    protected void onStop() {
//        cameraFront.onStop();
//        super.onStop();
//    }
//
    @Override
    protected void onResume() {
        cameraFront.onResume();
        super.onResume();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        cameraFront.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void initView() {
        cameraBack = findViewById(R.id.camera_back);
        cameraFront = findViewById(R.id.camera_front);
        divCamera = findViewById(R.id.div_camera);
        ivHeaderBackArrow = findViewById(R.id.iv_header_back_arrow);
        tvHeaderJudul = findViewById(R.id.tv_header_judul);
        ivHeaderInfo = findViewById(R.id.iv_header_info);
    }
}