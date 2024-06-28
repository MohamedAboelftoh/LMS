package com.example.lms.ui.student.fragments.courses_fragment.assignments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.lms.databinding.AssignmentPendingItemBinding
import com.example.lms.ui.api.api_student.assignments.AssignmentResponseItem
import java.text.SimpleDateFormat
import java.util.Calendar
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
fun bindAssignments(newAssignmentList: List<AssignmentResponseItem>) {
    val assignPendingList: MutableList<AssignmentResponseItem> = mutableListOf()
    val currentDate = Calendar.getInstance().time

    if (newAssignmentList != null) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        for (assignment in newAssignmentList) {
            val endDateString = assignment.endDate
            val startDateString = assignment.startDate

            val endDate = dateFormat.parse(endDateString)
            val startDate = dateFormat.parse(startDateString)
//if you run this check you will validate true by start and end date

            //if (endDate != null && currentDate.after(startDate)&& currentDate.before(endDate)) {
            if (endDate != null && currentDate.after(endDate)) {

                assignPendingList.add(assignment)
            }
        }
    }
    assignmentsList = assignPendingList
    notifyDataSetChanged()
}

    var onBtnMoreClickListener: OnBtnMoreClickListener?=null
    interface OnBtnMoreClickListener{
        fun onClick(position: Int,item: AssignmentResponseItem)
    }

}