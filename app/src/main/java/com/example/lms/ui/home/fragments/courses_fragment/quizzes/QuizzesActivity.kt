package com.example.lms.ui.home.fragments.courses_fragment.quizzes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lms.R
import com.example.lms.databinding.ActivityQuizzesBinding
import com.example.lms.ui.home.fragments.courses_fragment.CourseContent
import com.example.lms.ui.home.fragments.home_fragment.HomeRecyclerViewAdapter
import com.example.lms.ui.home.fragments.home_fragment.ItemHomeNews

class QuizzesActivity : AppCompatActivity() {
    private var quizzesList: MutableList<QuizItem> = mutableListOf()
    private lateinit var quizAdapter : QuizzesAdapter
    lateinit var viewBinding : ActivityQuizzesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityQuizzesBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.imgBack.setOnClickListener {
            navigateToCourseContentActivity()
        }
        initQuizzesList()
        quizAdapter = QuizzesAdapter(quizzesList)
        quizAdapter.onBtnStartClickListener = object : QuizzesAdapter.OnBtnStartClickListener{
            override fun onClick(position: Int, item: QuizItem) {
                navigateToQuestionsActivity()
            }
        }
        viewBinding.quizzesRv.adapter =quizAdapter
    }

    private fun navigateToQuestionsActivity() {
        val intent = Intent(this , QuestionsActivity::class.java)
        startActivity(intent)
    }
    private fun navigateToCourseContentActivity() {
        val intent = Intent(this , CourseContent::class.java)
        startActivity(intent)
    }

    private fun initQuizzesList() {
        for(i in 0..5){
            quizzesList.add(QuizItem("10:00","11:00","parallel programing quiz 1"))
        }
    }
}