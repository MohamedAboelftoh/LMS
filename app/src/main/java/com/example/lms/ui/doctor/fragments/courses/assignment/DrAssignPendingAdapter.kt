package com.example.lms.ui.doctor.fragments.courses.assignment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lms.databinding.AssignmentPendingItemBinding
import com.example.lms.ui.api.api_doctor.dr_courses.assignments.DrAllAssignmentsResponseItem

class DrAssignPendingAdapter (private var drAssignmentsList:ArrayList<DrAllAssignmentsResponseItem>?=null):RecyclerView.Adapter<DrAssignPendingAdapter.DrAssignPendingViewHolder>(){

    class DrAssignPendingViewHolder(val viewBinding:AssignmentPendingItemBinding):RecyclerView.ViewHolder(viewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrAssignPendingViewHolder {
        val viewBinding= AssignmentPendingItemBinding.inflate(
            LayoutInflater.from(parent.context)
            ,parent,false)
        return DrAssignPendingViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return drAssignmentsList?.size ?: 0
    }

    override fun onBindViewHolder(holder: DrAssignPendingViewHolder, position: Int) {
        val assignmentItem = drAssignmentsList!![position]
        holder.viewBinding.courseNameTv.text = assignmentItem.taskName
        holder.viewBinding.deadline.text = assignmentItem.endDate
        holder.viewBinding.btnMore.setOnClickListener{
            onButtonMoreClickListener?.buttonMoreClick(assignmentItem,position)
        }
    }

    fun bindAssignments(assignmentsList: ArrayList<DrAllAssignmentsResponseItem>?) {
        drAssignmentsList=assignmentsList
        notifyDataSetChanged()
    }

    var onButtonMoreClickListener:OnButtonMoreClickListener?=null
    interface OnButtonMoreClickListener{
        fun buttonMoreClick(item:DrAllAssignmentsResponseItem,position:Int)
    }
}