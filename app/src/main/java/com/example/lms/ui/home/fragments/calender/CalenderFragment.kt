package com.example.lms.ui.home.fragments.calender

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.lms.databinding.FragmentCalenderBinding
import com.example.lms.databinding.ItemCalenderBinding
import com.example.lms.ui.api.calender.CalenderResponseItem
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar


class CalenderFragment : Fragment() {
    lateinit var viewBinding : FragmentCalenderBinding
     lateinit var calenderAdapter : CalenderAdapter
    lateinit var myPreferencesToken:MyPreferencesToken

    //lateinit var selectedDate: Calendar
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding=FragmentCalenderBinding.inflate(inflater,container,false)
        return viewBinding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myPreferencesToken=MyPreferencesToken(requireContext())
        calenderAdapter = CalenderAdapter()
        viewBinding.recyclerViewCalender.adapter = calenderAdapter

        getCalenderEvents()

        viewBinding.floatingActionBtn.setOnClickListener {
            val addEventFragment = AddEventFragment()
            addEventFragment.show(parentFragmentManager,"")
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getCalenderEvents() {
        viewBinding.calendarView.setSelectedDate(CalendarDay.today())

        val token=myPreferencesToken.loadData("token")
        viewBinding.calendarView.setOnDateChangedListener { widget: MaterialCalendarView,
                                                            date: CalendarDay, selected: Boolean ->

            if(selected){
                val startDate = String.format("%d-%01d-%01d", date.year, date.month ,date.day )
                val endDate = String.format("%d-%01d-%01d", date.year, date.month ,date.day+1 )

                ApiManager.getApi().getCalenderEvents(token!!,startDate,endDate)
                    .enqueue(object :Callback<ArrayList<CalenderResponseItem>>{
                        override fun onResponse(
                            call: Call<ArrayList<CalenderResponseItem>>,
                            response: Response<ArrayList<CalenderResponseItem>>
                        ) {
                            if (response.isSuccessful){
                                calenderAdapter.bindEvents(response.body())
                                Toast.makeText(requireContext(), "successful", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                        override fun onFailure(call: Call<ArrayList<CalenderResponseItem>>, t: Throwable) {
                            Toast.makeText(requireContext(), "onFailure "+t.localizedMessage, Toast.LENGTH_SHORT)
                                .show()
                            Log.e("TAG", "${t.localizedMessage}: ", )
                        }
                    })
            }
        }
    }


}