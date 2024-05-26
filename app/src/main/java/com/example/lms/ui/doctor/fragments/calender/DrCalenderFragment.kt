package com.example.lms.ui.doctor.fragments.calender

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lms.R
import com.example.lms.databinding.ActivityDoctorMainBinding
import com.example.lms.databinding.FragmentDrCalenderBinding
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.student.fragments.calender.CalenderAdapter

class DrCalenderFragment : Fragment() {
    lateinit var viewBinding: FragmentDrCalenderBinding
    private lateinit var myPreferencesToken: MyPreferencesToken

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       viewBinding=FragmentDrCalenderBinding.inflate(inflater,container,false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myPreferencesToken = MyPreferencesToken(requireContext())

        viewBinding.floatingActionBtn.setOnClickListener{
            val addEventFragment = DrAddEventFragment()
            addEventFragment.show(parentFragmentManager, "")
        }
    }

}