package com.example.lms.ui.home.fragments.courses_fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.lms.R
import com.example.lms.databinding.ActivityCourseContentBinding

class CourseContent : AppCompatActivity() {
    lateinit var vieBinding:ActivityCourseContentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       vieBinding= ActivityCourseContentBinding.inflate(layoutInflater)
        setContentView(vieBinding.root)

        vieBinding.icBack.setOnClickListener{
           this.finish()      }
    }
}