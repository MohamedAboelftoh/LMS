package com.example.lms.ui.student.fragments.courses_fragment.material

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.lms.databinding.FileItemBinding
import com.example.lms.ui.api.api_student.material.fiels.FilesResponseItem

class FilesAdapter(private var filesList:MutableList<FilesResponseItem>?=null) :RecyclerView.Adapter<FilesAdapter.FilesViewHolder>(){
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
        holder.itemBinding.lecName.text= item.fileName
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(item , position)
        }
        holder.itemBinding.icMore.visibility=View.GONE
        holder.itemBinding.icDownload.visibility=View.VISIBLE
        holder.itemBinding.icDownload.setOnClickListener{
            onIconDownloadClickListener?.onIconDownloadClick(item,position,holder)

        }
    }

    fun bindFiles(body: MutableList<FilesResponseItem>?) {
        filesList=body
        notifyDataSetChanged()
    }
 var onItemClickListener : OnItemClickListener ?= null
interface OnItemClickListener{
    fun onItemClick(item : FilesResponseItem, position : Int)
}
    var onIconDownloadClickListener:OnIconDownloadClickListener?=null
    interface OnIconDownloadClickListener{
        fun onIconDownloadClick(item: FilesResponseItem, position: Int, holder: FilesViewHolder)
    }
}