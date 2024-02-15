package com.example.lms.ui.home.fragments.courses_fragment.assignments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lms.R
import com.example.lms.databinding.FragmentUploadBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class UploadFragment : BottomSheetDialogFragment() {
lateinit var viewBinding : FragmentUploadBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentUploadBinding.inflate(inflater,container,false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.btnSubmit.setOnClickListener {
            navigateToAssignmentActivity()
        }
    }

    private fun navigateToAssignmentActivity() {
        val intent = Intent(requireContext(),AssignmentsActivity::class.java)
        startActivity(intent)
    }
}