package com.example.lms.ui.doctor.fragments.courses
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lms.R
import com.example.lms.databinding.DrCourseItemBinding
import com.example.lms.ui.api.api_doctor.dr_courses.DrCoursesResponseItem

class DrCoursesAdapter(private var coursesList:List<DrCoursesResponseItem?>?=null):RecyclerView.Adapter<DrCoursesAdapter.CoursesViewHolder>() {

    class CoursesViewHolder(val itemBinding:DrCourseItemBinding):RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoursesViewHolder {

        val viewBinding=DrCourseItemBinding.inflate(LayoutInflater.from(parent.context)
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
        Glide.with(holder.itemView)
            .load(courses.imagePath)
            .placeholder(R.drawable.avatar_1)
            .into(holder.itemBinding.courseImage)
        holder.itemBinding.hours.text = courses.hours.toString()
        holder.itemBinding.courseItem.setOnClickListener { onItemClickListener?.onItemClick(position,courses) }
    }

    fun bindCourses(coursesResponse: List<DrCoursesResponseItem?>?) {
        coursesList=coursesResponse
        notifyDataSetChanged()
    }

    var onItemClickListener:OnItemClickListener?=null

    fun interface OnItemClickListener{
        fun onItemClick(position:Int,course: DrCoursesResponseItem?)
    }

}