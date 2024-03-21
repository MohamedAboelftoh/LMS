package com.example.lms.ui.doctor.fragments.courses.material

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lms.databinding.LectureItemBinding
import com.example.lms.ui.api.api_doctor.dr_courses.material.DrLecturesResponseItem


class DrLecturesAdapter (var lecturesList:List<DrLecturesResponseItem?>?=null):RecyclerView.Adapter<DrLecturesAdapter.DrLecturesViewHolder>(){

    class DrLecturesViewHolder(val itemBinding: LectureItemBinding): RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrLecturesViewHolder {
        val viewBinding=LectureItemBinding.inflate(
            LayoutInflater.from(parent.context)
            ,parent,false)
        return DrLecturesViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return lecturesList?.size?:0
    }

    override fun onBindViewHolder(holder: DrLecturesViewHolder, position: Int) {
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
    fun bindLectures(drLecturesResponse: List<DrLecturesResponseItem?>?) {
        val newLectureList : MutableList<DrLecturesResponseItem> = mutableListOf()
        if (drLecturesResponse != null) {
            for(i in drLecturesResponse){
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
        fun onClick(position:Int,item: DrLecturesResponseItem)
    }
}