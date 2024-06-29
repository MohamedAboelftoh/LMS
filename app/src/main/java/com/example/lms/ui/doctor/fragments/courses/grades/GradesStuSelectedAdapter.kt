package com.example.lms.ui.doctor.fragments.courses.grades

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lms.databinding.GradesItemBinding
import com.example.lms.ui.api.api_doctor.grades.GradesSelectedStuResponseItem

class GradesStuSelectedAdapter(var gradesList:ArrayList<GradesSelectedStuResponseItem>?=null) :RecyclerView.Adapter<GradesStuSelectedAdapter.GradesStuSelectedViewHolder>() {

    class GradesStuSelectedViewHolder(val itemBinding: GradesItemBinding): RecyclerView.ViewHolder(itemBinding.root)

     override fun onCreateViewHolder(
         parent: ViewGroup,
         viewType: Int
     ): GradesStuSelectedViewHolder {
         val view=GradesItemBinding.inflate(
             LayoutInflater.from(parent.context)
             ,parent,false)
         return GradesStuSelectedViewHolder(view)
     }

     override fun getItemCount(): Int {
         return gradesList?.size?:0
     }

     override fun onBindViewHolder(holder: GradesStuSelectedViewHolder, position: Int) {
         val gradeItem=gradesList!![position]
         holder.itemBinding.examName.text=gradeItem.title.toString()
         holder.itemBinding.markTv.text=gradeItem.grade.toString()

     }

    fun bindStuGrades(body: ArrayList<GradesSelectedStuResponseItem>?) {
        gradesList=body
        notifyDataSetChanged()
    }

}