package com.example.lms.ui.doctor.fragments.courses.grades

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.lms.databinding.ActivityStudentSelectedGradesBinding
import com.example.lms.ui.api.api_doctor.grades.GradesSelectedStuResponseItem
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.student.fragments.Variables
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StuSelectedGradesActivity : AppCompatActivity() {
    lateinit var viewBinding : ActivityStudentSelectedGradesBinding
    lateinit var myPreferencesToken: MyPreferencesToken
    lateinit var adapter : GradesStuSelectedAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityStudentSelectedGradesBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        initViews()
        getStuGrades()
    }

    private fun getStuGrades() {
        val token=myPreferencesToken.loadData("token")
        val cycleId=Variables.cycleId
        val studentId=Variables.studentId
        ApiManager.getApi().drGetStuSelectedGradesInACourse(token!!,cycleId!!,studentId!!)
            .enqueue(object :Callback<ArrayList<GradesSelectedStuResponseItem>>{
                override fun onResponse(
                    p0: Call<ArrayList<GradesSelectedStuResponseItem>>,
                    response: Response<ArrayList<GradesSelectedStuResponseItem>>
                ) {
                    if (response.isSuccessful){
                        adapter.bindStuGrades(response.body())
                    }
                    else{
                        Toast.makeText(this@StuSelectedGradesActivity,"failed to get Students Grades", Toast.LENGTH_SHORT).show()

                    }
                }

                override fun onFailure(
                    p0: Call<ArrayList<GradesSelectedStuResponseItem>>,
                    p1: Throwable
                ) {
                    Toast.makeText(this@StuSelectedGradesActivity,"${p1.localizedMessage}", Toast.LENGTH_SHORT).show()

                }
            })
    }

    private fun initViews() {
        myPreferencesToken = MyPreferencesToken(this)
        adapter= GradesStuSelectedAdapter()
        viewBinding.tasksGradesRecycler.adapter=adapter
        viewBinding.studentName.text = Variables.studentName
    }

}