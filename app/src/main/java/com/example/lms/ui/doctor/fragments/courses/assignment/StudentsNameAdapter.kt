package com.example.lms.ui.doctor.fragments.courses.assignment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.lms.databinding.ItemStudentNameBinding
import com.example.lms.ui.api.api_doctor.dr_courses.assignments.StudentsUploadedTheTaskResponseItem

class StudentsNameAdapter(private var studentsList:ArrayList<StudentsUploadedTheTaskResponseItem>?=null,var btnVisibility:Int?=null
):Adapter<StudentsNameAdapter.StudentsNameViewHolder>() {
    class StudentsNameViewHolder(val viewBinding:ItemStudentNameBinding):ViewHolder(viewBinding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentsNameViewHolder {

        val viewBinding= ItemStudentNameBinding.inflate(
            LayoutInflater.from(parent.context)
            ,parent,false)
        return StudentsNameViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return studentsList?.size ?: 0
    }

    override fun onBindViewHolder(holder: StudentsNameViewHolder, position: Int) {
        val student = studentsList!![position]
        holder.viewBinding.studentName.text=". ${student.studentName}"
        holder.viewBinding.studentNumber.text="${position+1}"
        holder.viewBinding.btnMore.visibility= btnVisibility!!
        holder.viewBinding.btnMore.setOnClickListener{
            onBtnMoreClickListener?.btnMoreClickListener(student,position)
        }

    }

    fun bindStudentsList(StudentsList2: ArrayList<StudentsUploadedTheTaskResponseItem>?) {
        studentsList=StudentsList2
        notifyDataSetChanged()
    }
    var onBtnMoreClickListener: OnBtnMoreClickListener?=null

    interface OnBtnMoreClickListener{
        fun btnMoreClickListener(item: StudentsUploadedTheTaskResponseItem, position:Int)
    }
}