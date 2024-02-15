package com.example.lms.ui.home.fragments.courses_fragment.assignments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lms.databinding.FragmentAssignmentPendingBinding

class AssignmentPendingFragment : Fragment() {
    lateinit var viewBinding: FragmentAssignmentPendingBinding
    private var assignmentsList: MutableList<AssignmentItem> = mutableListOf()
    private lateinit var assignmentAdapter: AssignmentPendingAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentAssignmentPendingBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAssignmentsList()
        assignmentAdapter = AssignmentPendingAdapter(assignmentsList)
        viewBinding.recyclerAssignmentsPending.adapter = assignmentAdapter
        assignmentAdapter.onBtnMoreClickListener = object : AssignmentPendingAdapter.OnBtnMoreClickListener{
            override fun onClick(position: Int, item: AssignmentItem) {
                navigateToAssignmentDetails()
            }

        }

    }

    private fun navigateToAssignmentDetails() {
        val intent = Intent(requireActivity() , AssignmentDetailsActivity::class.java)
        startActivity(intent)
    }

    private fun initAssignmentsList() {
        for (i in 0..5) {
            assignmentsList.add(AssignmentItem("parallel programing", "3 More day"))
        }

    }
}