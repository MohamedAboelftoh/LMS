package com.example.lms.ui.home.fragments.courses_fragment.assignments

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.lms.R
import com.example.lms.databinding.ActivityAssimmentsBinding
import com.example.lms.ui.home.fragments.courses_fragment.CourseContent
import com.example.lms.ui.home.fragments.courses_fragment.material.Variables
import com.google.android.material.tabs.TabLayout

class AssignmentsActivity : AppCompatActivity() {
    lateinit var viewBinding : ActivityAssimmentsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityAssimmentsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.courseNameTv.text = Variables.courseName
        viewBinding.icBack.setOnClickListener {
            navigateToCourseContent()
        }
        viewBinding.tabLayout.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    if(tab?.text == "Pending")
                        pushFragment(AssignmentPendingFragment())
                    else
                        pushFragment(AssignmentCompletedFragment())

                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    if(tab?.text == "Pending")
                        pushFragment(AssignmentPendingFragment())
                    else
                        pushFragment(AssignmentCompletedFragment())
                }

            }
        )
        bindTabs()
    }

    private fun navigateToCourseContent() {
        val intent = Intent(this,CourseContent::class.java)
        startActivity(intent)
        this.finish()
    }

    private fun pushFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.assignments_container,fragment)
            .commit()
    }

    private fun bindTabs() {
        val tab1 = viewBinding.tabLayout.newTab()
        tab1.text = "Pending"
        viewBinding.tabLayout.addTab(tab1)
        val tab2 = viewBinding.tabLayout.newTab()
        tab2.text = "Completed"
        viewBinding.tabLayout.addTab(tab2)
    }
}