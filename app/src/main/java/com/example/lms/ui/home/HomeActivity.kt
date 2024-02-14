package com.example.lms.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.lms.R
import com.example.lms.databinding.ActivityHomeBinding
import com.example.lms.ui.home.fragments.CalenderFragment
import com.example.lms.ui.home.fragments.account.AccountFragment
import com.example.lms.ui.home.fragments.courses_fragment.CoursesFragment
import com.example.lms.ui.home.fragments.home_fragment.HomeFragment

class HomeActivity : AppCompatActivity() {
    lateinit var viewBinding:ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        pushFragment(HomeFragment())
        viewBinding.content.bottomNavigation.setOnItemSelectedListener { item ->
            if (item.itemId == R.id.ic_hom) {
                pushFragment(HomeFragment())
            }
            if (item.itemId == R.id.ic_account) {
                pushFragment(AccountFragment())
            }
            if (item.itemId == R.id.ic_calender) {
                pushFragment(CalenderFragment())
            }
            if (item.itemId == R.id.ic_courses) {
                pushFragment(CoursesFragment())
            }
            true
        }

        openDrawer()
    }

    private fun pushFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.home_container,fragment)
            .commit()
    }

    private fun openDrawer(){
        viewBinding.content.icDrawer.setOnClickListener {
            viewBinding.drawerLayout.openDrawer(GravityCompat.START)
           }
    }


}