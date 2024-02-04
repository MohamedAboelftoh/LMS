package com.example.lms.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lms.databinding.ActivityLoginBinding
import com.example.lms.ui.resetPassword.ResetPasswordActivity1
import com.example.lms.ui.splashes.SplashActivity2

class LoginActivity : AppCompatActivity() {
     private lateinit var viewBinding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.forgetPass.setOnClickListener {
            navigateToResetPassword()
        }
        viewBinding.iconBack.setOnClickListener {
            navigateToSplashActivity2()
        }
    }

    private fun navigateToSplashActivity2() {
        val intent = Intent(this,SplashActivity2::class.java)
        startActivity(intent)
        finish()
    }
    private fun navigateToResetPassword() {
        val intent = Intent(this,ResetPasswordActivity1::class.java)
        startActivity(intent)
        finish()
    }
}