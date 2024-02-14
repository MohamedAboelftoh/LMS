package com.example.lms.ui.home.fragments.courses_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lms.R
import com.example.lms.databinding.FragmentCoursesBinding
import com.example.lms.databinding.FragmentCoursessContentBinding
import com.example.lms.ui.home.fragments.courses_fragment.CoursesFragment


class CoursesContentFragment : Fragment() {
    lateinit var viewBinding:FragmentCoursessContentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
     viewBinding= FragmentCoursessContentBinding.inflate(inflater,container,false)
        return viewBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.icBack.setOnClickListener{
            requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.home_container,CoursesFragment())
            .commit()

        }
    }

}