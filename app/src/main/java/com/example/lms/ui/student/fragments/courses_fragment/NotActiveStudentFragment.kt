package com.example.lms.ui.student.fragments.courses_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lms.R
import com.example.lms.databinding.FragmentCoursesBinding
import com.example.lms.databinding.FragmentNotActiveStudentBinding

class NotActiveStudentFragment : Fragment() {
lateinit var viewBinding:FragmentNotActiveStudentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding= FragmentNotActiveStudentBinding.inflate(inflater,container,false)
        return viewBinding.root
    }

}