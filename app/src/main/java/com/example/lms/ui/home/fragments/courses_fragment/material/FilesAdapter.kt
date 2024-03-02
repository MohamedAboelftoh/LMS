package com.example.lms.ui.home.fragments.courses_fragment.material

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.lms.databinding.FileItemBinding

class FilesAdapter(var filesList:List<FileItem?>?=null) :RecyclerView.Adapter<FilesAdapter.FilesViewHolder>(){
    class FilesViewHolder(val itemBinding:FileItemBinding):ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilesViewHolder {
            val view=FileItemBinding.inflate(LayoutInflater.from(parent.context)
                ,parent,false)
        return FilesViewHolder(view)
        }

    override fun getItemCount(): Int {
        return filesList?.size ?: 0
    }

    override fun onBindViewHolder(holder: FilesViewHolder, position: Int) {
        val item=filesList!![position]
        holder.itemBinding.lecName.text=item?.fileName
        holder.itemBinding.fileExtension.text=item?.fileExtension


    }
}