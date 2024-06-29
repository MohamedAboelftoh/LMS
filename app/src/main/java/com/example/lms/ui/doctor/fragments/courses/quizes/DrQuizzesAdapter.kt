package com.example.lms.ui.doctor.fragments.courses.quizes

import android.icu.text.SimpleDateFormat
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.lms.databinding.ItemDrAssignPendingBinding
import com.example.lms.databinding.QuizItemBinding
import com.example.lms.ui.api.api_doctor.dr_courses.quizzes.DrQuizzesResponseItem
import com.example.lms.ui.student.fragments.Variables
import java.util.ArrayList
import java.util.Locale

class DrQuizzesAdapter (private var quizzesList : List<DrQuizzesResponseItem?>?= null): Adapter<DrQuizzesAdapter.ViewHolder>()  {
    class ViewHolder(val viewBinding : ItemDrAssignPendingBinding) : RecyclerView.ViewHolder(viewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = ItemDrAssignPendingBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return quizzesList?.size ?: 0
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val quizItem = quizzesList?.get(position)
        holder.viewBinding.assignName.text = quizItem?.title
        holder.viewBinding.grades.text = quizItem?.grade.toString()

        holder.viewBinding.time.text = formatTime (quizItem?.startDate) + " To " +formatTime(quizItem?.endDate)
        holder.viewBinding.endTime.text="End: ${formatEndTime(quizItem?.endDate)}"

        holder.viewBinding.progressStartTv.text=quizItem?.numberOfStudentsSolve.toString()
        holder.viewBinding.progressEndTv.text=quizItem?.numberOfAllStudents.toString()
        holder.viewBinding.progressBar.max=quizItem?.numberOfAllStudents ?:0
        holder.viewBinding.progressBar.progress=quizItem?.numberOfStudentsSolve ?:0

        holder.viewBinding.icDelete.setOnClickListener{
            Variables.quizId=quizItem?.quizId
            onIconDeleteClickListener?.onClick(position,quizItem?.id)
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

    fun bindData(body: ArrayList<DrQuizzesResponseItem>?) {
        quizzesList = body
        notifyDataSetChanged()
    }

    var onIconDeleteClickListener:OnIconDeleteClickListener?=null
    fun interface OnIconDeleteClickListener{
        fun onClick(position:Int,quizId:String?)
    }
}