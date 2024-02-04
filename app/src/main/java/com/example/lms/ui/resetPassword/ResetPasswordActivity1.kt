package com.example.lms.ui.resetPassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lms.databinding.ActivityResetPassword1Binding
import com.example.lms.ui.login.LoginActivity

class ResetPasswordActivity1 : AppCompatActivity() {
    lateinit var viewBinding : ActivityResetPassword1Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityResetPassword1Binding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.btnNext.setOnClickListener {
          navigateToResetPasswordActivity2()
        }
        viewBinding.iconBack.setOnClickListener {
            navigateToLoginActivity()
        }
    }

    private fun navigateToLoginActivity() {
        val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToResetPasswordActivity2() {
        val intent = Intent(this,ResetPasswordActivity2::class.java)
        startActivity(intent)
        finish()
    }
}