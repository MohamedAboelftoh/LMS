package com.example.lms.ui.doctor.fragments.courses.material

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lms.databinding.FileItemBinding
import com.example.lms.ui.api.api_doctor.dr_courses.material.DrFilesResponseItem

class DrFilesAdapter (private var filesList:MutableList<DrFilesResponseItem>?=null) :RecyclerView.Adapter<DrFilesAdapter.FilesViewHolder>(){
    class FilesViewHolder(val itemBinding: FileItemBinding): RecyclerView.ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilesViewHolder {
        val view= FileItemBinding.inflate(
            LayoutInflater.from(parent.context)
            ,parent,false)
        return FilesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filesList?.size ?: 0
    }

    override fun onBindViewHolder(holder: FilesViewHolder, position: Int) {
        val item=filesList!![position]
        holder.itemBinding.lecName.text= item.fileName
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(item , position)
        }
    }

    fun bindFiles(body: MutableList<DrFilesResponseItem>?) {
        filesList=body
        notifyDataSetChanged()
    }
    var onItemClickListener : OnItemClickListener?= null
    interface OnItemClickListener{
        fun onItemClick(item : DrFilesResponseItem, position : Int)
    }
}