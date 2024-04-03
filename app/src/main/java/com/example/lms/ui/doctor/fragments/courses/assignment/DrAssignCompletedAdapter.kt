package com.example.lms.ui.doctor.fragments.courses.assignment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.lms.databinding.AssignmentCompletedItemBinding
import com.example.lms.databinding.ItemDrAssignCompletedBinding
import com.example.lms.ui.api.api_doctor.dr_courses.assignments.DrAllAssignmentsResponseItem
import com.example.lms.ui.student.fragments.Variables

class DrAssignCompletedAdapter(private var drAssignmentsList:ArrayList<DrAllAssignmentsResponseItem>?=null):Adapter<DrAssignCompletedAdapter.DrAssignCompletedViewHolder>() {
    class DrAssignCompletedViewHolder(val viewBinding :ItemDrAssignCompletedBinding): ViewHolder(viewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrAssignCompletedViewHolder {
        val viewBinding= ItemDrAssignCompletedBinding.inflate(
            LayoutInflater.from(parent.context)
            ,parent,false)
        return DrAssignCompletedViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
       return drAssignmentsList?.size ?: 0
    }

    override fun onBindViewHolder(holder: DrAssignCompletedViewHolder, position: Int) {
        val assignmentItem = drAssignmentsList!![position]
        holder.viewBinding.assignName.text = assignmentItem?.taskName
        holder.viewBinding.time.text = assignmentItem?.endDate
        holder.viewBinding.courseName.text = Variables.courseName


    }

    fun bindAssignments(assignmentList: ArrayList<DrAllAssignmentsResponseItem>?) {
        drAssignmentsList=assignmentList
        notifyDataSetChanged()
    }
}