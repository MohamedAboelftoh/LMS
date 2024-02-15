package com.example.lms.ui.home.fragments.courses_fragment.quizzes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.lms.databinding.CourseItemBinding
import com.example.lms.databinding.QuizItemBinding
import com.example.lms.ui.home.fragments.courses_fragment.CourseItem
import com.example.lms.ui.home.fragments.courses_fragment.CoursesAdapter

class QuizzesAdapter(private var quizzesList:MutableList<QuizItem>?):Adapter<QuizzesAdapter.QuizzesViewHolder> (){
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
        holder.viewBinding.courseName.text = quizItem.courseName
        holder.viewBinding.startTime.text = quizItem.startTime
        holder.viewBinding.endTime.text = quizItem.endTime
        holder.viewBinding.btnStart.setOnClickListener {
            onBtnStartClickListener?.onClick(position,quizItem)
        }
    }
    var onBtnStartClickListener: OnBtnStartClickListener?=null
    interface OnBtnStartClickListener{
        fun onClick(position: Int,item: QuizItem)
    }

}