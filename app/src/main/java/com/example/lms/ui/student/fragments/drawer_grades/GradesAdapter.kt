package com.example.lms.ui.student.fragments.drawer_grades

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lms.databinding.GradesItemBinding

class GradesAdapter(val gradesList:MutableList<GradesItem>?):RecyclerView.Adapter<GradesAdapter.GradesViewHolder>() {

    class GradesViewHolder(val itemBinding:GradesItemBinding):RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GradesViewHolder {
        val view=GradesItemBinding.inflate(LayoutInflater.from(parent.context)
            ,parent,false)
        return GradesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return gradesList?.size?:0
    }

    override fun onBindViewHolder(holder: GradesViewHolder, position: Int) {
        val item=gradesList!![position]
        holder.itemBinding.courseNameItemGrad.text=item?.courseName
        holder.itemBinding.markTv.text=item?.mark

    }
}