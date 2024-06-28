package com.example.lms.ui.student.fragments.courses_fragment.quizzes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.lms.databinding.ActivityQuizzesBinding
import com.example.lms.ui.NotConnectedActivity
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.api.api_student.quizes.CourseQuizzesResponseItem
import com.example.lms.ui.db.DataBase
import com.example.lms.ui.student.checkForInternet
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
        initViews()
        onIconBackClick()
        if(checkForInternet(this)) {
            getCourseQuizzes()
        }
        else{
            quizAdapter.bindQuizzes(  DataBase.getInstance(this).stuQuizzesDao().getQuizzesFromLocal())
        }
        onButtonStartClick()
//        getCourseQuizzes()
    }
    private fun cashQuizzesInLocal(body: ArrayList<CourseQuizzesResponseItem>?) {
        DataBase.getInstance(this).stuQuizzesDao().deleteAllQuizzes()
        DataBase.getInstance(this).stuQuizzesDao().insertQuizzes(body!!)
    }

    private fun initViews() {
        viewBinding.courseNameTv.text = Variables.courseName
        myPreferencesToken= MyPreferencesToken(this)
        quizAdapter = QuizzesAdapter()
        viewBinding.quizzesRv.adapter =quizAdapter
    }
    private fun onIconBackClick() {
        viewBinding.icBack.setOnClickListener {
            navigateFromActivity(this@QuizzesActivity,CourseContent())
        }
    }
    private fun onButtonStartClick() {
        quizAdapter.onBtnStartClickListener = object : QuizzesAdapter.OnBtnStartClickListener{
            override fun onClick(position: Int, item: CourseQuizzesResponseItem?) {
                Variables.quizId=item?.id
                if (checkForInternet(this@QuizzesActivity)) {
                    navigateFromActivity(this@QuizzesActivity, QuestionsActivity())
                }
                else{
                    navigateFromActivity(this@QuizzesActivity, NotConnectedActivity())
                }
            }
        }
    }

    private fun getCourseQuizzes(){
        val token=myPreferencesToken.loadData("token")
        val cycleId= Variables.cycleId
        ApiManager.getApi().getCourseQuizzes(token!!,cycleId!!).enqueue(object :Callback<ArrayList<CourseQuizzesResponseItem>>{
            override fun onResponse(
                call: Call<ArrayList<CourseQuizzesResponseItem>>,
                response: Response<ArrayList<CourseQuizzesResponseItem>>
            ) {
                if (response.isSuccessful) {
                    cashQuizzesInLocal(response.body())
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

    override fun onBackPressed() {
        super.onBackPressed()
        navigateFromActivity(this@QuizzesActivity,CourseContent())
    }
}