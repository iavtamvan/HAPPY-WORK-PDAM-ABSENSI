package com.pdamkotasmg.happywork.fitur.authentication

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.pdamkotasmg.happywork.R
import com.pdamkotasmg.happywork.fitur.authentication.login.LoginActivity
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

class WelcomeAuthActivity : AppCompatActivity() {
    private var btnPageLogin: Button? = null
    private var tvPageRegister: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_auth)
        supportActionBar?.hide()
        initView()
        methodRequiresTwoPermission()
        tvPageRegister?.setVisibility(View.GONE)
        btnPageLogin?.setOnClickListener { v: View? -> startActivity(Intent(applicationContext, LoginActivity::class.java)) }
    }

    private fun initView() {
        btnPageLogin = findViewById(R.id.btn_page_login)
        tvPageRegister = findViewById(R.id.tv_page_register)
    }

    @AfterPermissionGranted(RC_CAMERA_AND_LOCATION)
    private fun methodRequiresTwoPermission() {
        var perms = arrayOfNulls<String?>(0)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            perms = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE, Manifest.permission.USE_FINGERPRINT)
        }
        if (EasyPermissions.hasPermissions(this, *perms)) {
            // Already have permission, do the thing
            // ...
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.app_name),
                    RC_CAMERA_AND_LOCATION, *perms)
        }
    }

    companion object {
        private const val RC_CAMERA_AND_LOCATION = 1
    }
}