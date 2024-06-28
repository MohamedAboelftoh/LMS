package com.example.lms.ui.student.fragments.courses_fragment.material

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lms.databinding.LectureItemBinding
import com.example.lms.ui.api.api_student.material.CourseMaterialResponseItem

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
        val labName: String? =lab?.lectureName

        if(labName!!.length >=18){
            holder.itemBinding.lecName.text=labName.substring(0,15)+"..."
        }
        else{
            holder.itemBinding.lecName.text=labName
        }
        holder.itemBinding.lecItemCard.setOnClickListener {
            onItemClickListener?.onClick(position,lab!!)
        }
        holder.itemBinding.more.visibility=View.GONE
    }

    fun bindLabs(courseMaterialResponse: List<CourseMaterialResponseItem?>?) {
        val newLabList : MutableList<CourseMaterialResponseItem> = mutableListOf()
        if (courseMaterialResponse != null) {
            for(i in courseMaterialResponse){
                if(i?.type == "Lab"){
                    newLabList.add(i)
                }
            }
        }
        labsList = newLabList
       // labsList=courseMaterialResponse
        notifyDataSetChanged()
    }

    var onItemClickListener:OnItemClickListener?=null
    fun interface OnItemClickListener{
        fun onClick(position:Int,item: CourseMaterialResponseItem)
    }
}