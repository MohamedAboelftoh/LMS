package com.example.lms.ui.doctor.fragments.courses.assignment

import android.icu.text.SimpleDateFormat
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.lms.databinding.ItemDrAssignPendingBinding
import com.example.lms.ui.api.api_doctor.dr_courses.assignments.DrAllAssignmentsResponseItem
import com.example.lms.ui.student.fragments.Variables
import java.util.Locale

class DrAssignPendingAdapter (private var drAssignmentsList:ArrayList<DrAllAssignmentsResponseItem>?=null):RecyclerView.Adapter<DrAssignPendingAdapter.DrAssignPendingViewHolder>(){
    private var numberOfAllStudents:Int=100

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

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: DrAssignPendingViewHolder, position: Int) {
        val assignmentItem = drAssignmentsList!![position]
        holder.viewBinding.assignName.text = assignmentItem.taskName
        holder.viewBinding.time.text=formatTime (assignmentItem?.startDate) + " To " +formatTime(assignmentItem?.endDate)
        holder.viewBinding.endTime.text = "End: ${formatEndTime(assignmentItem.endDate)}"
        holder.viewBinding.progressStartTv.text=assignmentItem.numberOfStudentsUploads.toString()
        holder.viewBinding.progressEndTv.text=assignmentItem.numberOfAllStudents.toString()
        holder.viewBinding.grades.text="${assignmentItem.grade} Grades"
        holder.viewBinding.progressBar.max=assignmentItem.numberOfAllStudents ?:0
        holder.viewBinding.progressBar.progress=assignmentItem.numberOfStudentsUploads ?:0


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
    @RequiresApi(Build.VERSION_CODES.N)
    private fun formatTime(dateStr: String?): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val timeFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return if (dateStr != null) {
            val date: java.util.Date? = dateFormat.parse(dateStr)
            timeFormat.format(date)
        } else {
            ""
        }
    }
    @RequiresApi(Build.VERSION_CODES.N)
    private fun formatEndTime(dateStr: String?): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
        val timeFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm", Locale.getDefault())
        return if (dateStr != null) {
            val date: java.util.Date? = dateFormat.parse(dateStr)
            timeFormat.format(date)
        } else {
            ""
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