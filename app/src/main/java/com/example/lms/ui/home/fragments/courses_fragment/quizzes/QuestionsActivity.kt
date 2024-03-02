package com.example.lms.ui.home.fragments.courses_fragment.quizzes

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.lms.R
import com.example.lms.databinding.ActivityQuestionsBinding
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.api.quizes.QuestionsItem
import com.example.lms.ui.api.quizes.QuizQuestionsResponse
import com.example.lms.ui.api.quizes.submit.AnswersItem
import com.example.lms.ui.api.quizes.submit.SubmitQuizResponse
import com.example.lms.ui.api.quizes.submit.SubmitQuizRequest
import com.example.lms.ui.home.fragments.courses_fragment.material.Variables
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuestionsActivity : AppCompatActivity() {
    lateinit var viewBinding : ActivityQuestionsBinding
    lateinit var myPreferencesToken: MyPreferencesToken
    lateinit var questionsAdapter:QuestionsAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityQuestionsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        myPreferencesToken= MyPreferencesToken(this)
        questionsAdapter= QuestionsAdapter()
        viewBinding.questionsRecycler.adapter=questionsAdapter
        getQuestions()

        val snapHelper : SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(viewBinding.questionsRecycler)
        onButtonNextClick()
        onRadioBtnSelected()
        iconBackClick()
    }
    private fun iconBackClick(){
        viewBinding.imgBack.setOnClickListener {
            val layoutManager = viewBinding.questionsRecycler.layoutManager as LinearLayoutManager
            val currentPosition = layoutManager.findFirstVisibleItemPosition()
            if (currentPosition != RecyclerView.NO_POSITION && currentPosition > 0) {
                viewBinding.questionsRecycler.smoothScrollToPosition(currentPosition - 1)
                }
           }
    }


    fun getQuestions(){
        val token =myPreferencesToken.loadData("token")
        val quizId=Variables.quizId
        ApiManager.getApi().getQuizQuestions(token!!,quizId!!).enqueue(object :Callback<QuizQuestionsResponse>{
            override fun onResponse(
                call: Call<QuizQuestionsResponse>,
                response: Response<QuizQuestionsResponse>
            ) {
                if(response.isSuccessful){
                    questionsAdapter.bindQuestions(response.body()?.questions)
                    viewBinding.tvQuiz.text=response.body()?.title

                }
                else{
                    Toast.makeText(this@QuestionsActivity, "failed to get the Questions", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<QuizQuestionsResponse>, t: Throwable) {
                Toast.makeText(this@QuestionsActivity, "onFailure " + t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }


fun onButtonNextClick(){
    questionsAdapter.onNextClickListener=QuestionsAdapter.OnNextClickListener{position, item ->
        if(position==questionsAdapter.itemCountValue-1){
            val submitQuiz = SubmitQuizRequest(Variables.quizId,ans)
            val token = myPreferencesToken.loadData("token")
            ApiManager.getApi().submitQuiz(submitQuiz,Variables.quizId!!,token!!).enqueue(object
                :Callback<SubmitQuizResponse>{
                override fun onResponse(
                    call: Call<SubmitQuizResponse>,
                    response: Response<SubmitQuizResponse>
                ) {
                    if (response.isSuccessful){
                        showMessage("q1 : ${response.body()?.q0011}\n" +
                                "q2 : ${response.body()?.q0021}\n" +
                                "q3 : ${response.body()?.q0031}\n"+
                                "q4 : ${response.body()?.q0041}"
                            ,posActionName = "OK",
                            posAction = { dialogInterface,i->
                                dialogInterface.dismiss()
                                val intent=Intent(this@QuestionsActivity,FinishActivity::class.java)
                                startActivity(intent)
                            },

                            negActionName = "Cansel"
                            , negAction = { dialogInterface, i ->
                                dialogInterface.dismiss()

                            }
                        )
                    }
                    else{
                        Toast.makeText(this@QuestionsActivity, "error ", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<SubmitQuizResponse>, t: Throwable) {
                    Toast.makeText(this@QuestionsActivity, t.localizedMessage, Toast.LENGTH_SHORT).show()
                }

            })
            
        }else{
            questionsAdapter.incrementItemCount()
            // Scroll the RecyclerView one step forward
            viewBinding.questionsRecycler.smoothScrollToPosition(position + 1)
        }
    }
}
    val ans : ArrayList<AnswersItem> = arrayListOf()
    var answer :String?=null
    fun onRadioBtnSelected(){
        questionsAdapter.onRadioButtonSelect=object :QuestionsAdapter.OnRadioButtonSelect{
            override fun onRadioButtonClicked(
                position: Int,
                item: QuestionsItem?,
                checkedRadioButtonId: Int
            ) {
                when(checkedRadioButtonId){
                    R.id.radio_1 ->{
                        answer = "Q001_1_A1"
                    }
                    R.id.radio_2 -> {
                        answer = "Q002_1_A1"
                    }
                    R.id.radio_3 -> {
                        answer = "Q003_1_A1"
                    }
                    R.id.radio_4 -> {
                        answer = "Q004_1_A1"
                    }
                }
                ans.add(AnswersItem(answer,item?.id))
            }
        }
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