package com.example.lms.ui.home.fragments.courses_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lms.R
import com.example.lms.databinding.FragmentCoursesBinding

class CoursesFragment : Fragment() {
    lateinit var viewBinding: FragmentCoursesBinding
    lateinit var adapter: CoursesAdapter
    private var coursesList:MutableList<CourseItem> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding=FragmentCoursesBinding.inflate(inflater,container,false)
        return viewBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeData()
        initViews()

    }



    fun initializeData(){

        for(i in 1..5) {
            coursesList.add(
                CourseItem(
                    "Parallel Programing",
                    "Dr : Amr Masoud",
                    R.drawable.course_image
                )
            )
        }
        adapter= CoursesAdapter(coursesList)
        viewBinding.coursesRecView.adapter=adapter
    }
    private fun initViews() {
        adapter.onItemClickListener= CoursesAdapter.OnItemClickListener{ position, course ->
            requireActivity().supportFragmentManager
                .beginTransaction()
                .replace(R.id.home_container, CoursesContentFragment())
                .commit()

        }

    }
}