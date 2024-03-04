package com.example.lms.ui.home.fragments.courses_fragment.quizzes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lms.R
import com.example.lms.databinding.ActivityFinishBinding

class FinishActivity : AppCompatActivity() {
    lateinit var viewBinding : ActivityFinishBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.btnFinished.setOnClickListener {
            navigateToQuizzesActivity()
        }
    }
    private fun navigateToQuizzesActivity() {
        val intent = Intent(this,QuizzesActivity::class.java)
        startActivity(intent)
    }
}