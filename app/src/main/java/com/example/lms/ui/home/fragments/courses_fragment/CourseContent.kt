package com.example.lms.ui.home.fragments.courses_fragment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.lms.R
import com.example.lms.databinding.ActivityCourseContentBinding
import com.example.lms.ui.home.fragments.courses_fragment.assignments.AssignmentsActivity
import com.example.lms.ui.home.fragments.courses_fragment.grades.GradesActivity
import com.example.lms.ui.home.fragments.courses_fragment.quizzes.QuizzesActivity

class CourseContent : AppCompatActivity() {
    lateinit var vieBinding:ActivityCourseContentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       vieBinding= ActivityCourseContentBinding.inflate(layoutInflater)
        setContentView(vieBinding.root)
        vieBinding.cardGrades.setOnClickListener {
            navigateToGradesActivity()
        }
        vieBinding.quizzes.setOnClickListener {
            navigateToQuizzesScreen()
        }
        vieBinding.cardAssignments.setOnClickListener {
            navigateToAssignmentsScreen()
        }

        vieBinding.icBack.setOnClickListener{
           this.finish()
        }
    }

    private fun navigateToGradesActivity() {
        val intent = Intent(this,GradesActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToAssignmentsScreen() {
        val intent = Intent(this,AssignmentsActivity::class.java)
        startActivity(intent)
    }
    private fun navigateToQuizzesScreen() {
        val intent = Intent(this,QuizzesActivity::class.java)
        startActivity(intent)
    }
}