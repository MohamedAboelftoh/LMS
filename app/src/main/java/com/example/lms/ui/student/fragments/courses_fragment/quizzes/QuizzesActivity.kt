package com.example.lms.ui.student.fragments.courses_fragment.quizzes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.lms.databinding.ActivityQuizzesBinding
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.api.api_student.quizes.CourseQuizzesResponseItem
import com.example.lms.ui.student.fragments.courses_fragment.CourseContent
import com.example.lms.ui.student.fragments.Variables
import com.example.lms.ui.student.navigateFromActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class QuizzesActivity : AppCompatActivity() {
    private lateinit var quizAdapter : QuizzesAdapter
    lateinit var viewBinding : ActivityQuizzesBinding
    lateinit var myPreferencesToken: MyPreferencesToken

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityQuizzesBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.courseNameTv.text = Variables.courseName
        myPreferencesToken= MyPreferencesToken(this)
        viewBinding.icBack.setOnClickListener {
            navigateFromActivity(this@QuizzesActivity,CourseContent())
        }
        quizAdapter = QuizzesAdapter()
        quizAdapter.onBtnStartClickListener = object : QuizzesAdapter.OnBtnStartClickListener{
            override fun onClick(position: Int, item: com.example.lms.ui.api.api_student.quizes.CourseQuizzesResponseItem) {
                 Variables.quizId=item.id
                navigateFromActivity(this@QuizzesActivity,QuestionsActivity())
            }
        }
        viewBinding.quizzesRv.adapter =quizAdapter
        getCourseQuizzes()
    }

    private fun getCourseQuizzes(){
        val token=myPreferencesToken.loadData("token")
        val cycleId= Variables.cycleId
        ApiManager.getApi().getCourseQuizzes(token!!,cycleId!!).enqueue(object :Callback<ArrayList<com.example.lms.ui.api.api_student.quizes.CourseQuizzesResponseItem>>{
            override fun onResponse(
                call: Call<ArrayList<com.example.lms.ui.api.api_student.quizes.CourseQuizzesResponseItem>>,
                response: Response<ArrayList<com.example.lms.ui.api.api_student.quizes.CourseQuizzesResponseItem>>
            ) {
                if (response.isSuccessful) {

                    quizAdapter.bindQuizzes(response.body())
                }
                else
                {
                    Toast.makeText(this@QuizzesActivity, "failed to get the Quizzes", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ArrayList<com.example.lms.ui.api.api_student.quizes.CourseQuizzesResponseItem>>, t: Throwable) {
                Toast.makeText(this@QuizzesActivity, "onFailure " + t.localizedMessage, Toast.LENGTH_SHORT).show()
            }

        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        navigateFromActivity(this@QuizzesActivity,CourseContent())
    }

}