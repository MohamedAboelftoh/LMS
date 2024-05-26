package com.example.lms.ui.doctor.fragments.courses.assignment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.lms.R
import com.example.lms.databinding.FragmentStudentAssignDetailsBinding
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.student.fragments.Variables
import com.example.lms.ui.student.navigateFromFragment

class StudentAssignDetailsFragment : DialogFragment() {
    lateinit var viewBinding : FragmentStudentAssignDetailsBinding
    lateinit var myPreferencesToken: MyPreferencesToken

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding=FragmentStudentAssignDetailsBinding.inflate(layoutInflater)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.backgound_dialog_fragment)
        return viewBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.tvStudentName.text=Variables.studentName
        viewBinding.timeSentTv.text=Variables.stuTimeUploaded
        viewBinding.openFIleEt.setOnClickListener{
            navigateFromFragment(requireContext(),DrAssignPdfActivity())
        }
    }

}