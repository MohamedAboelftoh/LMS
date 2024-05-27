package com.example.lms.ui.doctor.fragments.courses.quizes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.lms.databinding.ItemDrAssignPendingBinding
import com.example.lms.ui.api.api_doctor.dr_courses.material.DrLecturesResponseItem
import com.example.lms.ui.api.api_doctor.dr_courses.quizzes.DrQuizzesResponseItem
import com.example.lms.ui.student.fragments.Variables
import java.util.ArrayList

class DrQuizzesAdapter (private var quizzesList : List<DrQuizzesResponseItem?>?= null): Adapter<DrQuizzesAdapter.ViewHolder>()  {
    class ViewHolder(val viewBinding : ItemDrAssignPendingBinding) : RecyclerView.ViewHolder(viewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = ItemDrAssignPendingBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return quizzesList?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val quizItem = quizzesList?.get(position)
        holder.viewBinding.assignName.text = quizItem?.title
        holder.viewBinding.courseName.text = Variables.courseName
        holder.viewBinding.points.text = quizItem?.grade.toString()
        holder.viewBinding.time.text = quizItem?.startDate + " To " + quizItem?.endDate
        holder.viewBinding.icDelete.setOnClickListener{
            Variables.quizId=quizItem?.quizId
            onIconDeleteClickListener?.onClick(position,quizItem?.id)
        }
    }

    fun bindData(body: ArrayList<DrQuizzesResponseItem>?) {
        quizzesList = body
        notifyDataSetChanged()
    }

    var onIconDeleteClickListener:OnIconDeleteClickListener?=null
    fun interface OnIconDeleteClickListener{
        fun onClick(position:Int,quizId:String?)
    }
}