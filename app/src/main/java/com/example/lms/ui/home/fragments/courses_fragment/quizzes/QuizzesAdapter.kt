package com.example.lms.ui.home.fragments.courses_fragment.quizzes

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.lms.R
import com.example.lms.databinding.QuizItemBinding
import com.example.lms.ui.api.quizes.CourseQuizzesResponseItem

class QuizzesAdapter(private var quizzesList:List<CourseQuizzesResponseItem?>?=null):Adapter<QuizzesAdapter.QuizzesViewHolder> (){
    class QuizzesViewHolder(val viewBinding: QuizItemBinding): RecyclerView.ViewHolder(viewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizzesViewHolder {
        val viewBinding=QuizItemBinding.inflate(
            LayoutInflater.from(parent.context)
            ,parent,false)
        return QuizzesViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
       return quizzesList?.size ?: 0
    }

    override fun onBindViewHolder(holder: QuizzesViewHolder, position: Int) {
        val quizItem = quizzesList!![position]
        holder.viewBinding.courseName.text = quizItem?.title
        holder.viewBinding.startTime.text = formatTStartTime(quizItem?.startDate)
        holder.viewBinding.endTime.text = formatEndTime(quizItem?.endDate)
        buttonStartAvailability(quizItem, holder, position)
    }

    private fun buttonStartAvailability(
        quizItem: CourseQuizzesResponseItem?,
        holder: QuizzesViewHolder,
        position: Int
    ) {
        if (quizItem?.status == "Not Available") {
            holder.viewBinding.btnStart.backgroundTintList = ColorStateList.valueOf(
                ContextCompat
                    .getColor(holder.itemView.context, R.color.greenColor)
            )
            holder.viewBinding.btnStart.setOnClickListener {
                onBtnStartClickListener?.onClick(position, quizItem)
            }
        } else {
            holder.viewBinding.btnStart.backgroundTintList = ColorStateList.valueOf(
                ContextCompat
                    .getColor(holder.itemView.context, R.color.colorPrimary)
            )
        }
    }

    private fun formatTStartTime(startDate: String?) :String{
        //Formatted Start Time                                      2024-02-01T00:00:00
        val timeOnly = startDate?.split("T")?.get(1)?.split(":")
        val formattedStartTime = timeOnly?.subList(0, 2)?.joinToString(":")
        return formattedStartTime!!
    }
    private fun formatEndTime(endDate: String?) :String{
        //Formatted End Time
        val timeOnly = endDate?.split("T")?.get(1)?.split(":")
        val formattedEndTime = timeOnly?.subList(0, 2)?.joinToString(":")
        return formattedEndTime!!
    }

    fun bindQuizzes(body: List<CourseQuizzesResponseItem>?) {
        quizzesList=body
        notifyDataSetChanged()
    }

    var onBtnStartClickListener: OnBtnStartClickListener?=null
    interface OnBtnStartClickListener{
        fun onClick(position: Int, item: CourseQuizzesResponseItem)
    }

}