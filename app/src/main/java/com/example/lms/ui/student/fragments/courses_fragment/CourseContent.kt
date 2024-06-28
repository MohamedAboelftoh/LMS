package com.example.lms.ui.student.fragments.courses_fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lms.databinding.ActivityCourseContentBinding
import com.example.lms.ui.NotConnectedActivity
import com.example.lms.ui.student.HomeActivity
import com.example.lms.ui.student.checkForInternet
import com.example.lms.ui.student.fragments.courses_fragment.assignments.AssignmentsActivity
import com.example.lms.ui.student.fragments.courses_fragment.grades.GradesActivity
import com.example.lms.ui.student.fragments.courses_fragment.material.MaterialActivity
import com.example.lms.ui.student.fragments.Variables
import com.example.lms.ui.student.fragments.courses_fragment.quizzes.QuizzesActivity
import com.example.lms.ui.student.navigateFromActivity

class CourseContent : AppCompatActivity() {
    private lateinit var vieBinding:ActivityCourseContentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       vieBinding= ActivityCourseContentBinding.inflate(layoutInflater)
        setContentView(vieBinding.root)
        vieBinding.courseNameTv.text = Variables.courseName
        clickListeners()
    }

    private fun clickListeners() {
        vieBinding.cardGrades.setOnClickListener {
            navigateFromActivity(this@CourseContent, GradesActivity())
        }
        vieBinding.quizzes.setOnClickListener {
            navigateFromActivity(this@CourseContent, QuizzesActivity())

        }
        vieBinding.cardAssignments.setOnClickListener {
            navigateFromActivity(this@CourseContent, AssignmentsActivity())
        }

        vieBinding.icBack.setOnClickListener {
            navigateFromActivity(this@CourseContent, HomeActivity())
        }

        vieBinding.cardMaterial.setOnClickListener {
            navigateFromActivity(this@CourseContent, MaterialActivity())
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        navigateFromActivity(this@CourseContent, HomeActivity())
    }
}