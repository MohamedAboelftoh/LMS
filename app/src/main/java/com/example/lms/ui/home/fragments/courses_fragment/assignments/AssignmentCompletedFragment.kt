package com.example.lms.ui.home.fragments.courses_fragment.assignments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lms.databinding.FragmentAssignmentCompletedBinding

class AssignmentCompletedFragment : Fragment() {
    lateinit var viewBinding: FragmentAssignmentCompletedBinding
    private var assignmentsList: MutableList<AssignmentItem> = mutableListOf()
    private lateinit var assignmentAdapter: AssignmentCompletedAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentAssignmentCompletedBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAssignmentsList()
        assignmentAdapter = AssignmentCompletedAdapter(assignmentsList)
        viewBinding.recyclerAssignmentsCompleted.adapter = assignmentAdapter

    }
    private fun initAssignmentsList() {
        for (i in 0..5) {
            assignmentsList.add(AssignmentItem("parallel programing", "22/2/2024"))
        }
    }
}