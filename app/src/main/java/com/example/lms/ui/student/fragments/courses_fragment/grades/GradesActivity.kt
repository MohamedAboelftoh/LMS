package com.example.lms.ui.student.fragments.courses_fragment.grades

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.lms.databinding.ActivityGradesBinding
import com.example.lms.ui.api.api_student.course_tasks__grades.CourseTasksGradesResponseItem
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.db.DataBase
import com.example.lms.ui.student.checkForInternet
import com.example.lms.ui.student.fragments.courses_fragment.CourseContent
import com.example.lms.ui.student.fragments.Variables
import com.example.lms.ui.student.navigateFromActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GradesActivity : AppCompatActivity() {
    lateinit var viewBinding: ActivityGradesBinding
    lateinit var adapter: CourseGradesAdapter
    lateinit var myPreferencesToken: MyPreferencesToken

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityGradesBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        myPreferencesToken = MyPreferencesToken(this)
        adapter = CourseGradesAdapter()
        viewBinding.gradesTasksRecycler.adapter = adapter
        viewBinding.courseNameTv.text = Variables.courseName

        if (checkForInternet(this)) {
            getGradesFromApi()
        } else {
            getGradesFromLocal()
        }

        iconBackClick()
        //getGradesFromApi()
    }

    private fun getGradesFromLocal() {
        val gradesList = ArrayList(DataBase.getInstance(this).stuCourseGradesDao().getCourseGradesFromLocal())
        adapter.bindGrades(gradesList)
    }

    private fun cacheGradesInLocal(body: ArrayList<CourseTasksGradesResponseItem>?) {
        DataBase.getInstance(this).stuCourseGradesDao().deleteCourseGradesFromLocal()
        DataBase.getInstance(this).stuCourseGradesDao().insertCourseGrades(body!!)
    }

    private fun getGradesFromApi() {
        val token = myPreferencesToken.loadData("token")
        val courseId = Variables.cycleId

        ApiManager.getApi().getAllGradesForCurrentCourse(token, courseId).enqueue(object : Callback<ArrayList<CourseTasksGradesResponseItem>> {
            override fun onResponse(
                call: Call<ArrayList<CourseTasksGradesResponseItem>>,
                response: Response<ArrayList<CourseTasksGradesResponseItem>>
            ) {
                if (response.isSuccessful) {
                    cacheGradesInLocal(response.body())
                    adapter.bindGrades(response.body())
                } else {
                    Toast.makeText(this@GradesActivity, "Grades not downloaded", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ArrayList<CourseTasksGradesResponseItem>>, t: Throwable) {
                Toast.makeText(this@GradesActivity, "${t.localizedMessage}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun iconBackClick() {
        viewBinding.icBack.setOnClickListener {
            navigateFromActivity(this@GradesActivity, CourseContent())
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        navigateFromActivity(this@GradesActivity, CourseContent())
    }
}
