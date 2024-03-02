package com.example.lms.ui.home.fragments.courses_fragment.material

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lms.databinding.LectureItemBinding
import com.example.lms.ui.api.material.CourseMaterialResponseItem

class LecturesAdapter (var lecturesList:List<CourseMaterialResponseItem?>?=null):RecyclerView.Adapter<LecturesAdapter.LecturesViewHolder>(){

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
        val lectureName: String? =lecture?.lectureName
        if(lectureName!!.length >=18){
            holder.itemBinding.lecName.text=lectureName.substring(0,15)+"..."
        }
        else{
            holder.itemBinding.lecName.text=lectureName
        }
        holder.itemBinding.lecItemCard.setOnClickListener {
            onItemClickListener?.onClick(position,lecture!!)
        }

    }

    fun bindLectures(courseMaterialResponse: List<CourseMaterialResponseItem?>?) {
        val newLectureList : MutableList<CourseMaterialResponseItem> = mutableListOf()
        if (courseMaterialResponse != null) {
            for(i in courseMaterialResponse){
                 if(i?.type == "Lecture"){
                     newLectureList.add(i)
                 }
            }
        }
        lecturesList = newLectureList
        //lecturesList=courseMaterialResponse
        notifyDataSetChanged()
    }

    var onItemClickListener:OnItemClickListener?=null
    fun interface OnItemClickListener{
        fun onClick(position:Int,item:CourseMaterialResponseItem)
    }
}