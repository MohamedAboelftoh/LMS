package com.example.lms.ui.home.fragments.courses_fragment.quizzes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.lms.databinding.ActivityQuestionsBinding
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.api.quizes.QuestionsItem
import com.example.lms.ui.api.quizes.QuizQuestionsResponse
import com.example.lms.ui.home.fragments.courses_fragment.material.Variables
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuestionsActivity : AppCompatActivity() {
    lateinit var viewBinding : ActivityQuestionsBinding
    lateinit var myPreferencesToken: MyPreferencesToken
    lateinit var questionsAdapter:QuestionsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityQuestionsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        myPreferencesToken= MyPreferencesToken(this)
        questionsAdapter= QuestionsAdapter()
        viewBinding.questionsRecycler.adapter=questionsAdapter


        getQuestions()

        val snapHelper : SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(viewBinding.questionsRecycler)
        onButtonNextClick()
    }


    fun getQuestions(){
        val token =myPreferencesToken.loadData("token")
        val quizId=Variables.quizId
        ApiManager.getApi().getQuizQuestions(token!!,quizId!!).enqueue(object :Callback<QuizQuestionsResponse>{
            override fun onResponse(
                call: Call<QuizQuestionsResponse>,
                response: Response<QuizQuestionsResponse>
            ) {
                if(response.isSuccessful){
                    questionsAdapter.bindQuestions(response.body()?.questions)
                    viewBinding.tvQuiz.text=response.body()?.title
                }
                else{
                    Toast.makeText(this@QuestionsActivity, "failed to get the Questions", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<QuizQuestionsResponse>, t: Throwable) {
                Toast.makeText(this@QuestionsActivity, "onFailure " + t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }


fun onButtonNextClick(){
    questionsAdapter.onNextClickListener=QuestionsAdapter.OnNextClickListener{position, item ->
        if(position==questionsAdapter.itemCountValue-1){
            val intent=Intent(this@QuestionsActivity,FinishActivity::class.java)
            startActivity(intent)
        }else{

            questionsAdapter.incrementItemCount()

            // Scroll the RecyclerView one step forward
            viewBinding.questionsRecycler.smoothScrollToPosition(position + 1)
        }


    }
}



}