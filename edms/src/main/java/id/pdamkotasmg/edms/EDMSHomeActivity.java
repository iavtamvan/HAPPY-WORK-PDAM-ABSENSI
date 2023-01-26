package id.pdamkotasmg.edms;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class EDMSHomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edmshome);
        getSupportActionBar().hide();


    }
}