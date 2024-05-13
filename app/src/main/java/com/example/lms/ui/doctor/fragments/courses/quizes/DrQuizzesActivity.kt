package com.example.lms.ui.doctor.fragments.courses.quizes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.lms.R
import com.example.lms.databinding.ActivityDrQuizesBinding
import com.example.lms.ui.api.api_doctor.dr_courses.quizzes.DrQuizzesResponseItem
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.student.fragments.Variables
import com.example.lms.ui.student.navigateFromActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DrQuizzesActivity : AppCompatActivity() {
    lateinit var viewBinding : ActivityDrQuizesBinding
    lateinit var myPreferencesToken: MyPreferencesToken
    val adapter = DrQuizzesAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityDrQuizesBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        myPreferencesToken = MyPreferencesToken(this)
        viewBinding.courseNameTv.text = Variables.courseName
        viewBinding.floatingActionBtn.setOnClickListener {
            navigateFromActivity(this@DrQuizzesActivity,DrAddQuizActivity())
        }
        viewBinding.rvQuizzes.adapter = adapter
        getAllQuizzesOfCourse()
    }

    private fun getAllQuizzesOfCourse() {
        val token = myPreferencesToken.loadData("token")
        val cycleId = Variables.cycleId
        ApiManager.getApi().getAllQuizzesForOneCourse(cycleId!!,token!!)
            .enqueue(object : Callback<ArrayList<DrQuizzesResponseItem>>{
                override fun onResponse(
                    call: Call<ArrayList<DrQuizzesResponseItem>>,
                    response: Response<ArrayList<DrQuizzesResponseItem>>
                ) {
                    if(response.isSuccessful){
                        adapter.bindData(response.body())
                    }
                    else{
                        Toast.makeText(this@DrQuizzesActivity,"error",Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ArrayList<DrQuizzesResponseItem>>, t: Throwable) {
                    Toast.makeText(this@DrQuizzesActivity,"error",Toast.LENGTH_SHORT).show()
                }
            })
    }
}