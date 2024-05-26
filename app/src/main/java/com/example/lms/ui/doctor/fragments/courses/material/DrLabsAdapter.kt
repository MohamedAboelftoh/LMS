package com.example.lms.ui.doctor.fragments.courses.material

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lms.databinding.LectureItemBinding
import com.example.lms.ui.api.api_doctor.dr_courses.material.DrLecturesResponseItem

class DrLabsAdapter(var labsList:List<DrLecturesResponseItem?>?=null):RecyclerView.Adapter<DrLabsAdapter.DrLabsViewHolder>() {
    class DrLabsViewHolder(val itemBinding: LectureItemBinding): RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrLabsViewHolder {
        val viewBinding=LectureItemBinding.inflate(
            LayoutInflater.from(parent.context)
            ,parent,false)
        return DrLabsViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return labsList?.size?:0
    }

    override fun onBindViewHolder(holder: DrLabsViewHolder, position: Int) {
        val lecture=labsList!![position]
        val labName: String? =lecture?.lectureName
        if(labName!!.length >=18){
            holder.itemBinding.lecName.text=labName.substring(0,15)+"..."
        }
        else{
            holder.itemBinding.lecName.text=labName
        }
        holder.itemBinding.lecItemCard.setOnClickListener {
            onItemClickListener?.onClick(position,lecture!!)
        }
    }

    fun bindLabs(drLecturesResponse: List<DrLecturesResponseItem?>?) {
        val newLabList : MutableList<DrLecturesResponseItem> = mutableListOf()
        if (drLecturesResponse != null) {
            for(i in drLecturesResponse){
                if(i?.type == "Lab"){
                    newLabList.add(i)
                }
            }
        }
        labsList = newLabList
        //lecturesList=courseMaterialResponse
        notifyDataSetChanged()
    }
    var onItemClickListener:OnItemClickListener?=null
    fun interface OnItemClickListener{
        fun onClick(position:Int,item: DrLecturesResponseItem)
    }
}