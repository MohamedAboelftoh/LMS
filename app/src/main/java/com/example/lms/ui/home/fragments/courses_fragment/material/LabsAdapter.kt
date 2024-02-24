package com.example.lms.ui.home.fragments.courses_fragment.material

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lms.databinding.LectureItemBinding
import com.example.lms.ui.api.material.CourseMaterialResponseItem

class LabsAdapter (var labsList:List<CourseMaterialResponseItem?>?=null):RecyclerView.Adapter<LabsAdapter.LabsViewHolder>(){
    class LabsViewHolder(val itemBinding:LectureItemBinding):RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LabsViewHolder {
        val viewBinding=LectureItemBinding.inflate(LayoutInflater.from(parent.context)
            ,parent,false)
        return LabsViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return labsList?.size?:0
    }

    override fun onBindViewHolder(holder: LabsViewHolder, position: Int) {
        val lab=labsList!![position]
        holder.itemBinding.lecName.text=lab?.lectureName
    }

    fun bindLabs(courseMaterialResponse: List<CourseMaterialResponseItem?>?) {
        labsList=courseMaterialResponse
        notifyDataSetChanged()
    }
}