package com.example.lms.ui.doctor.fragments.courses.assignment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleAdapter.ViewBinder
import com.example.lms.R
import com.example.lms.databinding.FragmentDrAssignmentCompletedBinding


class DrAssignmentCompletedFragment : Fragment() {

   lateinit var viewBinding: FragmentDrAssignmentCompletedBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding=FragmentDrAssignmentCompletedBinding.inflate(inflater,container,false)
        return viewBinding.root
    }

}