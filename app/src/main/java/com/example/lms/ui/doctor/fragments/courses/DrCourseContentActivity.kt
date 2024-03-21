package com.example.lms.ui.doctor.fragments.courses

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.lms.R
import com.example.lms.databinding.ActivityDrCourseContentBinding
import com.example.lms.ui.doctor.DrMainActivity
import com.example.lms.ui.doctor.fragments.courses.material.DrMaterialActivity
import com.example.lms.ui.student.fragments.Variables
import com.example.lms.ui.student.fragments.courses_fragment.CourseContent
import com.example.lms.ui.student.fragments.courses_fragment.material.LectureFragment
import com.example.lms.ui.student.navigateFromActivity

class DrCourseContentActivity : AppCompatActivity() {
    lateinit var viewBinding : ActivityDrCourseContentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityDrCourseContentBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.courseNameTv.text = Variables.courseName
        viewBinding.cardMaterial.setOnClickListener {
            navigateFromActivity(this,DrMaterialActivity())
        }
        viewBinding.icBack.setOnClickListener{
            navigateFromActivity(this,DrMainActivity())
        }
    }
}