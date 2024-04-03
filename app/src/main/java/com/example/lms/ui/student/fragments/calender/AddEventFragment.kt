package com.example.lms.ui.student.fragments.calender

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.lms.databinding.FragmentAddEventBinding
import com.example.lms.ui.api.api_student.calender.CalenderRequest
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar
import java.util.Locale

class AddEventFragment : BottomSheetDialogFragment() {
    lateinit var viewBinding : FragmentAddEventBinding

    val calendar = Calendar.getInstance()
    lateinit var myPreferencesToken: MyPreferencesToken
    lateinit var endDate:String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentAddEventBinding.inflate(inflater,container,false)
        return viewBinding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myPreferencesToken = MyPreferencesToken(requireContext())
        viewBinding.tvSelectDate.setOnClickListener {
          showDateBicker()
        }
        viewBinding.btnAdd.setOnClickListener {
            if (!validation()){
                return@setOnClickListener
            }
            addEvent()
        }
    }
    private fun getCurrentTime(): String {
        val calendar = Calendar.getInstance()
        val hours = calendar.get(Calendar.HOUR_OF_DAY)
        val minutes = calendar.get(Calendar.MINUTE)
        val seconds = calendar.get(Calendar.SECOND)
        return String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, minutes, seconds)
    }
    private fun validation(): Boolean {
        var isvaild : Boolean = true
        if(viewBinding.event.text.isNullOrBlank())
        {
            viewBinding.eventContainer.error = "Event is require"
            isvaild = false
        }
        else{
            viewBinding.eventContainer.error = null
        }
        if(viewBinding.date.text.isNullOrBlank())
        {
            viewBinding.tvSelectDateContainer.error = "Date is require"
            isvaild = false
        }
        else{
            viewBinding.tvSelectDateContainer.error = null
        }
        return isvaild
    }
    private fun addEvent() {
        val token = myPreferencesToken.loadData("token")
        val startTime = viewBinding.date.text.toString()
        val event = viewBinding.event.text.toString()
        val calenderRequest =
            CalenderRequest(endDate, event, startTime)
        ApiManager.getApi().addNewEvent(token!! ,calenderRequest )
          .enqueue(object : Callback<ResponseBody>{
              override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                  if (response.isSuccessful){
                      Toast.makeText(requireContext(),"Event Added",Toast.LENGTH_LONG).show()
                      dismiss()
                  }
              }
              override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                  Toast.makeText(requireContext(),"Not Added",Toast.LENGTH_LONG).show()
              }
          })
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun showDateBicker() {
        val dateBicker = DatePickerDialog(requireContext())
        dateBicker.setOnDateSetListener { datePicker, year, month, day ->
            val formattedDate = String.format("%d-%02d-%02d", year, month + 1, day)+"T"+getCurrentTime()
             endDate = String.format("%d-%02d-%02d", year, month + 1, day+1)+"T"+getCurrentTime()

            viewBinding.date.text = formattedDate
            calendar.set(year,month,day)
            calendar.set(Calendar.HOUR_OF_DAY,0)
            calendar.set(Calendar.MINUTE,0)
            calendar.set(Calendar.SECOND,0)
            calendar.set(Calendar.MILLISECOND,0)
        }
        dateBicker.show()
    }

}