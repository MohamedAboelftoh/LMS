package com.example.lms.ui.doctor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.lms.R
import com.example.lms.databinding.ActivityDoctorMainBinding
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.doctor.fragments.account.DrAccountFragment
import com.example.lms.ui.doctor.fragments.calender.DrCalenderFragment
import com.example.lms.ui.doctor.fragments.courses.DrCoursesFragment
import com.example.lms.ui.doctor.fragments.news.DrNewsFragment

class DrMainActivity : AppCompatActivity() {
    lateinit var viewBinding: ActivityDoctorMainBinding
    lateinit var myPreferencesToken: MyPreferencesToken
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding=ActivityDoctorMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        pushFragment(DrNewsFragment())
        bottomNavigationSelected()
    }

    private fun bottomNavigationSelected(){
        viewBinding.content.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.ic_hom->{ pushFragment(DrNewsFragment())}
                R.id.ic_account->{ pushFragment(DrAccountFragment())}
                R.id.ic_calender->{ pushFragment(DrCalenderFragment())}
                R.id.ic_courses->{ pushFragment(DrCoursesFragment())}

            }
            true
        }
    }
    private fun pushFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.home_container,fragment)
            .addToBackStack(null)
            .commit()
    }
}