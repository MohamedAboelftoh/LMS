package com.example.lms.ui.doctor.fragments.courses.assignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.lms.R
import com.example.lms.databinding.ActivityDrAssignPendingDetailsBinding
import com.example.lms.databinding.FragmentDrAssignmentPendingBinding
import com.example.lms.ui.api.api_doctor.dr_courses.assignments.DrAllAssignmentsResponseItem
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.student.fragments.Variables
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DrAssignPendingDetailsActivity : AppCompatActivity() {
        lateinit var viewBinding:ActivityDrAssignPendingDetailsBinding
        val editAssignmentFragment=EditAssignmentFragment()
    lateinit var myPreferencesToken: MyPreferencesToken
    val studentsNameFragment=StudentNamesFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding=ActivityDrAssignPendingDetailsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
       myPreferencesToken= MyPreferencesToken(this)
        viewBinding.btnEdit.setOnClickListener{
            editAssignmentFragment.show(supportFragmentManager,"")
        }
        viewBinding.number.setOnClickListener{
            studentsNameFragment.show(supportFragmentManager,"")

        }
        viewBinding.courseNameTv.text=Variables.courseName
       getAssignments()

        viewBinding.btnGo.setOnClickListener{
            viewBinding.constraintTaskData.visibility= View.GONE
        }
        viewBinding.btnBack.setOnClickListener{
            viewBinding.constraintTaskData.visibility= View.VISIBLE
        }
    }


    fun getAssignments(){
        val token = myPreferencesToken.loadData("token")
        val cycleId = Variables.cycleId
        ApiManager.getApi().drGetAllAssignment(token!!, cycleId!!).enqueue(object :
            Callback<ArrayList<DrAllAssignmentsResponseItem>> {
            override fun onResponse(
                call: Call<ArrayList<DrAllAssignmentsResponseItem>>,
                response: Response<ArrayList<DrAllAssignmentsResponseItem>>
            ) {
                if (response.isSuccessful) {
                    val position = intent.getIntExtra("position", -1) // Default value -1 if "position" extra is not found
                    val tasksList = response.body()

                    if (position != -1 && tasksList != null && position < tasksList.size) {
                        val taskItem = tasksList[position]
                        viewBinding.assignmentNameTv.text = taskItem.taskName
                        viewBinding.nameTv.text = taskItem.taskName
                        viewBinding.deadlineTv.text = taskItem.endDate
                        viewBinding.number.text="+"+Variables.studentsNumber.toString()
                    } else {
                        Toast.makeText(
                            this@DrAssignPendingDetailsActivity,
                            "Invalid position or empty tasks list",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@DrAssignPendingDetailsActivity,
                        "Failed to get assignments",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            override fun onFailure(
                call: Call<ArrayList<DrAllAssignmentsResponseItem>>,
                t: Throwable
            ) {
                Toast.makeText(
                    this@DrAssignPendingDetailsActivity,
                    "${t.localizedMessage}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

}