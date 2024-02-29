package com.example.lms.ui.home.fragments.courses_fragment.quizzes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lms.databinding.FinishItemBinding
import com.example.lms.databinding.QuestionItemBinding
import com.example.lms.ui.api.courses.CoursesResponseItem
import com.example.lms.ui.api.quizes.QuestionsItem

class QuestionsAdapter (var questionsList:List<QuestionsItem?>?=null):RecyclerView.Adapter<QuestionsAdapter.QuestionsViewHolder>(){
    class QuestionsViewHolder(val itemBinding: QuestionItemBinding):RecyclerView.ViewHolder(itemBinding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionsViewHolder {
          val itemBinding = QuestionItemBinding.inflate(
              LayoutInflater.from(parent.context), parent, false
          )
         return QuestionsViewHolder(itemBinding)
    }


    override fun onBindViewHolder(holder: QuestionsViewHolder, position: Int) {
        val item=questionsList!![position]

        val answers=item?.answers
        holder.itemBinding.question.text=item?.text
        holder.itemBinding.tvQuestionNumber.text=item?.questionNumber.toString()
        holder.itemBinding.nextBtn.setOnClickListener { onNextClickListener?.onItemClick(position,item) }
        holder.itemBinding.radio1.text=answers?.get(0)?.text
        holder.itemBinding.radio2.text=answers?.get(1)?.text
        holder.itemBinding.radio3.text=answers?.get(2)?.text
        holder.itemBinding.radio4.text=answers?.get(3)?.text


    }
    var itemCountValue: Int = 0

    override fun getItemCount(): Int {
        itemCountValue = questionsList?.size ?: 0

        return itemCountValue
    }
    fun incrementItemCount() {
        itemCountValue++
        notifyItemInserted(itemCountValue - 1)
    }


    fun bindQuestions(questions: ArrayList<QuestionsItem?>?) {
        questionsList=questions
        notifyDataSetChanged()
    }

    var onNextClickListener:OnNextClickListener?=null

    fun interface OnNextClickListener{
        fun onItemClick(position:Int,item: QuestionsItem?)
    }


}