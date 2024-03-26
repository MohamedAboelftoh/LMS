package com.example.lms.ui.doctor.fragments.courses.assignment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.lms.R
import com.example.lms.databinding.FragmentEditAssignmentBinding

class EditAssignmentFragment : DialogFragment() {
    lateinit var viewBinding : FragmentEditAssignmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentEditAssignmentBinding.inflate(inflater,container,false)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.backgound_dialog_fragment)
        return viewBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}