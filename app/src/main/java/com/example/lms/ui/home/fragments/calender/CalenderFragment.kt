/*//package com.example.lms.ui.home.fragments.calender
//
//import android.os.Build
//import android.os.Bundle
//import android.util.Log
//import androidx.fragment.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Toast
//import androidx.annotation.RequiresApi
//import com.example.lms.databinding.FragmentCalenderBinding
//import com.example.lms.databinding.ItemCalenderBinding
//import com.example.lms.ui.api.calender.CalenderResponseItem
//import com.example.lms.ui.api.module.ApiManager
//import com.example.lms.ui.api.module.MyPreferencesToken
//import com.prolificinteractive.materialcalendarview.CalendarDay
//import com.prolificinteractive.materialcalendarview.MaterialCalendarView
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//import java.util.Calendar
//
//
//class CalenderFragment : Fragment() {
//    lateinit var viewBinding : FragmentCalenderBinding
//     lateinit var calenderAdapter : CalenderAdapter
//    lateinit var myPreferencesToken:MyPreferencesToken
//
//    //lateinit var selectedDate: Calendar
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        viewBinding=FragmentCalenderBinding.inflate(inflater,container,false)
//        return viewBinding.root
//    }
//
//    @RequiresApi(Build.VERSION_CODES.N)
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        myPreferencesToken=MyPreferencesToken(requireContext())
//        calenderAdapter = CalenderAdapter()
//        viewBinding.recyclerViewCalender.adapter = calenderAdapter
//        getTodayOtomaticly()
//        getCalenderEvents()
//
//        viewBinding.floatingActionBtn.setOnClickListener {
//            val addEventFragment = AddEventFragment()
//            addEventFragment.show(parentFragmentManager,"")
//        }
//    }
//fun getTodayOtomaticly(){
//    viewBinding.calendarView.selectedDate = CalendarDay.today()
//    val token=myPreferencesToken.loadData("token")
//    var selectedDate: CalendarDay = CalendarDay.today()
//    val startDate = String.format(
//        "%d-%02d-%02d",
//        selectedDate.year,
//        selectedDate.month,
//        selectedDate.day
//    )
//    val endDate = String.format(
//        "%d-%02d-%02d",
//        selectedDate.year,
//        selectedDate.month,
//        selectedDate.day+1
//    )
//
//ApiManager.getApi().getCalenderEvents(token!!,startDate,endDate).enqueue(object :Callback<ArrayList<CalenderResponseItem>>{
//    override fun onResponse(
//        call: Call<ArrayList<CalenderResponseItem>>,
//        response: Response<ArrayList<CalenderResponseItem>>
//    ) {
//        calenderAdapter.bindEvents(response.body())
//
//    }
//
//    override fun onFailure(call: Call<ArrayList<CalenderResponseItem>>, t: Throwable) {
//        TODO("Not yet implemented")
//    }
//})
//
//}
//    @RequiresApi(Build.VERSION_CODES.N)
//    fun getCalenderEvents() {
//        viewBinding.calendarView.selectedDate = CalendarDay.today()
//
//        val token=myPreferencesToken.loadData("token")
//        viewBinding.calendarView.setOnDateChangedListener { widget: MaterialCalendarView,
//                                                            date: CalendarDay, selected: Boolean ->
//
//            if(selected){
//                val startDate = String.format("%d-%01d-%01d", date.year, date.month ,date.day )
//                val endDate = String.format("%d-%01d-%01d", date.year, date.month ,date.day+1 )
//
//                ApiManager.getApi().getCalenderEvents(token!!,startDate,endDate)
//                    .enqueue(object :Callback<ArrayList<CalenderResponseItem>>{
//                        override fun onResponse(
//                            call: Call<ArrayList<CalenderResponseItem>>,
//                            response: Response<ArrayList<CalenderResponseItem>>
//                        ) {
//                            if (response.isSuccessful){
//                                calenderAdapter.bindEvents(response.body())
//                            }
//                        }
//                        override fun onFailure(call: Call<ArrayList<CalenderResponseItem>>, t: Throwable) {
//                            Toast.makeText(requireContext(), "onFailure "+t.localizedMessage, Toast.LENGTH_SHORT)
//                                .show()
//                        }
//                    })
//            }
//        }
//    }
//
//
//}
*/
package com.example.lms.ui.home.fragments.calender

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.lms.databinding.FragmentCalenderBinding
import com.example.lms.ui.api.calender.CalenderResponseItem
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CalenderFragment : Fragment() {
    private lateinit var viewBinding: FragmentCalenderBinding
    private lateinit var calenderAdapter: CalenderAdapter
    private lateinit var myPreferencesToken: MyPreferencesToken

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentCalenderBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        myPreferencesToken = MyPreferencesToken(requireContext())
        calenderAdapter = CalenderAdapter()
        viewBinding.recyclerViewCalender.adapter = calenderAdapter

        // Automatically load events for today
        getEventsForDate(CalendarDay.today())

        // Set listener for calendar view
        viewBinding.calendarView.setOnDateChangedListener { widget: MaterialCalendarView, date: CalendarDay, selected: Boolean ->
            if (selected) {
                getEventsForDate(date)
            }
        }

        viewBinding.floatingActionBtn.setOnClickListener {
            val addEventFragment = AddEventFragment()
            addEventFragment.show(parentFragmentManager, "")
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getEventsForDate(date: CalendarDay) {
        val token = myPreferencesToken.loadData("token")
        val startDate = String.format("%d-%02d-%02d", date.year, date.month , date.day)
        val endDate = String.format("%d-%02d-%02d", date.year, date.month , date.day + 1)

        ApiManager.getApi().getCalenderEvents(token!!, startDate, endDate)
            .enqueue(object : Callback<ArrayList<CalenderResponseItem>> {
                override fun onResponse(
                    call: Call<ArrayList<CalenderResponseItem>>,
                    response: Response<ArrayList<CalenderResponseItem>>
                ) {
                    if (response.isSuccessful) {
                        calenderAdapter.bindEvents(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<CalenderResponseItem>>, t: Throwable) {
                    Toast.makeText(
                        requireContext(),
                        "onFailure " + t.localizedMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
    }
}
