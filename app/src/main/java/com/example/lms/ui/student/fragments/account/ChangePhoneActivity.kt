package com.example.lms.ui.student.fragments.account

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lms.databinding.ActivityChangePhoneBinding

class ChangePhoneActivity : AppCompatActivity() {
    lateinit var viewBinding : ActivityChangePhoneBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityChangePhoneBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.icBack.setOnClickListener {
            this.finish()
        }
    }
}