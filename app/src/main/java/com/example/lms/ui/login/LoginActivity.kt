package com.example.lms.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lms.databinding.ActivityLoginBinding
import com.example.lms.ui.home.HomeActivity
import com.example.lms.ui.resetPassword.ResetPasswordActivity
import com.example.lms.ui.splashes.SplashActivity

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
            navigateToSplashActivity()
        }
        viewBinding.btnLogin.setOnClickListener {
            navigateToHome()
        }
    }

    private fun navigateToSplashActivity() {
        val intent = Intent(this,SplashActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun navigateToResetPassword() {
        val intent = Intent(this,ResetPasswordActivity::class.java)
        startActivity(intent)
        finish()
    }
    private fun navigateToHome() {
        val intent = Intent(this,HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}