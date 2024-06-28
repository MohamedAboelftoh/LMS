package com.example.lms.ui.student.fragments.calender

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
    private var editableFormattedDateTime: Editable? =null


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

        viewBinding.btnStartDate1.setOnClickListener {
            showDateAndTimeBicker(viewBinding.startDate1)
        }
        viewBinding.btnEndDate1.setOnClickListener {
            showDateAndTimeBicker(viewBinding.endDate1)
        }
        viewBinding.btnAdd.setOnClickListener {
            if (!validation()){
                return@setOnClickListener
            }
            addEvent()
        }
    }
    private fun validation(): Boolean {
        var isvaild = true
        if(viewBinding.event.text.isNullOrBlank())
        {
            viewBinding.eventContainer.error = "Event is require"
            isvaild = false
        }
        else{
            viewBinding.eventContainer.error = null
        }
        if(viewBinding.startDate1.text.isNullOrBlank())
        {
            viewBinding.selectStartDateContainer.error = "Date is require"
            isvaild = false
        }
        else{
            viewBinding.selectStartDateContainer.error = null
        }
        return isvaild
    }
    private fun addEvent() {
        val token = myPreferencesToken.loadData("token")
        val startTime = viewBinding.startDate1.text.toString()
        val endTime = viewBinding.endDate1.text.toString()

        val event = viewBinding.event.text.toString()
        val calenderRequest =
            CalenderRequest(endTime, event, startTime)
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
    private fun showDateAndTimeBicker(date : TextView) {
        val dateBicker =  DatePickerDialog(requireContext())
        dateBicker?.setOnDateSetListener { datePicker, year, month, day ->
            val calendar = Calendar.getInstance()
            val timePickerDialog = TimePickerDialog(
                requireContext(),
                { view, hourOfDay, minute ->
                    calendar.set(year, month, day, hourOfDay, minute)
                    val formattedDateTime = String.format(
                        "%d-%02d-%02dT%02d:%02d:%02d",
                        year,
                        month + 1,
                        day,
                        hourOfDay,
                        minute,
                        0
                    )

                    editableFormattedDateTime =
                        Editable.Factory.getInstance().newEditable(formattedDateTime)
                    date.text = editableFormattedDateTime
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                false
            )
            timePickerDialog.show()
        }
        dateBicker?.show()
    }


}