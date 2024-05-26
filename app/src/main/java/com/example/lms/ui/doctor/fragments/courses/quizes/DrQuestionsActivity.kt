package com.example.lms.ui.doctor.fragments.courses.quizes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.lms.R
import com.example.lms.databinding.ActivityDrQuestionsBinding
import com.example.lms.ui.api.api_doctor.dr_courses.quizzes.DrQuestionsItem
import com.example.lms.ui.api.api_doctor.dr_courses.quizzes.DrQuizItem
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.student.fragments.Variables
import com.example.lms.ui.student.navigateFromActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DrQuestionsActivity : AppCompatActivity() {
    lateinit var viewBinding:ActivityDrQuestionsBinding
    private val questionsList = mutableListOf<DrQuestionsItem>()
     private lateinit var questionsAdapter : DrQuestionsAdapter
     private val snapHelper : SnapHelper = LinearSnapHelper()
    lateinit var myPreferencesToken: MyPreferencesToken
    var title : String ?= null
    var notes : String ?= null
    var startDate : String ?= null
    var endDate : String ?= null
    var grade : Int ?= null
    var points : Int ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding=ActivityDrQuestionsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        myPreferencesToken = MyPreferencesToken(this)
        getQuizData()
        for (i in 0 until points!!){
            questionsList.add(DrQuestionsItem())
        }
        questionsAdapter = DrQuestionsAdapter(questionsList)
        viewBinding.drQuestionsRecycler.adapter=questionsAdapter
         snapHelper.attachToRecyclerView(viewBinding.drQuestionsRecycler)
        viewBinding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.ic_add ->{
                    // Create a new DrQuestionsItem object with necessary details
                    //val newQuestion = DrQuestionsItem() // Add appropriate arguments if needed
                    // Add the new item to the adapter
                   // questionsAdapter.addItem(DrQuestionsItem())
                    // Scroll to the bottom to show the new item
                    //viewBinding.drQuestionsRecycler.scrollToPosition(questionsAdapter.itemCount - 1)
                }
                R.id.ic_publish ->{
                    val cycleId = Variables.cycleId
                    val token = myPreferencesToken.loadData("token")
                    val filledDataList = questionsAdapter.getQuestions()
                    val questions = DrQuizItem(notes,cycleId,endDate,grade,filledDataList,title,startDate)
                    ApiManager.getApi().createQuiz(token!!,questions)
                        .enqueue(object :Callback<DrQuizItem>{
                            override fun onResponse(
                                call: Call<DrQuizItem>,
                                response: Response<DrQuizItem>
                            ) {
                                if(response.isSuccessful){
                                    Toast.makeText(this@DrQuestionsActivity,"created", Toast.LENGTH_LONG).show()
                                    navigateFromActivity(this@DrQuestionsActivity,DrQuizzesActivity())
                                }else{
                                    Toast.makeText(this@DrQuestionsActivity,"Not created", Toast.LENGTH_LONG).show()
                                }
                            }

                            override fun onFailure(call: Call<DrQuizItem>, t: Throwable) {
                                Toast.makeText(this@DrQuestionsActivity,"error", Toast.LENGTH_LONG).show()
                            }

                        })
                }
            }
            true
        }
    }

    private fun getQuizData() {
        title = intent.getStringExtra("title")
        notes = intent.getStringExtra("notes")
        startDate = intent.getStringExtra("startDate")
        endDate = intent.getStringExtra("endDate")
        grade = intent.getStringExtra("grade")?.toInt()
        points = intent.getStringExtra("points")?.toInt()
    }
}