package com.example.lms.ui.home.fragments.courses_fragment.quizzes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.lms.R
import com.example.lms.databinding.ActivityQuestionsBinding

class QuestionsActivity : AppCompatActivity() {
    lateinit var viewBinding : ActivityQuestionsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityQuestionsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        pushFragmentQuestions(QuestionFragment())
        viewBinding.btnNext.setOnClickListener {
          navigateToFinishActivity()
        }
    }

    private fun navigateToFinishActivity() {
        val intent = Intent(this,FinishActivity::class.java)
        startActivity(intent)
    }

    private fun pushFragmentQuestions(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.question_container,fragment)
            .commit()
    }
}