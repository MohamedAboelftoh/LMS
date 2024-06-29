package com.example.lms.ui.doctor.fragments.courses

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.lms.R
import com.example.lms.databinding.ActivityDrCourseContentBinding
import com.example.lms.ui.NotConnectedActivity
import com.example.lms.ui.doctor.DrMainActivity
import com.example.lms.ui.doctor.fragments.courses.assignment.DrAssignActivity
import com.example.lms.ui.doctor.fragments.courses.grades.DrAllStudentActivity
import com.example.lms.ui.doctor.fragments.courses.material.DrMaterialActivity
import com.example.lms.ui.doctor.fragments.courses.quizes.DrQuizzesActivity
import com.example.lms.ui.student.checkForInternet
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
        viewBinding.cardAssignments.setOnClickListener{
            if(checkForInternet(this))
                navigateFromActivity(this@DrCourseContentActivity,DrAssignActivity())
            else
                navigateFromActivity(this@DrCourseContentActivity,NotConnectedActivity())

        }
        viewBinding.cardQuiezzes.setOnClickListener {
            if(checkForInternet(this))
                navigateFromActivity(this@DrCourseContentActivity,DrQuizzesActivity())
            else
                navigateFromActivity(this@DrCourseContentActivity,NotConnectedActivity())
        }
        viewBinding.cardGrades.setOnClickListener{
            if(checkForInternet(this@DrCourseContentActivity))
            navigateFromActivity(this@DrCourseContentActivity,DrAllStudentActivity())
            else
                navigateFromActivity(this,NotConnectedActivity())

        }
        viewBinding.icBack.setOnClickListener{
            navigateFromActivity(this,DrMainActivity())
        }
    }
}