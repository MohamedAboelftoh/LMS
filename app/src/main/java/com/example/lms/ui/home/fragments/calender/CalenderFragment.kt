package com.example.lms.ui.home.fragments.calender

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import com.example.lms.R
import com.example.lms.databinding.FragmentCalenderBinding
import com.prolificinteractive.materialcalendarview.CalendarDay
import java.util.Calendar


class CalenderFragment : Fragment() {
    lateinit var viewBinding : FragmentCalenderBinding
    private var calenderList : ArrayList<CalenderItem> = arrayListOf()
    private lateinit var calenderAdapter : CalenderAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding=FragmentCalenderBinding.inflate(inflater,container,false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.calendarView.selectedDate = CalendarDay.today()
        fillList()
        calenderAdapter = CalenderAdapter(calenderList)
        viewBinding.recyclerViewCalender.adapter = calenderAdapter
//        viewBinding.calendarView.setOnDateChangedListener { widget, date, selected ->
//            if (selected){
//
//            }
//        }
    }

    private fun fillList() {
             calenderList.add(CalenderItem(
                "Parallel Programming - Dr Amr Masoud",
                "Second Quiz",
                "9:00 AM - 10:00 AM",
                 R.color.firstColorCalender,
                 R.color.firstColorCalenderView
            ))
        calenderList.add(CalenderItem(
            "Parallel Programming - Dr Amr Masoud",
            "Second Quiz",
            "9:00 AM - 10:00 AM",
            R.color.secondColorCalender,
            R.color.secondColorCalenderView
        ))
        calenderList.add(CalenderItem(
            "Parallel Programming - Dr Amr Masoud",
            "Second Quiz",
            "9:00 AM - 10:00 AM",
            R.color.thirdColorCalender,
            R.color.thirdColorCalenderView
        ))
        calenderList.add(CalenderItem(
            "Parallel Programming - Dr Amr Masoud",
            "Second Quiz",
            "9:00 AM - 10:00 AM",
            R.color.firstColorCalender,
            R.color.firstColorCalenderView
        ))
        calenderList.add(CalenderItem(
            "Parallel Programming - Dr Amr Masoud",
            "Second Quiz",
            "9:00 AM - 10:00 AM",
            R.color.secondColorCalender,
            R.color.secondColorCalenderView
        ))

    }
}