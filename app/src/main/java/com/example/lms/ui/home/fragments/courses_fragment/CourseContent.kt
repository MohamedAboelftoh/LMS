package com.example.lms.ui.home.fragments.courses_fragment

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.lms.R
import com.example.lms.databinding.ActivityCourseContentBinding
import com.example.lms.ui.home.HomeActivity
import com.example.lms.ui.home.fragments.courses_fragment.assignments.AssignmentsActivity
import com.example.lms.ui.home.fragments.courses_fragment.grades.GradesActivity
import com.example.lms.ui.home.fragments.courses_fragment.material.MaterialActivity
import com.example.lms.ui.home.fragments.courses_fragment.quizzes.QuizzesActivity

class CourseContent : AppCompatActivity() {
    lateinit var vieBinding:ActivityCourseContentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       vieBinding= ActivityCourseContentBinding.inflate(layoutInflater)
        setContentView(vieBinding.root)
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
    }
}