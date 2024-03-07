package com.example.lms.ui.home

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.lms.R
import com.example.lms.databinding.ActivityHomeBinding
import com.example.lms.ui.api.login.LoginResponse
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.home.fragments.account.AccountFragment
import com.example.lms.ui.home.fragments.calender.CalenderFragment
import com.example.lms.ui.home.fragments.courses_fragment.CoursesFragment
import com.example.lms.ui.home.fragments.courses_fragment.assignments.AssignmentsActivity
import com.example.lms.ui.home.fragments.drawer_grades.AllCoursesGrades
import com.example.lms.ui.home.fragments.home_fragment.HomeFragment
import com.example.lms.ui.login.LoginActivity
import com.example.lms.ui.splashes.SplashActivity
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
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
        drawerItemsSelected()
    }
    fun drawerItemsSelected(){
        viewBinding.navView.setNavigationItemSelectedListener{menuItem->
            when(menuItem.itemId){
                R.id.ic_account_info->{
                    viewBinding.drawerLayout.closeDrawer(GravityCompat.START)
                    pushFragment(AccountFragment())
                    viewBinding.content.bottomNavigation.menu.findItem(R.id.ic_account).isChecked = true
                }
                R.id.ic_logout->{
                    showMessage("Do you Sure To Logout "
                        ,posActionName = "OK",
                        posAction = { dialogInterface,i->
                            dialogInterface.dismiss()
                            logout()
                        },
                        negActionName = "Cansel"
                        , negAction = { dialogInterface, i ->
                            dialogInterface.dismiss()
                        }
                    )
                }
                R.id.ic_grades->{
                    navigateFromActivity(this@HomeActivity, AllCoursesGrades())
                }
            }
            true
        }
    }

    private fun logout() {
        saveCredentials("","")
        navigateToLoginActivity()
    }
    private fun navigateToLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }
    private fun saveCredentials(email: String, password: String) {
          myPreferencesToken.saveData("email", email)
          myPreferencesToken.saveData("password", password)
//        val sharedPreferences: SharedPreferences = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
//        val editor: SharedPreferences.Editor = sharedPreferences.edit()
//        editor.putString("email", email)
//        editor.putString("password", password)
//        editor.apply()
    }
    fun showMessage(message:String
                    ,posActionName:String?=null
                    ,posAction: DialogInterface.OnClickListener?=null
                    ,negActionName:String?=null
                    ,negAction: DialogInterface.OnClickListener?=null

    ): AlertDialog {
        val dialogBuilder= AlertDialog.Builder(this)
        dialogBuilder.setMessage(message)
        if (posActionName!=null)
        {
            dialogBuilder.setPositiveButton(posActionName,posAction)
        }
        if(negActionName!=null)
        {
            dialogBuilder.setNegativeButton(negActionName,negAction)

        }
        return dialogBuilder.show()
    }

    private fun bottomNavigationSelected(){
        viewBinding.content.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.ic_hom->{ pushFragment(HomeFragment())}
                R.id.ic_account->{ pushFragment(AccountFragment())}
                R.id.ic_calender->{ pushFragment(CalenderFragment())}
                R.id.ic_courses->{ pushFragment(CoursesFragment())}

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

    override fun onBackPressed() {
        if(viewBinding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            viewBinding.drawerLayout.closeDrawer(GravityCompat.START)
        }
        else {
            if (getCurrentFragment() !is HomeFragment) {
                pushFragment(HomeFragment())
                viewBinding.content.bottomNavigation.menu.findItem(R.id.ic_hom).isChecked = true
            } else {
                super.onBackPressed()
                navigateFromActivity(this@HomeActivity,SplashActivity())
            }
        }
    }



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