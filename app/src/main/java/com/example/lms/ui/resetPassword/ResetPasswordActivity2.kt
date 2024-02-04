package com.example.lms.ui.resetPassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lms.R
import com.example.lms.databinding.ActivityResetPassword2Binding

class ResetPasswordActivity2 : AppCompatActivity() {
    private lateinit var viewBinding : ActivityResetPassword2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityResetPassword2Binding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.btnNext.setOnClickListener {
            
        }
    }
}