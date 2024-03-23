package com.example.lms.ui.doctor.fragments.courses.assignment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lms.R
import com.example.lms.databinding.FragmentDrAssignmentPendingBinding

class DrAssignmentPendingFragment : Fragment() {
    lateinit var viewBinding:FragmentDrAssignmentPendingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding=FragmentDrAssignmentPendingBinding.inflate(inflater,container,false)
        return viewBinding.root

    }

}