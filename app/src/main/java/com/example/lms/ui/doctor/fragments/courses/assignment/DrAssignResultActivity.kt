package com.example.lms.ui.doctor.fragments.courses.assignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.example.lms.databinding.ActivityAssignResultBinding
import com.example.lms.ui.api.api_doctor.dr_courses.assignments.StudentsUploadedTheTaskResponseItem
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.student.fragments.Variables
import com.example.lms.ui.student.navigateFromActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DrAssignResultActivity : AppCompatActivity() {
    lateinit var viewBinding: ActivityAssignResultBinding
    lateinit var myPreferencesToken: MyPreferencesToken
    val studentAssignDetailsFragment=StudentAssignDetailsFragment()

    lateinit var adapter: StudentsNameAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding=ActivityAssignResultBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        myPreferencesToken= MyPreferencesToken(this)
        adapter= StudentsNameAdapter(btnVisibility = View.VISIBLE)
        viewBinding.stuCompletedRecycler.adapter=adapter
        getStudents()
        viewBinding.courseNameTv.text= Variables.courseName
        viewBinding.icBack.setOnClickListener{
            navigateFromActivity(this@DrAssignResultActivity,DrAssignActivity())
        }
        showStudentTaskDetails()
    }

    private fun showStudentTaskDetails() {
        adapter.onBtnMoreClickListener=object :StudentsNameAdapter.OnBtnMoreClickListener{
            override fun btnMoreClickListener(
                item: StudentsUploadedTheTaskResponseItem,
                position: Int
            ) {
                Variables.studentName=item.studentName
                Variables.stuTimeUploaded=item.timeUploaded
                Variables.filePath=item.filePath
                studentAssignDetailsFragment.show(supportFragmentManager,"")            }
        }
    }
    private fun getStudents() {
        val token=myPreferencesToken.loadData("token")
        val taskId=Variables.taskId
        ApiManager.getApi().drGetStudentsWhoUploadTheTas(token!!,taskId!!).enqueue(object
            : Callback<ArrayList<StudentsUploadedTheTaskResponseItem>> {
            override fun onResponse(
                call: Call<ArrayList<StudentsUploadedTheTaskResponseItem>>,
                response: Response<ArrayList<StudentsUploadedTheTaskResponseItem>>
            ) {
                if(response.isSuccessful){
                    adapter.bindStudentsList(response.body())
                    Toast.makeText(this@DrAssignResultActivity,"successful", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this@DrAssignResultActivity,"failed to get Students Name", Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(
                call: Call<ArrayList<StudentsUploadedTheTaskResponseItem>>,
                t: Throwable
            ) {
                Toast.makeText(this@DrAssignResultActivity,"${t.localizedMessage}", Toast.LENGTH_SHORT).show()

            }
        })
    }

}