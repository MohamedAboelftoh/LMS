package com.example.lms.ui.student.fragments.courses_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lms.databinding.CourseItemBinding
import com.example.lms.ui.api.courses.CoursesResponseItem

class CoursesAdapter(var coursesList:List<CoursesResponseItem?>?=null):RecyclerView.Adapter<CoursesAdapter.CoursesViewHolder>() {

    class CoursesViewHolder(val itemBinding:CourseItemBinding):RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoursesViewHolder {

        val viewBinding=CourseItemBinding.inflate(LayoutInflater.from(parent.context)
            ,parent,false)
        return CoursesViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return coursesList?.size ?: 0
    }

    override fun onBindViewHolder(holder: CoursesViewHolder, position: Int) {
       val courses=coursesList!![position]
        val courseName: String? =courses?.name
        if(courseName!!.length >=25){
            holder.itemBinding.courseNameTv.text=courseName.substring(0,22)+"..."
        }
        else{
            holder.itemBinding.courseNameTv.text=courseName
        }
        //holder.itemBinding.courseNameTv.text=courses?.name
        holder.itemBinding.courseDoctor.text=courses?.instructorFullName
        //holder.itemBinding.courseImage.setImageResource(courses.courseImage?:R.drawable.course_image)
        holder.itemBinding.courseItem.setOnClickListener { onItemClickListener?.onItemClick(position,courses) }
    }

    fun bindCourses(coursesResponse: List<CoursesResponseItem?>?) {
        coursesList=coursesResponse
        notifyDataSetChanged()
    }

    var onItemClickListener:OnItemClickListener?=null

    fun interface OnItemClickListener{
        fun onItemClick(position:Int,course:CoursesResponseItem?)
    }

}