package com.example.lms.ui.splashes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.lms.R
import com.example.lms.databinding.ActivitySplash1Binding
import com.example.lms.ui.home.HomeActivity

class SplashActivity1 : AppCompatActivity() {
    private lateinit var viewBinding : ActivitySplash1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySplash1Binding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.btnNext.setOnClickListener {
            navigateToSplash2()
        }
    }

    private fun navigateToSplash2() {
        val intent = Intent(this,SplashActivity2::class.java)
        startActivity(intent)
    }
}