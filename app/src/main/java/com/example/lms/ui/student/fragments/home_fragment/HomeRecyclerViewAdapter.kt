package com.example.lms.ui.student.fragments.home_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.bumptech.glide.Glide
import com.example.lms.R
import com.example.lms.databinding.ItemNewsHomeBinding
import com.example.lms.ui.api.api_student.news.NewsResponseItem

class HomeRecyclerViewAdapter( var newsList: List<com.example.lms.ui.api.api_student.news.NewsResponseItem?>?= null ) :  Adapter<HomeRecyclerViewAdapter.ViewHolder>() {
    class ViewHolder (val  viewBinding : ItemNewsHomeBinding): RecyclerView.ViewHolder(viewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val viewBinding = ItemNewsHomeBinding.inflate(LayoutInflater.from(parent.context)
          ,parent,false)
        return ViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return newsList?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val item = newsList!![position]
      holder.viewBinding.userName.text = item?.userName
        holder.viewBinding.description.text = item?.content
        holder.viewBinding.status.text = item?.createdAt
       // holder.viewBinding.imgProfile.setImageResource(item.profileImg!!)
        Glide.with(holder.itemView)
            .load(item?.filePath)
            .placeholder(R.drawable.course_image)
            .into(holder.viewBinding.imgDescription)

        Glide.with(holder.itemView)
            .load(item?.userImage)
            .placeholder(R.drawable.course_image)
            .into(holder.viewBinding.profileNews)
       // holder.viewBinding.status.text = item.state
    }

    fun bindNews(news: List<com.example.lms.ui.api.api_student.news.NewsResponseItem?>?) {
        newsList = news
        notifyDataSetChanged()
    }
}