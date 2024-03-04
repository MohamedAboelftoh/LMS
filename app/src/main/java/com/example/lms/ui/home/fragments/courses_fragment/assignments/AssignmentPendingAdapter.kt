package com.example.lms.ui.home.fragments.courses_fragment.assignments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.lms.databinding.AssignmentPendingItemBinding
import com.example.lms.ui.api.assignments.AssignmentResponseItem
import java.text.SimpleDateFormat
import java.util.Locale

class AssignmentPendingAdapter (private var assignmentsList:MutableList<AssignmentResponseItem>?=null):Adapter<AssignmentPendingAdapter.AssignmentViewHolder>(){
    class AssignmentViewHolder(val viewBinding: AssignmentPendingItemBinding): RecyclerView.ViewHolder(viewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssignmentViewHolder {
        val viewBinding= AssignmentPendingItemBinding.inflate(
            LayoutInflater.from(parent.context)
            ,parent,false)
        return AssignmentViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
       return assignmentsList?.size ?: 0
    }

    override fun onBindViewHolder(holder: AssignmentViewHolder, position: Int) {
        val assignmentItem = assignmentsList!![position]
        holder.viewBinding.courseNameTv.text = assignmentItem.taskName
        holder.viewBinding.deadline.text = formatDate(assignmentItem.endDate)
        holder.viewBinding.btnMore.setOnClickListener {
            onBtnMoreClickListener?.onClick(position, assignmentItem)
        }
    }

    private fun formatDate(createdAt: String?) : String? {
        val endDate = createdAt?.split("T")
        val date = endDate?.get(0)
        return date
    }

    fun bindAssignments(newAssignmentList: MutableList<AssignmentResponseItem>?) {
        assignmentsList = newAssignmentList
        notifyDataSetChanged()
    }

    var onBtnMoreClickListener: OnBtnMoreClickListener?=null
    interface OnBtnMoreClickListener{
        fun onClick(position: Int,item: AssignmentResponseItem)
    }

}