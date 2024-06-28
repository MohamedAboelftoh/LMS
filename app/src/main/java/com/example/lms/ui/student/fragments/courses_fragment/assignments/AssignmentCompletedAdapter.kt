package com.example.lms.ui.student.fragments.courses_fragment.assignments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.lms.databinding.AssignmentCompletedItemBinding
import com.example.lms.ui.api.api_student.assignments.AssignmentResponseItem
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar

class AssignmentCompletedAdapter (private var assignmentsList:MutableList<AssignmentResponseItem>?=null):Adapter<AssignmentCompletedAdapter.AssignmentViewHolder>(){
    class AssignmentViewHolder(val viewBinding: AssignmentCompletedItemBinding): RecyclerView.ViewHolder(viewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AssignmentViewHolder {
        val viewBinding= AssignmentCompletedItemBinding.inflate(
            LayoutInflater.from(parent.context)
            ,parent,false)
        return AssignmentViewHolder(viewBinding)
    }
    //.........................................................................
    override fun getItemCount(): Int {
       return assignmentsList?.size ?: 0
    }

    override fun onBindViewHolder(holder: AssignmentViewHolder, position: Int) {
        val assignmentItem = assignmentsList!![position]
        holder.viewBinding.courseNameTv.text = assignmentItem?.taskName
        holder.viewBinding.deadline.text = formatDate(assignmentItem?.endDate)
    }
//    fun bindAssignments(newAssignmentList: MutableList<AssignmentResponseItem>?) {
//        assignmentsList = newAssignmentList
//        notifyDataSetChanged()
//    }

    fun bindAssignments(newAssignmentList: List<AssignmentResponseItem>) {
        val assignCompletedList: MutableList<AssignmentResponseItem> = mutableListOf()
        val currentDate = Calendar.getInstance().time

        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        for (assignment in newAssignmentList) {
            val endDateString = assignment.endDate
            val endDate = dateFormat.parse(endDateString)
            if (endDate != null && currentDate.after(endDate)) {
                assignCompletedList.add(assignment)
            }
        }
        assignmentsList = assignCompletedList
        notifyDataSetChanged()
    }
    private fun formatDate(createdAt: String?) : String? {
        val endDate = createdAt?.split("T")
        val date = endDate?.get(0)
        return date
    }

}