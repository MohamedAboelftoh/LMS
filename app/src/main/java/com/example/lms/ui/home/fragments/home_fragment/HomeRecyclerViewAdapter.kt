package com.example.lms.ui.home.fragments.home_fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.lms.R
import com.example.lms.databinding.ItemNewsHomeBinding

class HomeRecyclerViewAdapter(private val listItems :MutableList<ItemHomeNews> ) :  Adapter<HomeRecyclerViewAdapter.ViewHolder>() {
    class ViewHolder (val  viewBinding : ItemNewsHomeBinding): RecyclerView.ViewHolder(viewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val viewBinding = ItemNewsHomeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return listItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val item = listItems[position]
      holder.viewBinding.userName.text = item.userName
        holder.viewBinding.description.text = item.description
        holder.viewBinding.time.text = item.time
        holder.viewBinding.imgProfile.setImageResource(item.profileImg!!)
        holder.viewBinding.imgDescription.setImageResource(item.postImg!!)
        holder.viewBinding.status.text = item.state
    }
}