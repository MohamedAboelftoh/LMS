package com.example.lms.ui.home.fragments.courses_fragment.quizzes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.lms.R
import com.example.lms.databinding.ActivityQuestionsBinding
import com.example.lms.ui.api.material.CourseMaterialResponseItem
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.api.quizes.QuestionsItem
import com.example.lms.ui.home.fragments.courses_fragment.material.Variables
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuestionsActivity : AppCompatActivity() {
    lateinit var viewBinding : ActivityQuestionsBinding
    lateinit var myPreferencesToken: MyPreferencesToken

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityQuestionsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        myPreferencesToken= MyPreferencesToken(this)


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