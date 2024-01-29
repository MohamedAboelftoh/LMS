package com.example.lms.ui.splashes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lms.R
import com.example.lms.databinding.ActivitySplash2Binding
import com.example.lms.ui.login.LoginActivity

class SplashActivity2 : AppCompatActivity() {
    private lateinit var viewBinding : ActivitySplash2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySplash2Binding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.btnNext2.setOnClickListener {
            navigateToLogin()
        }
        viewBinding.iconBack.setOnClickListener {
            navigateToSplash1()
        }
    }

    private fun navigateToSplash1() {
        val intent = Intent(this,SplashActivity1::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToLogin() {
        val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}