package com.example.lms.ui.home.fragments.courses_fragment.quizzes

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.lms.R
import com.example.lms.databinding.ActivityQuestionsBinding
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.api.quizes.QuizQuestionsResponse
import com.example.lms.ui.api.quizes.submit.AnswersItem
import com.example.lms.ui.api.quizes.submit.SubmitQuizRequest
import com.example.lms.ui.home.fragments.courses_fragment.material.Variables
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuestionsActivity : AppCompatActivity() {
    lateinit var viewBinding : ActivityQuestionsBinding
    lateinit var myPreferencesToken: MyPreferencesToken
    lateinit var questionsAdapter:QuestionsAdapter
    val startTimeInMillis : Long = 1 * 60 * 1000
    var remainingTime :Long = startTimeInMillis
    private val snapHelper : SnapHelper = LinearSnapHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityQuestionsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.courseNameTv.text = Variables.courseName
        myPreferencesToken= MyPreferencesToken(this)
        startTimer()
        questionsAdapter= QuestionsAdapter()
        viewBinding.questionsRecycler.adapter=questionsAdapter
        getQuestions()
        snapHelper.attachToRecyclerView(viewBinding.questionsRecycler)
        onButtonNextClick()
        onRadioBtnSelected()
        iconBackClick()
    }

    private fun startTimer() {
         object : CountDownTimer(startTimeInMillis, 1 * 1000) {
            override fun onTick(p0: Long) {
                remainingTime = p0
                updateTimerText()
            }

            override fun onFinish() {
                val intent=Intent(this@QuestionsActivity,QuizzesActivity::class.java)
                startActivity(intent)
                Toast.makeText(this@QuestionsActivity, "Time Ended", Toast.LENGTH_LONG).show()
            }

        }.start()
    }

    private fun updateTimerText() {
        val minute = remainingTime.div(1000).div(60)
        val second = remainingTime.div(1000) % 60
        val formattedTime = String.format("%02d:%02d", minute , second)
        viewBinding.tvClock.text = formattedTime
    }

    private fun iconBackClick(){
        viewBinding.icBack.setOnClickListener {
            val layoutManager = viewBinding.questionsRecycler.layoutManager as LinearLayoutManager
            val currentPosition = layoutManager.findFirstVisibleItemPosition()
            if (currentPosition != RecyclerView.NO_POSITION && currentPosition > 0) {
                viewBinding.questionsRecycler.smoothScrollToPosition(currentPosition - 1)
                }
           }
    }


    private fun getQuestions(){
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

    private val questionsAnswersList : MutableList<AnswersItem> = mutableListOf()
    private var questionId :String?=null
    private fun onButtonNextClick(){
        questionsAdapter.onNextClickListener=QuestionsAdapter.OnNextClickListener{position, item ->
            if(position==questionsAdapter.itemCountValue-1){
                val submitQuiz = SubmitQuizRequest(Variables.quizId,questionsAnswersList)
                val token = myPreferencesToken.loadData("token")

                val submitQuizResponse: MutableList<Map<String?,Boolean?>> = mutableListOf()

                ApiManager.getApi().submitQuiz(submitQuiz,Variables.quizId!!,token!!).enqueue(object
                    :Callback< List<Map<String?,Boolean?>>>{
                    override fun onResponse(
                        call: Call< List<Map<String?,Boolean?>>>,
                        response: Response< List<Map<String?,Boolean?>>>
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
                                    val intent=Intent(this@QuestionsActivity,FinishActivity::class.java)
                                    startActivity(intent)
                                },
                                negActionName = "Cancel",
                                negAction = { dialogInterface, i ->
                                    dialogInterface.dismiss()
                                }
                            )
                        } else {
                            Toast.makeText(this@QuestionsActivity, "Error", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call< List<Map<String?,Boolean?>>>, t: Throwable) {
                        Toast.makeText(this@QuestionsActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
                    }
                })

            } else {
                questionsAdapter.incrementItemCount()
                // Scroll the RecyclerView one step forward
                viewBinding.questionsRecycler.smoothScrollToPosition(position + 1)
            }
        }
    }
    private fun onRadioBtnSelected(){
        questionsAdapter.onRadioButtonSelect=
            QuestionsAdapter.OnRadioButtonSelect { position, item, checkedRadioButtonId ->
                when(checkedRadioButtonId){
                    R.id.radio_1 ->{
                        questionId = "Q001_1_A1"
                    }

                    R.id.radio_2 -> {
                        questionId = "Q002_1_A1"
                    }

                    R.id.radio_3 -> {
                        questionId = "Q003_1_A1"
                    }

                    R.id.radio_4 -> {
                        questionId = "Q004_1_A1"
                    }
                }
                questionsAnswersList.add(AnswersItem(questionId,item?.id))
            }
    }


    fun showMessage(message:String
                    ,posActionName:String?=null
                    ,posAction: DialogInterface.OnClickListener?=null
                    ,negActionName:String?=null
                    ,negAction: DialogInterface.OnClickListener?=null

    ): AlertDialog {
        val dialogBuilder= AlertDialog.Builder(this)
        dialogBuilder.setMessage(message)
        if (posActionName!=null)
        {
            dialogBuilder.setPositiveButton(posActionName,posAction)
        }
        if(negActionName!=null)
        {
            dialogBuilder.setNegativeButton(negActionName,negAction)

        }
        return dialogBuilder.show()
    }
}
