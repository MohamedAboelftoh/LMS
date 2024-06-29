package com.example.lms.ui.doctor.fragments.courses.grades

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lms.databinding.ItemStudentNameBinding
import com.example.lms.ui.api.api_doctor.grades.AllStuEnrolledInCourseItem

class DrAllStudentsGradesAdapter(private var studentsList:ArrayList<AllStuEnrolledInCourseItem>?=null):
    RecyclerView.Adapter<DrAllStudentsGradesAdapter.AllStudentsNameViewHolder>() {

    class AllStudentsNameViewHolder(val viewBinding: ItemStudentNameBinding): RecyclerView.ViewHolder(viewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllStudentsNameViewHolder {
        val viewBinding= ItemStudentNameBinding.inflate(
            LayoutInflater.from(parent.context)
            ,parent,false)
        return AllStudentsNameViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return studentsList?.size ?: 0
    }

    override fun onBindViewHolder(holder: AllStudentsNameViewHolder, position: Int) {
        val student = studentsList!![position]
        holder.viewBinding.studentName.text=". ${student.studentName}"
        holder.viewBinding.studentNumber.text="${position+1}"
        holder.viewBinding.btnMore.setOnClickListener{
            onBtnMoreClickListener?.btnMoreClickListener(student,position)
        }
    }

    fun bindStudentsList(body: ArrayList<AllStuEnrolledInCourseItem>?) {
        studentsList=body
        notifyDataSetChanged()
    }

    var onBtnMoreClickListener: OnBtnMoreClickListener?=null
    interface OnBtnMoreClickListener{
        fun btnMoreClickListener(item: AllStuEnrolledInCourseItem, position:Int)
    }
}