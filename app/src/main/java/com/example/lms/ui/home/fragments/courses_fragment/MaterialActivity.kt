package com.example.lms.ui.home.fragments.courses_fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.lms.R
import com.example.lms.databinding.ActivityMaterialBinding

class MaterialActivity : AppCompatActivity() {
    lateinit var viewBinding:ActivityMaterialBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding=ActivityMaterialBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
    }
}