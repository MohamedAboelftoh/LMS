package com.example.lms.ui.student.fragments.courses_fragment.quizzes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.lms.R
import com.example.lms.databinding.ActivityQuestionsBinding
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.api.api_student.quizes.QuizQuestionsResponse
import com.example.lms.ui.api.api_student.quizes.submit.AnswersItem
import com.example.lms.ui.student.fragments.Variables
import com.example.lms.ui.student.navigateFromActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class QuestionsActivity : AppCompatActivity() {
    lateinit var viewBinding : ActivityQuestionsBinding
    lateinit var myPreferencesToken: MyPreferencesToken
    lateinit var questionsAdapter:QuestionsAdapter
     var duration : Long ?= 1 * 60 * 1000
   // val startTimeInMillis : Long = 1 * 60 * 1000
   private var remainingTime :Long = duration!!
    private lateinit var myCountDownTimer: CountDownTimer
    lateinit var finish:FinishActivity
    private val snapHelper : SnapHelper = LinearSnapHelper()
    private val questionsAnswersList : ArrayList<com.example.lms.ui.api.api_student.quizes.submit.AnswersItem> = arrayListOf()
    private var questionsNumber = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityQuestionsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        finish=FinishActivity()
        viewBinding.courseNameTv.text = Variables.courseName
        myPreferencesToken= MyPreferencesToken(this)
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
            duration!!,
            1000, // Update interval
            { millisUntilFinished ->
                remainingTime = millisUntilFinished
                updateTimerText()
            },
            {
                // Timer finished
                navigateFromActivity(this@QuestionsActivity,QuizzesActivity())
                Toast.makeText(this@QuestionsActivity, "Time Ended", Toast.LENGTH_LONG).show()
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
        ApiManager.getApi().getQuizQuestions(token!!,quizId!!).enqueue(object :Callback<com.example.lms.ui.api.api_student.quizes.QuizQuestionsResponse>{
            override fun onResponse(
                call: Call<com.example.lms.ui.api.api_student.quizes.QuizQuestionsResponse>,
                response: Response<com.example.lms.ui.api.api_student.quizes.QuizQuestionsResponse>
            ) {
                duration = convertTimeToMilliseconds(response.body()?.duration!!)
                startTimer()
                if(response.isSuccessful){
                    questionsAdapter.bindQuestions(response.body()?.questions)
                    viewBinding.tvQuiz.text=response.body()?.title
                }
                else{
                    Toast.makeText(this@QuestionsActivity, "failed to get the Questions", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<com.example.lms.ui.api.api_student.quizes.QuizQuestionsResponse>, t: Throwable) {
                Toast.makeText(this@QuestionsActivity, "onFailure " + t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun onButtonNextClick(){
        questionsAdapter.onNextClickListener=QuestionsAdapter.OnNextClickListener{ position, item ->
            if(position==questionsAdapter.itemCountValue-1){
                questionsNumber = position + 1
                val intent = Intent(this,FinishActivity::class.java)
                intent.putExtra("questionsAnswersList",questionsAnswersList)
                intent.putExtra("questionNumbers",questionsNumber)
                startActivity(intent)
            } else {
                questionsAdapter.incrementItemCount()
                viewBinding.questionsRecycler.smoothScrollToPosition(position + 1)
            }
        }
    }
    private fun onRadioBtnSelected() {
        questionsAdapter.onRadioButtonSelect = QuestionsAdapter.OnRadioButtonSelect { position, item, checkedRadioButtonId ->
            // Get the question ID
            val questionId = item?.id
            // Check if item and answers list are not null and contains at least one element
            if (item != null && item.answers?.isNotEmpty() == true) {
                // Get the selected answer ID from the radio button ID
                val selectedAnswerId = when (checkedRadioButtonId) {
                    R.id.radio_1 -> if (item.answers.size >= 1) item.answers[0]?.id else null
                    R.id.radio_2 -> if (item.answers.size >= 2) item.answers[1]?.id else null
                    R.id.radio_3 -> if (item.answers.size >= 3) item.answers[2]?.id else null
                    R.id.radio_4 -> if (item.answers.size >= 4) item.answers[3]?.id else null
                    else -> null
                }
                // Check if an answer for this question already exists in the list
                val existingAnswer = questionsAnswersList.find { it.questionId == questionId }
                // If an answer already exists, update it; otherwise, add a new answer
                if (existingAnswer != null) {
                    // Update the existing answer with the selected option
                    existingAnswer.answerId = selectedAnswerId
                } else {
                    // Add a new answer to the list
                    questionsAnswersList.add(
                        com.example.lms.ui.api.api_student.quizes.submit.AnswersItem(
                            selectedAnswerId,
                            questionId
                        )
                    )
                }
            }
        }
    }

    /*
    //new
    private fun onRadioBtnSelected() {
        questionsAdapter.onRadioButtonSelect = QuestionsAdapter.OnRadioButtonSelect { position, item, checkedRadioButtonId ->
            // Get the question ID
            val questionId = item?.id
            // Get the selected answer ID from the radio button ID
            val selectedAnswerId = when (checkedRadioButtonId) {
                R.id.radio_1 -> item?.answers?.get(0)?.id
                R.id.radio_2 -> item?.answers?.get(1)?.id
                R.id.radio_3 -> item?.answers?.get(2)?.id
                R.id.radio_4 -> item?.answers?.get(3)?.id
                else -> null
            }
            // Check if an answer for this question already exists in the list
            val existingAnswer = questionsAnswersList.find { it.questionId == questionId }
            // If an answer already exists, update it; otherwise, add a new answer
            if (existingAnswer != null) {
                // Update the existing answer with the selected option
                existingAnswer.answerId = selectedAnswerId
            } else {
                // Add a new answer to the list
                questionsAnswersList.add(AnswersItem(selectedAnswerId, questionId))
            }
        }
    }
    */


    override fun onBackPressed() {

    }
    fun convertTimeToMilliseconds(timeStr: String): Long {
        val parts = timeStr.split(":")
        if (parts.size != 3) {
            throw IllegalArgumentException("Invalid time format")
        }

        val hours = parts[0].toLong()
        val minutes = parts[1].toLong()
        val seconds = parts[2].toLong()

        // Convert the time to seconds and then to milliseconds
        return (hours * 3600 + minutes * 60 + seconds) * 1000
    }
}