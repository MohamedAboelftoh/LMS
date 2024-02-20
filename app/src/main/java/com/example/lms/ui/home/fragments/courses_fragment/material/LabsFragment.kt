package com.example.lms.ui.home.fragments.courses_fragment.material

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lms.databinding.FragmentLabsBinding

class LabsFragment : Fragment() {
    lateinit var adapter:LabsAdapter
    private val labsList:MutableList<LectureItem>? = mutableListOf()
    lateinit var viewBinding:FragmentLabsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding=FragmentLabsBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeData()
    }

    private fun initializeData() {
        for (i in 1..10) {
            labsList?.add(LectureItem("Lab $i "))
        }
        adapter=LabsAdapter(labsList)
        viewBinding.labRecycler.adapter=adapter
    }


}