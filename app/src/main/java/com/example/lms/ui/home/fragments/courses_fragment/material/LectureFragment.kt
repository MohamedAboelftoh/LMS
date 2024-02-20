package com.example.lms.ui.home.fragments.courses_fragment.material

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lms.databinding.FragmentLectureBinding


class LectureFragment : Fragment() {
    lateinit var viewBinding:FragmentLectureBinding
    lateinit var adapter:LecturesAdapter
    private var lecturesList:MutableList<LectureItem> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding=FragmentLectureBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeData()
    }

    private fun initializeData() {
        for (i in 1 .. 10){
            lecturesList.add(LectureItem("Lecture $i"))

        }
        adapter=LecturesAdapter(lecturesList)
        viewBinding.lecRecycler.adapter=adapter

    }


}