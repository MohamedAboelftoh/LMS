package com.example.lms.ui.home.fragments.courses_fragment.material

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.lms.databinding.FileItemBinding
import com.example.lms.ui.api.material.fiels.FielslResponseItem

class FilesAdapter(var filesList:MutableList<FielslResponseItem>?=null) :RecyclerView.Adapter<FilesAdapter.FilesViewHolder>(){
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

//        val extention=item?.fileName?.split(".")?.get(1)
//        holder.itemBinding.fileExtension.text="."+extention


    }

    fun bindFiles(body: MutableList<FielslResponseItem>?) {
        filesList=body
        notifyDataSetChanged()
    }


}