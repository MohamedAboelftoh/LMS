package com.example.lms.ui.doctor.fragments.courses.grades

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.lms.databinding.ActivityAllStudentBinding
import com.example.lms.ui.api.api_doctor.grades.AllStuEnrolledInCourseItem
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.student.fragments.Variables
import com.example.lms.ui.student.navigateFromActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DrAllStudentActivity : AppCompatActivity() {
    lateinit var viewBinding : ActivityAllStudentBinding
    lateinit var myPreferencesToken: MyPreferencesToken
    lateinit var adapter : DrAllStudentsGradesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityAllStudentBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        initViews()
        getStudents()
        btnMoreClick()

    }

    private fun btnMoreClick() {
        adapter.onBtnMoreClickListener=object :DrAllStudentsGradesAdapter.OnBtnMoreClickListener{
            override fun btnMoreClickListener(item: AllStuEnrolledInCourseItem, position: Int) {
                Variables.studentName=item.studentName
                Variables.studentId=item.studentId
                navigateFromActivity(this@DrAllStudentActivity,StuSelectedGradesActivity())
            }
        }
    }

    private fun initViews() {
        myPreferencesToken = MyPreferencesToken(this)
        adapter= DrAllStudentsGradesAdapter()
        viewBinding.studentsNamesRecycler.adapter=adapter
        viewBinding.courseNameTv.text = Variables.courseName
    }
    private fun getStudents(){
        val token=myPreferencesToken.loadData("token")
        val cycleId=Variables.cycleId
        ApiManager.getApi().drGetStudentsEnrolledInACourse(token?:"",cycleId?:"")
            .enqueue(object :Callback<ArrayList<AllStuEnrolledInCourseItem>>{
                override fun onResponse(
                    p0: Call<ArrayList<AllStuEnrolledInCourseItem>>,
                    response: Response<ArrayList<AllStuEnrolledInCourseItem>>
                ) {
                    if (response.isSuccessful){
                        adapter.bindStudentsList(response.body())
                    }
                    else{
                        Toast.makeText(this@DrAllStudentActivity,"failed to get Students Name", Toast.LENGTH_SHORT).show()

                    }
                }

                override fun onFailure(
                    p0: Call<ArrayList<AllStuEnrolledInCourseItem>>,
                    p1: Throwable
                ) {
                    Toast.makeText(this@DrAllStudentActivity,"${p1.localizedMessage}", Toast.LENGTH_SHORT).show()
                }
            })
    }
}