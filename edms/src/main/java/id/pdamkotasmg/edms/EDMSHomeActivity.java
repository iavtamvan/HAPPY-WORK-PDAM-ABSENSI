package id.pdamkotasmg.edms;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Random;

public class EDMSHomeActivity extends AppCompatActivity {

    private ImageView ivMore;
    private TextView tvJudulSurat;
    private RecyclerView rvEdms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edmshome);
        getSupportActionBar().hide();
        initView();

    }

    public int getRandomColor(){
        Random rnd = new Random();
        return Color.argb(255, rnd.nextInt(115), rnd.nextInt(198), rnd.nextInt(202));
    }

    private void initView() {
        ivMore = findViewById(R.id.iv_more);
        tvJudulSurat = findViewById(R.id.tv_judul_surat);
        rvEdms = findViewById(R.id.rv_edms);
    }
}