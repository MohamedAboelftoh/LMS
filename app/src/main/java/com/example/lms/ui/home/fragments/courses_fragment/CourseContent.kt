package com.example.lms.ui.home.fragments.courses_fragment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lms.databinding.ActivityCourseContentBinding
import com.example.lms.ui.home.HomeActivity
import com.example.lms.ui.home.fragments.courses_fragment.assignments.AssignmentsActivity
import com.example.lms.ui.home.fragments.courses_fragment.grades.GradesActivity
import com.example.lms.ui.home.fragments.courses_fragment.material.MaterialActivity
import com.example.lms.ui.home.fragments.Variables
import com.example.lms.ui.home.fragments.courses_fragment.quizzes.QuizzesActivity
import com.example.lms.ui.home.navigateFromActivity

class CourseContent : AppCompatActivity() {
    lateinit var vieBinding:ActivityCourseContentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       vieBinding= ActivityCourseContentBinding.inflate(layoutInflater)
        setContentView(vieBinding.root)
        vieBinding.courseNameTv.text = Variables.courseName
        vieBinding.cardGrades.setOnClickListener {
            navigateToAnotherActivity(GradesActivity())
        }
        vieBinding.quizzes.setOnClickListener {
            navigateToAnotherActivity(QuizzesActivity())
        }
        vieBinding.cardAssignments.setOnClickListener {
            navigateToAnotherActivity(AssignmentsActivity())
        }

        vieBinding.icBack.setOnClickListener{
            navigateToAnotherActivity(HomeActivity())
        }

        vieBinding.cardMaterial.setOnClickListener {
            navigateToAnotherActivity(MaterialActivity())
        }
    }

    private fun navigateToAnotherActivity(activity:AppCompatActivity) {
        val intent = Intent(this,activity::class.java)
        startActivity(intent)
        this.finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        navigateFromActivity(this@CourseContent,HomeActivity())
    }
}