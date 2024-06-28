package com.example.lms.ui.student.fragments.courses_fragment.quizzes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.lms.R
import com.example.lms.databinding.ActivityFinishBinding
import com.example.lms.ui.api.api_student.quizes.Result
import com.example.lms.ui.api.api_student.quizes.SubmitQuizResponse
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.api.api_student.quizes.submit.AnswersItem
import com.example.lms.ui.api.api_student.quizes.submit.SubmitQuizRequest
import com.example.lms.ui.student.fragments.Variables
import com.example.lms.ui.student.navigateFromActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FinishActivity : AppCompatActivity() {
    lateinit var viewBinding : ActivityFinishBinding
    lateinit var myPreferencesToken: MyPreferencesToken
    private var studentQuizGrade = 0
    private var fullGrade=0
    private var listAnswers: ArrayList<AnswersItem>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        myPreferencesToken= MyPreferencesToken(this)
        listAnswers = intent.getSerializableExtra("questionsAnswersList") as? ArrayList<AnswersItem>
        viewBinding.back.setOnClickListener {
            finish()
        }
        viewBinding.btnFinished.setOnClickListener {
            if(viewBinding.btnFinished.text == "Submit"){
                viewBinding.progressBar.visibility = View.VISIBLE
                submitQuiz()
            }
            else{
                navigateFromActivity(this@FinishActivity,QuizzesActivity())
            }
            }
    }
    private fun submitQuiz() {
        MyCountDownTimer.cancelTimer()
        val submitQuiz = SubmitQuizRequest(
            Variables.quizId,
            listAnswers
        )
        val token = myPreferencesToken.loadData("token")
        val submitQuizResponse: MutableList<Result> = mutableListOf()
        ApiManager.getApi().submitQuiz(submitQuiz, Variables.quizId!!, token!!).enqueue(object : Callback<SubmitQuizResponse> {
            override fun onResponse(
                call: Call<SubmitQuizResponse>,
                response: Response<SubmitQuizResponse>
            ) {
                viewBinding.progressBar.visibility = View.INVISIBLE
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    responseBody?.let {
                        submitQuizResponse.addAll(it.results)
                         studentQuizGrade = it.totalStudentGrade
                         fullGrade=it.totalGrade
                        changeUi()
                        // Now you can use the grade variable as needed
                    }
                } else {
                    Toast.makeText(this@FinishActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<SubmitQuizResponse>, t: Throwable) {
                viewBinding.progressBar.visibility = View.INVISIBLE
                Toast.makeText(this@FinishActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }


    /*private fun submitQuiz() {
        MyCountDownTimer.cancelTimer()
        val submitQuiz = SubmitQuizRequest(
            Variables.quizId,
            listAnswers
        )
        val token = myPreferencesToken.loadData("token")
        val submitQuizResponse: MutableList<Map<String?, Boolean?>> = mutableListOf()
        ApiManager.getApi().submitQuiz(submitQuiz, Variables.quizId!!, token!!).enqueue(object
            : Callback<List<Map<String?, Boolean?>>> {
            override fun onResponse(
                call: Call<List<Map<String?, Boolean?>>>,
                response: Response<List<Map<String?, Boolean?>>>
            ) {
                viewBinding.progressBar.visibility = View.INVISIBLE
                if (response.isSuccessful) {
                    //val message = StringBuilder()
                    response.body()?.forEach { item ->
                        submitQuizResponse.add(item)
                      //  message.append("${item.keys}: ${item.values}\n")
                    }
                    //calculateGrade(submitQuizResponse)
                    changeUi()
                } else {
                    Toast.makeText(this@FinishActivity, "Error", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Map<String?, Boolean?>>>, t: Throwable) {
                viewBinding.progressBar.visibility = View.INVISIBLE
                Toast.makeText(this@FinishActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }*/

    private fun changeUi() {
        viewBinding.back.visibility = View.GONE
        viewBinding.btnFinished.text = getString(R.string.done)
        viewBinding.tvFinished.text = getString(R.string.finished)
        viewBinding.grade.text = "$studentQuizGrade/$fullGrade"
        viewBinding.answerSuccessfully.text = getString(R.string.answers_have_been_sent_successfully)
        viewBinding.tvFinished.setTextColor(ContextCompat.getColor(this, R.color.greenColor))
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if(viewBinding.btnFinished.text == "Submit"){
           finish()
        }
        else{
            navigateFromActivity(this@FinishActivity,QuizzesActivity())
        }
    }

}