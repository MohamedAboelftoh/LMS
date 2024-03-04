package com.example.lms.ui.home.fragments.account

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.lms.R
import com.example.lms.databinding.ActivityChangePasswordBinding
import com.example.lms.ui.home.HomeActivity

class ChangePasswordActivity : AppCompatActivity() {
    lateinit var viewBinding : ActivityChangePasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.icBack.setOnClickListener {
            this.finish()
        }
    }

}