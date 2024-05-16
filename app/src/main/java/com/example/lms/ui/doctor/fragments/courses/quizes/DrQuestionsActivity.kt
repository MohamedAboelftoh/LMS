package com.example.lms.ui.doctor.fragments.courses.quizes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.lms.R
import com.example.lms.databinding.ActivityDrQuestionsBinding
import com.example.lms.ui.api.api_doctor.dr_courses.quizzes.DrQuestionsItem

class DrQuestionsActivity : AppCompatActivity() {
    lateinit var viewBinding:ActivityDrQuestionsBinding
     lateinit var questionsAdapter:DrQuestionsAdapter
     //var listOfQuestions = mutableListOf<DrQuestionsItem>()
     private val snapHelper : SnapHelper = LinearSnapHelper()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding=ActivityDrQuestionsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
         questionsAdapter= DrQuestionsAdapter()
        viewBinding.drQuestionsRecycler.adapter=questionsAdapter
         snapHelper.attachToRecyclerView(viewBinding.drQuestionsRecycler)
        addAnswer()
        removeAnswer()
        viewBinding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.ic_add ->{
//                    listOfQuestions.add(DrQuestionsItem())
//                    questionsAdapter.bindData(listOfQuestions)


                    questionsAdapter.addItem() // Add the new item to the adapter
                    questionsAdapter.updateList(questionsAdapter.questionsList!!) // Update the adapter's data and notify the RecyclerView
                    val itemCount = questionsAdapter.itemCount
                    viewBinding.drQuestionsRecycler.smoothScrollToPosition(itemCount - 1)

//                    val newItem = DrQuestionsItem()
//                    listOfQuestions .add(newItem)
//                    questionsAdapter.notifyItemInserted(listOfQuestions.size - 1)
//                    viewBinding.drQuestionsRecycler.smoothScrollToPosition(listOfQuestions.size - 1)
                }
            }
            true
        }
    }

    private fun addAnswer() {
        questionsAdapter.onButtonAddAnsClickListener=object :DrQuestionsAdapter.OnButtonAddAnsClickListener{
            override fun onClick(
                item: DrQuestionsItem,
                position: Int,
                holder: DrQuestionsAdapter.DrQuestionsViewHolder
            ) {
                super.onClick(item, position, holder)
                if(holder.itemBinding.linear3.visibility== View.GONE){
                    holder.itemBinding.linear3.visibility= View.VISIBLE

                }
                else  if(holder.itemBinding.linear4.visibility== View.GONE){
                    holder.itemBinding.linear4.visibility= View.VISIBLE

                }
                else{
                    //show a Toast
                    Toast.makeText(this@DrQuestionsActivity, "The largest number is 4"
                        , Toast.LENGTH_SHORT).show()

                }
            }
        }

    }

    private fun removeAnswer() {
        questionsAdapter.onButtonRemoveAnsClickListener=object :DrQuestionsAdapter.OnButtonRemoveAnsClickListener{
            override fun onClick(
                item: DrQuestionsItem,
                position: Int,
                holder: DrQuestionsAdapter.DrQuestionsViewHolder
            ) {super.onClick(item, position,holder)

            if(holder.itemBinding.linear4.visibility== View.VISIBLE){
                holder.itemBinding.linear4.visibility= View.GONE

            }
            else if(holder.itemBinding.linear3.visibility== View.VISIBLE){
                holder.itemBinding.linear3.visibility= View.GONE

            }
            else{
                //show a Toast
                Toast.makeText(this@DrQuestionsActivity, "The least number is 2"
                    , Toast.LENGTH_SHORT).show()

            }
            }
        }
    }
}