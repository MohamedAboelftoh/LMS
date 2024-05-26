package com.example.lms.ui.doctor.fragments.courses.assignment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.example.lms.R
import com.example.lms.databinding.FragmentEditAssignmentBinding
import com.example.lms.ui.api.api_doctor.dr_courses.assignments.UpdateAssignmentModel
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.student.fragments.Variables
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar
import java.util.Locale

class EditAssignmentFragment : DialogFragment() {
    lateinit var viewBinding : FragmentEditAssignmentBinding
    lateinit var myPreferencesToken: MyPreferencesToken
    lateinit var endDate:String
     var editableFormattedDateTime: Editable? =null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentEditAssignmentBinding.inflate(inflater,container,false)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.backgound_dialog_fragment)
        return viewBinding.root

    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myPreferencesToken= MyPreferencesToken(requireContext())
        viewBinding.confirm.setOnClickListener{
            updateAssignment()
            dismiss()
        }
        viewBinding.changeDeadline.setOnClickListener{
            showDateBicker()
        }

    }
    @RequiresApi(Build.VERSION_CODES.N)
    private fun showDateBicker() {
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

                    viewBinding.deadlineTv.text = editableFormattedDateTime
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                false
            )
            timePickerDialog.show()
        }
        dateBicker?.show()
    }

    fun updateAssignment(){
        val token=myPreferencesToken.loadData("token")
        val taskId=Variables.taskId
        val taskGrade=viewBinding.gradeTv.text.toString()
        val taskName=viewBinding.taskName.text.toString()

        val updateAssignmentModel=UpdateAssignmentModel("$editableFormattedDateTime",taskGrade,taskName,"2024-03-29T11:30:00")
        ApiManager.getApi().updateAssignment(token!!,taskId!!,updateAssignmentModel).enqueue(object :Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful){
                    onAssignmentEditListener?.onAssignmentEdit()
                   Toast.makeText(context,"Updated Successful", Toast.LENGTH_SHORT).show()
                }
                else{
                  Toast.makeText(context,"Failed to update The Task", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
              //Toast.makeText(context,"${t.localizedMessage}", Toast.LENGTH_SHORT).show()

            }
        })
    }
    var onAssignmentEditListener : OnAssignmentEditListener ?= null
    interface OnAssignmentEditListener{
        fun onAssignmentEdit()
    }
}