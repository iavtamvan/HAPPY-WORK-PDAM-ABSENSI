package com.pdamkotasmg.happywork.fitur.kehadiran.view

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.pdamkotasmg.happywork.R
import com.pdamkotasmg.happywork.fitur.kehadiran.controller.KehadiranController
import de.hdodenhof.circleimageview.CircleImageView

class KehadiranActivity : AppCompatActivity() {
    private var kehadiranController: KehadiranController? = null
    private var ivHeaderBackArrow: ImageView? = null
    private var tvHeaderJudul: TextView? = null
    private var ivHeaderInfo: ImageView? = null
    private var ciListKehadiranProfileImage: CircleImageView? = null
    private var tvListKehadiranNama: TextView? = null
    private var tvDate: TextView? = null
    private var tvListKehadiranShift: TextView? = null
    private var rvKehadiran: RecyclerView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kehadiran)
        supportActionBar?.hide()
        initView()
        tvHeaderJudul?.setText("Rekam Kehadiran")
        kehadiranController = KehadiranController()
        kehadiranController?.getKehadiran(applicationContext, rvKehadiran!!)
    }

    private fun initView() {
        ivHeaderBackArrow = findViewById(R.id.iv_header_back_arrow)
        tvHeaderJudul = findViewById(R.id.tv_header_judul)
        ivHeaderInfo = findViewById(R.id.iv_header_info)
        ciListKehadiranProfileImage = findViewById(R.id.ci_list_kehadiran_profile_image)
        tvListKehadiranNama = findViewById(R.id.tv_list_kehadiran_nama)
        tvDate = findViewById(R.id.tv_date)
        tvListKehadiranShift = findViewById(R.id.tv_list_kehadiran_shift)
        rvKehadiran = findViewById(R.id.rv_kehadiran)
    }
}