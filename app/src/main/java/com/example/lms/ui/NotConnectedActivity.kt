package com.example.lms.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.lms.R
import com.example.lms.databinding.ActivityNotConnectedBinding

class NotConnectedActivity : AppCompatActivity() {
    lateinit var viewBinding:ActivityNotConnectedBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       viewBinding= ActivityNotConnectedBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        iconBackClick()
    }
    private fun iconBackClick() {
        viewBinding.icBack.setOnClickListener{
            finish()
        }
        viewBinding.backTv.setOnClickListener{
            finish()
        }
    }
}