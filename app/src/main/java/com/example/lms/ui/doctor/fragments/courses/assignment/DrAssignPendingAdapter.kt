package com.example.lms.ui.doctor.fragments.courses.assignment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lms.databinding.AssignmentPendingItemBinding
import com.example.lms.databinding.ItemDrAssignPendingBinding
import com.example.lms.ui.api.api_doctor.dr_courses.assignments.DrAllAssignmentsResponseItem
import com.example.lms.ui.student.fragments.Variables

class DrAssignPendingAdapter (private var drAssignmentsList:ArrayList<DrAllAssignmentsResponseItem>?=null):RecyclerView.Adapter<DrAssignPendingAdapter.DrAssignPendingViewHolder>(){

    class DrAssignPendingViewHolder(val viewBinding:ItemDrAssignPendingBinding):RecyclerView.ViewHolder(viewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrAssignPendingViewHolder {
        val viewBinding= ItemDrAssignPendingBinding.inflate(
            LayoutInflater.from(parent.context)
            ,parent,false)
        return DrAssignPendingViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return drAssignmentsList?.size ?: 0
    }

    override fun onBindViewHolder(holder: DrAssignPendingViewHolder, position: Int) {
        val assignmentItem = drAssignmentsList!![position]
        holder.viewBinding.assignName.text = assignmentItem.taskName
        holder.viewBinding.time.text = assignmentItem.endDate
            holder.viewBinding.courseName.text = Variables.courseName

        holder.viewBinding.btnAvailable.setOnClickListener{
            onButtonMoreClickListener?.buttonMoreClick(assignmentItem,position)
        }
        holder.viewBinding.icSee.setOnClickListener{
            onIconSeeClickListener?.iconSeeClick(assignmentItem,position)
        }
        holder.viewBinding.icEdit.setOnClickListener{
            onIconEditClickListener?.iconEditClick(assignmentItem,position)
        }
        holder.viewBinding.icDelete.setOnClickListener{
            onIconDeleteClickListener?.iconDeleteClick(assignmentItem,position)
        }
    }

    fun bindAssignments(assignmentsList: ArrayList<DrAllAssignmentsResponseItem>?) {
        drAssignmentsList=assignmentsList
        notifyDataSetChanged()
    }

    var onButtonMoreClickListener:OnButtonMoreClickListener?=null
    var onIconSeeClickListener:OnIconSeeClickListener?=null
    var onIconEditClickListener:OnIconEditClickListener?=null
    var onIconDeleteClickListener:OnIconDeleteClickListener?=null
    interface OnButtonMoreClickListener{
        fun buttonMoreClick(item:DrAllAssignmentsResponseItem,position:Int)
    }

    interface OnIconSeeClickListener{
        fun iconSeeClick(item:DrAllAssignmentsResponseItem,position:Int)
    }
    interface OnIconEditClickListener{
        fun iconEditClick(item:DrAllAssignmentsResponseItem,position:Int)
    }
    interface OnIconDeleteClickListener{
        fun iconDeleteClick(item:DrAllAssignmentsResponseItem,position:Int)
    }
}