package com.example.lms.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.lms.R
import com.example.lms.databinding.ActivityHomeBinding
import com.example.lms.ui.api.login.LoginResponse
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.home.fragments.calender.CalenderFragment
import com.example.lms.ui.home.fragments.account.AccountFragment
import com.example.lms.ui.home.fragments.courses_fragment.CoursesFragment
import com.example.lms.ui.home.fragments.home_fragment.HomeFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {
    lateinit var viewBinding:ActivityHomeBinding
    lateinit var myPreferencesToken:MyPreferencesToken

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myPreferencesToken=MyPreferencesToken(this)

        viewBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        pushFragment(HomeFragment())
        bottomNavigationSelected()

        openDrawer()
        getCurrentUser()
    }
    fun bottomNavigationSelected(){
        viewBinding.content.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.ic_hom->{ pushFragment(HomeFragment())}
                R.id.ic_account->{ pushFragment(AccountFragment())}
                R.id.ic_calender->{ pushFragment(CalenderFragment())}
                R.id.ic_courses->{ pushFragment(CoursesFragment())}

            }
//
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

    override fun onBackPressed() {

            // Check if the current fragment is not the HomeFragment
            if (getCurrentFragment() !is HomeFragment) {
                pushFragment(HomeFragment())
            } else {
                super.onBackPressed() // If already on the HomeFragment, perform default back button behavior
            }
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
    private fun getCurrentUser() {
        val token=myPreferencesToken.loadData("token")
        ApiManager.getApi().getCurrentUser(token!!).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if(response.isSuccessful){
                    val header = viewBinding.navView.getHeaderView(0)
                    val dateTextView = header.findViewById<TextView>(R.id.name_drawer_tv)
                    dateTextView.text= "Hi,"+response.body()?.displayName
                }else{
                    val toast = Toast.makeText(this@HomeActivity, "current user fail", Toast.LENGTH_LONG)
                    toast.show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                val toast = Toast.makeText(this@HomeActivity, t.localizedMessage, Toast.LENGTH_LONG)
                toast.show()
            }
        })
    }

}