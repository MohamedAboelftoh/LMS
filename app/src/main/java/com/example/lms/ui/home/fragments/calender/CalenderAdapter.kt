package com.example.lms.ui.home.fragments.calender

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.lms.R
import com.example.lms.databinding.ItemCalenderBinding
import com.example.lms.ui.api.calender.CalenderResponseItem
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CalenderAdapter (private var calenderList : ArrayList<CalenderResponseItem>?=null) : Adapter<CalenderAdapter.ViewHolder>() {
     var viewHolder: ViewHolder? = null
    class ViewHolder(var viewBinding : ItemCalenderBinding) : RecyclerView.ViewHolder (viewBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = ItemCalenderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        viewHolder= ViewHolder(viewBinding)
        return viewHolder !!
    }

    override fun getItemCount(): Int {
        return calenderList?.size?: 0
    }

override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val calenderItem = calenderList?.get(position)
    holder.viewBinding.quiz.text = calenderItem?.body
    holder.viewBinding.startDate.text = calenderItem?.startDate
    holder.viewBinding.endDate.text = calenderItem?.endDate

    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())

    if (calenderItem != null) {
        val startDate: Date = dateFormat.parse(calenderItem.startDate)
        val endDate: Date = dateFormat.parse(calenderItem.endDate)

        val currentDate = Date()

        // Event is ongoing
        if (startDate.before(currentDate) && endDate.after(currentDate)) {
            holder.viewBinding.constraintLayout.setBackgroundResource(R.color.firstColorCalender)
            holder.viewBinding.view.setBackgroundResource(R.color.firstColorCalenderView)
        }
        // Event has not started yet
        else if (startDate.after(currentDate)) {
            holder.viewBinding.constraintLayout.setBackgroundResource(R.color.secondColorCalender)
            holder.viewBinding.view.setBackgroundResource(R.color.secondColorCalenderView)
        }
        // Event has ended
        else if (endDate.before(currentDate)) {
            holder.viewBinding.constraintLayout.setBackgroundResource(R.color.thirdColorCalender)
            holder.viewBinding.view.setBackgroundResource(R.color.thirdColorCalenderView)
        }
    }
}

    fun bindEvents(eventsList: ArrayList<CalenderResponseItem>?) {
        calenderList=eventsList

        notifyDataSetChanged()
    }
}