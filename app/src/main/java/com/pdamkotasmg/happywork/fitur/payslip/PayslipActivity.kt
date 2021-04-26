package com.pdamkotasmg.happywork.fitur.payslip

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pdamkotasmg.happywork.R

class PayslipActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payslip)
        supportActionBar?.hide()
    }
}