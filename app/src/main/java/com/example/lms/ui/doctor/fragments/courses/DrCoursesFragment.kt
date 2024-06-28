package com.example.lms.ui.doctor.fragments.courses

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.lms.R
import com.example.lms.databinding.FragmentDrCoursesBinding
import com.example.lms.ui.api.api_doctor.dr_courses.DrCoursesResponseItem
import com.example.lms.ui.api.api_student.courses.CoursesResponseItem
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.db.DataBase
import com.example.lms.ui.student.checkForInternet
import com.example.lms.ui.student.fragments.Variables
import com.example.lms.ui.student.fragments.courses_fragment.CourseContent
import com.example.lms.ui.student.fragments.courses_fragment.CoursesAdapter
import com.example.lms.ui.student.navigateFromFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class DrCoursesFragment : Fragment() {
    lateinit var viewBinding : FragmentDrCoursesBinding
    lateinit var adapter: DrCoursesAdapter
    lateinit var myPreferencesToken: MyPreferencesToken
    private lateinit var viewModel: DrCoursesViewModel
    private var numOfCourses : Int ?= null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentDrCoursesBinding.inflate(inflater,container,false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        if(checkForInternet(requireContext())){
            initObservers()
        }else{
            adapter.bindCourses(DataBase.getInstance(requireContext()).coursesDao().getCoursesFromLocal())
        }
        onCourseClick()
    }

    private fun initViews() {
        numOfCourses = DataBase.getInstance(requireContext()).coursesDao().getCoursesFromLocal().size
        viewModel = ViewModelProvider(this)[DrCoursesViewModel::class.java]
        myPreferencesToken= MyPreferencesToken(requireContext())
        adapter = DrCoursesAdapter()
        viewBinding.coursesRecView.adapter = adapter
        viewModel.uploadCourses(myPreferencesToken.loadData("token"))
        callTextChanges()
    }

    private fun initObservers() {
        viewModel.coursesList.observe(viewLifecycleOwner){
            cacheCoursesInLocal(it)
            adapter.bindCourses(it)
            numOfCourses = it.size
       }
        viewModel.toastMessage.observe(viewLifecycleOwner){ message ->
            Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show()
        }
    }

    private fun cacheCoursesInLocal(it: ArrayList<DrCoursesResponseItem>?) {
        DataBase.getInstance(requireContext()).coursesDao().deleteAllCourses()
        DataBase.getInstance(requireContext()).coursesDao().insertCourses(it!!)
    }

    private fun onCourseClick() {
        adapter.onItemClickListener= DrCoursesAdapter.OnItemClickListener{ position, course ->
            Variables.cycleId = course?.cycleId
            Variables.courseName = course?.name
            navigateFromFragment(requireContext(),DrCourseContentActivity())
        }
    }
    private fun callTextChanges() {
        val text = "enrolled <font color='#2295EF'>$numOfCourses</font> Courses"
        viewBinding.tvEnrolled.text = Html.fromHtml( text)
    }

}