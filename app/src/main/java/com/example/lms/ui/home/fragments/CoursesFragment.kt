package com.example.lms.ui.home.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lms.R
import com.example.lms.ui.home.courses.CourseItem
import com.example.lms.ui.home.courses.CoursesAdapter
import kotlinx.coroutines.CoroutineExceptionHandler

class CoursesFragment : Fragment() {
    lateinit var adapter:CoursesAdapter
    lateinit var coursesList:ArrayList<CourseItem>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_courses, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeData()
        adapter= CoursesAdapter(coursesList)
    }
    fun initializeData(){
        coursesList= ArrayList()
        for(i in 1..5) {
            coursesList.add(
                CourseItem(
                    "Parallel Programing",
                    "Dr : Amr Masoud",
                    R.drawable.course_image
                )
            )
        }
    }

}