package com.example.lms.ui.home.fragments.courses_fragment.quizzes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.lms.databinding.ActivityQuizzesBinding
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.api.quizes.CourseQuizzesResponseItem
import com.example.lms.ui.home.fragments.courses_fragment.CourseContent
import com.example.lms.ui.home.fragments.courses_fragment.material.Variables
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuizzesActivity : AppCompatActivity() {
    private lateinit var quizAdapter : QuizzesAdapter
    lateinit var viewBinding : ActivityQuizzesBinding
    lateinit var myPreferencesToken:MyPreferencesToken

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityQuizzesBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        myPreferencesToken=MyPreferencesToken(this)
        viewBinding.imgBack.setOnClickListener {
            navigateToCourseContentActivity()
        }
        quizAdapter = QuizzesAdapter()
        quizAdapter.onBtnStartClickListener = object : QuizzesAdapter.OnBtnStartClickListener{
            override fun onClick(position: Int, item: CourseQuizzesResponseItem) {
                 Variables.quizId=item.id
                navigateToQuestionsActivity()
            }
        }
        viewBinding.quizzesRv.adapter =quizAdapter
        getCourseQuizzes()
    }

    private fun navigateToQuestionsActivity() {
        val intent = Intent(this , QuestionsActivity::class.java)
        startActivity(intent)
    }
    private fun navigateToCourseContentActivity() {
        val intent = Intent(this , CourseContent::class.java)
        startActivity(intent)
    }

    private fun getCourseQuizzes(){
        val token=myPreferencesToken.loadData("token")
        val cycleId=Variables.cycleId
        ApiManager.getApi().getCourseQuizzes(token!!,cycleId!!).enqueue(object :Callback<ArrayList<CourseQuizzesResponseItem>>{
            override fun onResponse(
                call: Call<ArrayList<CourseQuizzesResponseItem>>,
                response: Response<ArrayList<CourseQuizzesResponseItem>>
            ) {
                if (response.isSuccessful) {

                    quizAdapter.bindQuizzes(response.body())
                }
                else
                {
                    Toast.makeText(this@QuizzesActivity, "failed to get the Quizzes", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ArrayList<CourseQuizzesResponseItem>>, t: Throwable) {
                Toast.makeText(this@QuizzesActivity, "onFailure " + t.localizedMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }

}