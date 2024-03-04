package com.example.lms.ui.home.fragments.calender

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.lms.R
import com.example.lms.databinding.ItemCalenderBinding

class CalenderAdapter (private var calenderList : List<CalenderItem>) : Adapter<CalenderAdapter.ViewHolder>() {
    class ViewHolder(var viewBinding : ItemCalenderBinding) : RecyclerView.ViewHolder (viewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = ItemCalenderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return calenderList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val calenderItem = calenderList[position]
        holder.viewBinding.courseName.text = calenderItem.courseName
        holder.viewBinding.quiz.text = calenderItem.quiz
        holder.viewBinding.time.text = calenderItem.time
        holder.viewBinding.linearLayout.setBackgroundResource(calenderItem.color!!)
        holder.viewBinding.view.setBackgroundResource(calenderItem.colorView!!)
    }
}