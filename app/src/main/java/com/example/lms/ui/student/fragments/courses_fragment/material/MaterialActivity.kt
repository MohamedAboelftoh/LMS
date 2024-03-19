package com.example.lms.ui.student.fragments.courses_fragment.material

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.lms.R
import com.example.lms.databinding.ActivityMaterialBinding
import com.example.lms.ui.student.fragments.Variables
import com.example.lms.ui.student.fragments.courses_fragment.CourseContent
import com.example.lms.ui.student.navigateFromActivity
import com.google.android.material.tabs.TabLayout

class MaterialActivity : AppCompatActivity() {
    lateinit var viewBinding:ActivityMaterialBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding=ActivityMaterialBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.courseNameTv.text = Variables.courseName
        pushFragment(LectureFragment())
        viewBinding.icBack.setOnClickListener {
            navigateFromActivity(this@MaterialActivity,CourseContent())
        }
        viewBinding.tabLayout.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    if(tab?.text == "Lectures")
                        pushFragment(LectureFragment())
                    else
                        pushFragment(LabsFragment())
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    if(tab?.text == "Labs")
                        pushFragment(LabsFragment())
                    else
                        pushFragment(LectureFragment())
                }

            }
        )
        bindTabs()

    }

    override fun onBackPressed() {
        super.onBackPressed()
        navigateFromActivity(this@MaterialActivity,CourseContent())
    }
    private fun pushFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.lec_container,fragment)
            .commit()
    }

    private fun bindTabs() {
        val tab1 = viewBinding.tabLayout.newTab()
        tab1.text = "Lectures"
        viewBinding.tabLayout.addTab(tab1)
        val tab2 = viewBinding.tabLayout.newTab()
        tab2.text = "Labs"
        viewBinding.tabLayout.addTab(tab2)
    }
}




