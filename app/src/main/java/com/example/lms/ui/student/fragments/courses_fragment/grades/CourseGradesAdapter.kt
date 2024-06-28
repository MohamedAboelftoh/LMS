package com.example.lms.ui.student.fragments.courses_fragment.grades

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lms.databinding.GradesItemBinding
import com.example.lms.ui.api.api_student.course_tasks__grades.CourseTasksGradesResponseItem

class CourseGradesAdapter(var gradesList:List<CourseTasksGradesResponseItem?>?=null):RecyclerView.Adapter<CourseGradesAdapter.CourseTasksViewHolder>() {

    class CourseTasksViewHolder(val itemBinding: GradesItemBinding): RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseTasksViewHolder {
        val viewBinding=GradesItemBinding.inflate(
            LayoutInflater.from(parent.context)
            ,parent,false)
        return CourseTasksViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return gradesList?.size ?: 0
    }

    override fun onBindViewHolder(holder: CourseTasksViewHolder, position: Int) {
        val grade=gradesList!![position]
        val examName: String? =grade?.title
        if(examName!!.length >=25){
            holder.itemBinding.examName.text=examName.substring(0,22)+"..."
        }
        else{
            holder.itemBinding.examName.text=examName
        }
        holder.itemBinding.markTv.text="${grade.studentGrade} / ${grade.fullGrade}"
    }

    fun bindGrades(body: ArrayList<CourseTasksGradesResponseItem>?) {
        gradesList=body
        notifyDataSetChanged()
    }

}