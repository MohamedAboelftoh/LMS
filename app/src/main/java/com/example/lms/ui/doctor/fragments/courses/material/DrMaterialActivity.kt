package com.example.lms.ui.doctor.fragments.courses.material

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.lms.R
import com.example.lms.databinding.ActivityDrMaterialBinding
import com.example.lms.ui.doctor.fragments.courses.DrCourseContentActivity
import com.example.lms.ui.student.fragments.Variables
import com.example.lms.ui.student.navigateFromActivity
import com.google.android.material.tabs.TabLayout

class DrMaterialActivity : AppCompatActivity() {
    lateinit var viewBinding: ActivityDrMaterialBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding=ActivityDrMaterialBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.courseNameTv.text = Variables.courseName
        pushFragment(DrLectureFragment())
        viewBinding.icBack.setOnClickListener {
            navigateFromActivity(this@DrMaterialActivity, DrCourseContentActivity())
        }
        viewBinding.tabLayout.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    if(tab?.text == "Lectures")
                        pushFragment(DrLectureFragment())
                    else
                        pushFragment(DrLabsFragment())
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    if(tab?.text == "Labs")
                        pushFragment(DrLabsFragment())
                    else
                        pushFragment(DrLectureFragment())
                }

            }
        )
        bindTabs()


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