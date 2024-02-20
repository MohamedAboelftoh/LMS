package com.example.lms.ui.home.fragments.courses_fragment.material

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lms.databinding.LectureItemBinding

class LecturesAdapter (val lecturesList:MutableList<LectureItem>?):RecyclerView.Adapter<LecturesAdapter.LecturesViewHolder>(){

    class LecturesViewHolder(val itemBinding:LectureItemBinding):RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LecturesViewHolder {
       val viewBinding=LectureItemBinding.inflate(LayoutInflater.from(parent.context)
           ,parent,false)
        return LecturesViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return lecturesList?.size?:0
    }

    override fun onBindViewHolder(holder: LecturesViewHolder, position: Int) {
        val lecture=lecturesList!![position]
        holder.itemBinding.lecName.text=lecture.lectureName

    }
}