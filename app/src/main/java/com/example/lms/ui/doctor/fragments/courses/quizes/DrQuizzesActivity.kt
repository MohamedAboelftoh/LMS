package com.example.lms.ui.doctor.fragments.courses.quizes

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.lms.databinding.ActivityDrQuizesBinding
import com.example.lms.ui.api.api_doctor.dr_courses.quizzes.DrQuizzesResponseItem
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.student.fragments.Variables
import com.example.lms.ui.student.navigateFromActivity
import okhttp3.ResponseBody
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
        deleteQuizAfterPressOk()
        adapter.onBtnAvailableClickListener =
            DrQuizzesAdapter.OnIconDeleteClickListener { position, quizId ->
                navigateFromActivity(this@DrQuizzesActivity , DrQuestionsOfQuizActivity()) }
    }

    fun deleteQuizAfterPressOk(){
        val token=myPreferencesToken.loadData("token")
        adapter.onIconDeleteClickListener=
        DrQuizzesAdapter.OnIconDeleteClickListener { position, quizId ->

            showMessage("Do you Sure To Delete ", posActionName = "OK",
                posAction = { dialogInterface, i ->
                    dialogInterface.dismiss()
                    deleteQuiz(token!!,quizId!!)
                },

                negActionName = "Cansel", negAction = { dialogInterface, i ->
                    dialogInterface.dismiss()

                }
            )
        }
    }
    private fun deleteQuiz(token:String,quizId:String) {
        ApiManager.getApi().DrDeleteQuiz(quizId!!,token!!).enqueue(object :Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful){
                    getAllQuizzesOfCourse()
                    Toast.makeText(this@DrQuizzesActivity, "Task Deleted Successful", Toast.LENGTH_LONG).show()
                } else{
                    Toast.makeText(this@DrQuizzesActivity, "Error in Delete", Toast.LENGTH_LONG).show()

                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@DrQuizzesActivity, "onFailure "+t.localizedMessage, Toast.LENGTH_LONG).show()
            }
        })


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



    fun showMessage(message:String
                    ,posActionName:String?=null
                    ,posAction: DialogInterface.OnClickListener?=null
                    ,negActionName:String?=null
                    ,negAction: DialogInterface.OnClickListener?=null

    ): AlertDialog {
        val dialogBuilder= AlertDialog.Builder(this)
        dialogBuilder.setMessage(message)
        if (posActionName!=null)
        {
            dialogBuilder.setPositiveButton(posActionName,posAction)
        }
        if(negActionName!=null)
        {
            dialogBuilder.setNegativeButton(negActionName,negAction)

        }
        return dialogBuilder.show()
    }
}