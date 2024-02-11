package com.example.lms.ui.home.courses

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lms.R
import com.example.lms.databinding.CourseItemBinding

class CoursesAdapter(var coursesList:List<CourseItem>?):RecyclerView.Adapter<CoursesAdapter.CoursesViewHolder>() {

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
        holder.itemBinding.courseNameTv.text=courses.courseName
        holder.itemBinding.courseDoctor.text=courses.courseInstructor
        holder.itemBinding.courseImage.setImageResource(courses.courseImage?:R.drawable.course_image)
    }




}