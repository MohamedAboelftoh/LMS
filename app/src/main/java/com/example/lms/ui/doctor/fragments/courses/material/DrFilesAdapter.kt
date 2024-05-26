package com.example.lms.ui.doctor.fragments.courses.material

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lms.R
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
        val path = item.filePath?.substringAfterLast('/')
        val extension = path?.substringAfterLast('.', "")
        if(extension.equals("pdf")){
            holder.itemBinding.pdf.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.redColor))
        }
        else if (extension.equals("docx")){
            holder.itemBinding.pdf.setBackgroundColor(ContextCompat.getColor(holder.itemView.context, R.color.colorPrimary))
        }
        holder.itemBinding.pdf.text = extension
        holder.itemBinding.lecName.text= item.fileName
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(item , position)
        }
        holder.itemBinding.icMore.setOnClickListener{
            onIconMoreClickListener?.onIconMoreClick(item,position,holder)

        }
        Glide.with(holder.itemView)
            .load(item.filePath)
            .into(holder.itemBinding.image)
    }

    fun bindFiles(body: MutableList<DrFilesResponseItem>?) {
        filesList=body
        notifyDataSetChanged()
    }
    var onItemClickListener : OnItemClickListener?= null
    interface OnItemClickListener{
        fun onItemClick(item : DrFilesResponseItem, position : Int)
    }

    var onIconMoreClickListener:OnIconMoreClickListener?=null
    interface OnIconMoreClickListener{
        fun onIconMoreClick(item: DrFilesResponseItem, position: Int, holder: FilesViewHolder)
    }
}