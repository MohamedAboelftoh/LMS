package com.example.lms.ui.doctor.fragments.courses

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lms.R
import com.example.lms.databinding.ActivityDrCourseContentBinding
import com.example.lms.ui.doctor.DrFilesActivity
import com.example.lms.ui.student.fragments.Variables
import com.example.lms.ui.student.navigateFromActivity

class DrCourseContentActivity : AppCompatActivity() {
    lateinit var viewBinding : ActivityDrCourseContentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityDrCourseContentBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.courseNameTv.text = Variables.courseName
        viewBinding.cardMaterial.setOnClickListener {
            navigateFromActivity(this,DrFilesActivity())
        }
    }
}