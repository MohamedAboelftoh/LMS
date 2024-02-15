package com.example.lms.ui.home.fragments.courses_fragment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.lms.R
import com.example.lms.databinding.ActivityCourseContentBinding
import com.example.lms.ui.home.fragments.courses_fragment.quizzes.QuizzesActivity

class CourseContent : AppCompatActivity() {
    lateinit var vieBinding:ActivityCourseContentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       vieBinding= ActivityCourseContentBinding.inflate(layoutInflater)
        setContentView(vieBinding.root)
        vieBinding.quizzes.setOnClickListener {
            navigateToQuizzesScreen()
        }

        vieBinding.icBack.setOnClickListener{
           this.finish()      }
    }

    private fun navigateToQuizzesScreen() {
        val intent = Intent(this,QuizzesActivity::class.java)
        startActivity(intent)
    }

}