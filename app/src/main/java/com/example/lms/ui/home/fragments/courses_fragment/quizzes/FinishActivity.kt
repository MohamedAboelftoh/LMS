package com.example.lms.ui.home.fragments.courses_fragment.quizzes

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.lms.databinding.ActivityFinishBinding
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.api.quizes.submit.AnswersItem
import com.example.lms.ui.api.quizes.submit.SubmitQuizRequest
import com.example.lms.ui.home.fragments.Variables
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FinishActivity : AppCompatActivity() {
    lateinit var viewBinding : ActivityFinishBinding
    lateinit var  questionActivity:QuestionsActivity
    lateinit var myPreferencesToken: MyPreferencesToken
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityFinishBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        myPreferencesToken= MyPreferencesToken(this)
        questionActivity= QuestionsActivity()
        val answersList = intent.getSerializableExtra("questionsAnswersList") as? ArrayList<AnswersItem>

        viewBinding.btnFinished.setOnClickListener {
            val submitQuiz = SubmitQuizRequest(Variables.quizId,answersList)
            val token = myPreferencesToken.loadData("token")
            val submitQuizResponse: MutableList<Map<String?,Boolean?>> = mutableListOf()

            ApiManager.getApi().submitQuiz(submitQuiz, Variables.quizId!!,token!!).enqueue(object
                : Callback<List<Map<String?, Boolean?>>> {
                override fun onResponse(
                    call: Call<List<Map<String?, Boolean?>>>,
                    response: Response<List<Map<String?, Boolean?>>>
                ) {
                    if (response.isSuccessful){

                        val message = StringBuilder()
                        response.body()?.forEach { item->
                            submitQuizResponse.add(item)
                            message.append("${item.keys}: ${item.values}\n")

                        }
                        showMessage(
                            message.toString(),
                            posActionName = "OK",
                            posAction = { dialogInterface,i->
                                dialogInterface.dismiss()
                               MyCountDownTimer.cancelTimer()
                                val intent=Intent(this@FinishActivity,QuizzesActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        )
                    } else {
                        Toast.makeText(this@FinishActivity, "Error", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call< List<Map<String?,Boolean?>>>, t: Throwable) {
                    Toast.makeText(this@FinishActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
    fun showMessage(message:String
                    ,posActionName:String?=null
                    ,posAction: DialogInterface.OnClickListener?=null


    ): AlertDialog {
        val dialogBuilder= AlertDialog.Builder(this)
        dialogBuilder.setMessage(message)
        if (posActionName!=null)
        {
            dialogBuilder.setPositiveButton(posActionName,posAction)
        }
        dialogBuilder.setCancelable(false)
        return dialogBuilder.show()
    }



}