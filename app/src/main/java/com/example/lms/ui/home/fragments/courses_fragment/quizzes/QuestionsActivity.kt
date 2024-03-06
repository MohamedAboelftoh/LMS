package com.example.lms.ui.home.fragments.courses_fragment.quizzes

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import android.window.OnBackInvokedDispatcher
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
import com.example.lms.ui.home.fragments.Variables
import com.example.lms.ui.home.navigateFromActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class QuestionsActivity : AppCompatActivity() {
    lateinit var viewBinding : ActivityQuestionsBinding
    lateinit var myPreferencesToken: MyPreferencesToken
    lateinit var questionsAdapter:QuestionsAdapter
    val startTimeInMillis : Long = 1 * 60 * 1000
    var remainingTime :Long = startTimeInMillis
    private lateinit var myCountDownTimer: CountDownTimer
    lateinit var finish:FinishActivity
    private val snapHelper : SnapHelper = LinearSnapHelper()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityQuestionsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        finish=FinishActivity()
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

//    private fun startTimer() {
//         object : CountDownTimer(startTimeInMillis, 1 * 1000) {
//            override fun onTick(p0: Long) {
//                remainingTime = p0
//                updateTimerText()
//            }
//            override fun onFinish() {
//                val intent=Intent(this@QuestionsActivity,QuizzesActivity::class.java)
//                startActivity(intent)
//                Toast.makeText(this@QuestionsActivity, "Time Ended", Toast.LENGTH_LONG).show()
//            }
//        }.start()
//    }
private fun startTimer() {
    myCountDownTimer = MyCountDownTimer.getInstance(
        startTimeInMillis,
        1000, // Update interval
        { millisUntilFinished ->
            remainingTime = millisUntilFinished
            updateTimerText()
        },
        {
            // Timer finished
            val intent=Intent(this@QuestionsActivity,QuizzesActivity::class.java)
               startActivity(intent)
            Toast.makeText(this@QuestionsActivity, "Time Ended", Toast.LENGTH_LONG).show()
            // Handle what to do when the timer finishes, like navigate to another activity
        }
    )
    myCountDownTimer.start()
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
        val quizId= Variables.quizId
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

     val questionsAnswersList : MutableList<AnswersItem> = mutableListOf()
    private var questionId :String?=null

    private fun onButtonNextClick(){
        questionsAdapter.onNextClickListener=QuestionsAdapter.OnNextClickListener{position, item ->
            if(position==questionsAdapter.itemCountValue-1){
                val intent=Intent(this@QuestionsActivity,FinishActivity::class.java)
                intent.putExtra("questionsAnswersList", ArrayList(questionsAnswersList))
                startActivity(intent)
            } else {
                questionsAdapter.incrementItemCount()
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

    override fun onBackPressed() {
        super.onBackPressed()
        navigateFromActivity(this@QuestionsActivity,QuizzesActivity())
    }
}
