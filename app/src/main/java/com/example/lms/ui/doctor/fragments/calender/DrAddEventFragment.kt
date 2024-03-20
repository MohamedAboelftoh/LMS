package com.example.lms.ui.doctor.fragments.calender

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.example.lms.R
import com.example.lms.databinding.FragmentAddEventBinding
import com.example.lms.databinding.FragmentDrAddEventBinding
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.student.fragments.calender.AddEventFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Calendar


class DrAddEventFragment : BottomSheetDialogFragment() {
    lateinit var viewBinding : FragmentDrAddEventBinding
    lateinit var myPreferencesToken: MyPreferencesToken
 override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding=FragmentDrAddEventBinding.inflate(inflater,container,false)
     return viewBinding.root
    }
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myPreferencesToken = MyPreferencesToken(requireContext())
        viewBinding.tvSelectDate.setOnClickListener {
            val dateBicker = DatePickerDialog(requireContext())
            dateBicker.show()
        }
    }

}