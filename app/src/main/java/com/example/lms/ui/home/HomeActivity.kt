package com.example.lms.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
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
            .addToBackStack(null)
            .commit()
    }

    override fun onBackPressed() {
        if (viewBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            // Close the navigation drawer if it's open
            viewBinding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            // Check if the current fragment is not the HomeFragment
            if (getCurrentFragment() !is HomeFragment) {
                pushHomeFragmentAfterDelay()
            } else {
                super.onBackPressed() // If already on the HomeFragment, perform default back button behavior
            }
        }
    }

    private fun pushHomeFragmentAfterDelay() {
        Handler().postDelayed({
            val homeFragment = HomeFragment()
            pushFragment(homeFragment) // Navigate to the HomeFragment
        }, 100) // Delay of 100 milliseconds
    }

    // Function to get the current fragment
    private fun getCurrentFragment(): Fragment? {
        return supportFragmentManager.findFragmentById(R.id.home_container)
    }
    private fun openDrawer(){
        viewBinding.content.icDrawer.setOnClickListener {
            viewBinding.drawerLayout.openDrawer(GravityCompat.START)
           }
    }


}