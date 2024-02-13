package com.example.lms.ui.home.fragments.account

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.lms.R
import com.example.lms.databinding.ActivityChangePhoneBinding
import com.example.lms.ui.home.HomeActivity

class ChangePhoneActivity : AppCompatActivity() {
    lateinit var viewBinding : ActivityChangePhoneBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityChangePhoneBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.icBack.setOnClickListener {
            navigateToAccount()
        }
    }
    private fun navigateToAccount() {
        val intent = Intent(this,HomeActivity::class.java)
        startActivity(intent)
    }
}