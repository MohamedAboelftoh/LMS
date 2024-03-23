package com.example.lms.ui.doctor.fragments.courses.assignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.lms.R
import com.example.lms.databinding.ActivityDrAssignBinding
import com.example.lms.ui.doctor.fragments.courses.DrCourseContentActivity
import com.example.lms.ui.student.fragments.Variables
import com.example.lms.ui.student.fragments.courses_fragment.assignments.AssignmentCompletedFragment
import com.example.lms.ui.student.fragments.courses_fragment.assignments.AssignmentPendingFragment
import com.example.lms.ui.student.navigateFromActivity
import com.google.android.material.tabs.TabLayout

class DrAssignActivity : AppCompatActivity() {
    lateinit var viewBinding:ActivityDrAssignBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding=ActivityDrAssignBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.courseNameTv.text = Variables.courseName
        viewBinding.icBack.setOnClickListener{
            navigateFromActivity(this@DrAssignActivity,DrCourseContentActivity())
        }
        viewBinding.tabLayout.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    if(tab?.text == "Pending")
                        pushFragment(DrAssignmentPendingFragment())
                    else
                        pushFragment(DrAssignmentCompletedFragment())

                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    if(tab?.text == "Pending")
                        pushFragment(DrAssignmentPendingFragment())
                    else
                        pushFragment(DrAssignmentCompletedFragment())
                }

            }
        )
        bindTabs()
        viewBinding.floatingActionBtn.setOnClickListener{
            navigateFromActivity(this@DrAssignActivity,DrAddAssignmentActivity())
        }
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