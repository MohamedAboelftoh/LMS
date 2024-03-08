package com.example.lms.ui.home.fragments.courses_fragment.quizzes

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.lms.R
import com.example.lms.databinding.ActivityFinishBinding
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.api.quizes.submit.AnswersItem
import com.example.lms.ui.api.quizes.submit.SubmitQuizRequest
import com.example.lms.ui.home.fragments.Variables
import com.example.lms.ui.home.navigateFromActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FinishActivity : AppCompatActivity() {
    lateinit var viewBinding : ActivityFinishBinding
    lateinit var myPreferencesToken: MyPreferencesToken
    private var grade = 0
     private var questionNumber : Int ?= 0
    private var listAnswers: ArrayList<AnswersItem>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        myPreferencesToken= MyPreferencesToken(this)
        listAnswers = intent.getSerializableExtra("questionsAnswersList") as? ArrayList<AnswersItem>
        questionNumber = intent.getIntExtra("questionNumbers",0)
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
        val submitQuiz = SubmitQuizRequest(Variables.quizId, listAnswers)
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
                    calculateGrade(submitQuizResponse)
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
    }

    private fun changeUi() {
        viewBinding.back.visibility = View.GONE
        viewBinding.btnFinished.text = getString(R.string.done)
        viewBinding.tvFinished.text = getString(R.string.finished)
        viewBinding.grade.text = "$grade/$questionNumber"
        viewBinding.answerSuccessfully.text = getString(R.string.answers_have_been_sent_successfully)
        viewBinding.tvFinished.setTextColor(ContextCompat.getColor(this, R.color.greenColor))
    }

    private fun calculateGrade(submitQuizResponse: MutableList<Map<String?, Boolean?>>) {
        submitQuizResponse.forEach { map ->
            map.values.forEach { value ->
                if (value == true) {
                    grade++
                }
            }
        }
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
//    fun showMessage(message:String
//                    ,posActionName:String?=null
//                    ,posAction: DialogInterface.OnClickListener?=null
//
//
//    ): AlertDialog {
//        val dialogBuilder= AlertDialog.Builder(this)
//        dialogBuilder.setMessage(message)
//        if (posActionName!=null)
//        {
//            dialogBuilder.setPositiveButton(posActionName,posAction)
//        }
//        dialogBuilder.setCancelable(false)
//        return dialogBuilder.show()
//    }

}