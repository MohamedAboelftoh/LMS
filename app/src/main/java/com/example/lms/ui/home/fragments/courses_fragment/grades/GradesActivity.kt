package com.example.lms.ui.home.fragments.courses_fragment.grades

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lms.databinding.ActivityGradesBinding
import com.example.lms.ui.home.fragments.courses_fragment.CourseContent
import com.example.lms.ui.home.fragments.Variables
import com.example.lms.ui.home.navigateFromActivity

class GradesActivity : AppCompatActivity() {
    lateinit var viewBinding : ActivityGradesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityGradesBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.courseNameTv.text = Variables.courseName
        viewBinding.icBack.setOnClickListener {
            navigateFromActivity(this@GradesActivity,CourseContent())
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        navigateFromActivity(this@GradesActivity,CourseContent())
    }
}