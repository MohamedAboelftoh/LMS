package com.example.lms.ui.doctor

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.lms.R
import com.example.lms.databinding.ActivityDoctorMainBinding
import com.example.lms.ui.api.api_doctor.InstructorInfoResponse
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.db.DataBase
import com.example.lms.ui.doctor.fragments.account.DrAccountFragment
import com.example.lms.ui.doctor.fragments.courses.DrCoursesFragment
import com.example.lms.ui.doctor.fragments.news.DrNewsFragment
import com.example.lms.ui.login.LoginActivity
import com.example.lms.ui.splashes.SplashActivity
import com.example.lms.ui.student.checkForInternet
import com.example.lms.ui.student.fragments.calender.CalenderFragment
import com.example.lms.ui.student.fragments.home_fragment.HomeFragment
import com.example.lms.ui.student.navigateFromActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DrMainActivity : AppCompatActivity() {
    lateinit var viewBinding: ActivityDoctorMainBinding
    lateinit var myPreferencesToken: MyPreferencesToken
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding=ActivityDoctorMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        myPreferencesToken= MyPreferencesToken(this)
        pushFragment(DrNewsFragment())
        bottomNavigationSelected()
        openDrawer()
        if(checkForInternet(applicationContext)){
            getCurrentUser()
        }
        else{
            getCurrentUserFromLocal()
        }
        drawerItemsSelected()
    }
    override fun onBackPressed() {
        if(viewBinding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            viewBinding.drawerLayout.closeDrawer(GravityCompat.START)
        }
        else {
            if (getCurrentFragment() !is DrNewsFragment) {
                pushFragment(DrNewsFragment())
                viewBinding.content.bottomNavigation.menu.findItem(R.id.ic_hom).isChecked = true
            } else {
                super.onBackPressed()
                navigateFromActivity(this@DrMainActivity, SplashActivity())
            }
        }
    }
    private fun getCurrentFragment(): Fragment? {
        return supportFragmentManager.findFragmentById(R.id.home_dr_container)
    }
    private fun bottomNavigationSelected(){
        viewBinding.content.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.ic_hom->{ pushFragment(DrNewsFragment())}
                R.id.ic_account->{ pushFragment(DrAccountFragment())}
                R.id.ic_calender->{ pushFragment(CalenderFragment())}
                R.id.ic_courses->{ pushFragment(DrCoursesFragment())}

            }
            true
        }
    }
    private fun pushFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.home_dr_container,fragment)
            .addToBackStack(null)
            .commit()
    }
    private fun getCurrentUserFromLocal() {
        val header = viewBinding.navView.getHeaderView(0)
        val dateTextView = header.findViewById<TextView>(R.id.name_drawer_tv)
         val imgProfile = header.findViewById<ImageView>(R.id.profile_header)
        dateTextView.text= DataBase.getInstance(this).instructorDao().getInstructorFromLocal().fullName?:"User"

        val imgPath= DataBase.getInstance(this).instructorDao().getInstructorFromLocal().imagePath
        Glide.with(this)
            .load(imgPath ?: R.drawable.avatar_1)  // Use placeholder if imagePath is null
            .placeholder(R.drawable.avatar_1)
            .into(imgProfile)
    }
    private fun getCurrentUser() {
        val header = viewBinding.navView.getHeaderView(0)
        val dateTextView = header.findViewById<TextView>(R.id.name_drawer_tv)
         val imgProfile = header.findViewById<ImageView>(R.id.profile_header)

        val token=myPreferencesToken.loadData("token")
        ApiManager.getApi().getInstructorInfo(token!!).enqueue(object : Callback<InstructorInfoResponse> {
            override fun onResponse(
                call: Call<InstructorInfoResponse>,
                response: Response<InstructorInfoResponse>
            ) {
                if(response.isSuccessful){
                    dateTextView.text= "Hi,"+response.body()?.fullName

                    Glide.with(viewBinding.navView)
                        .load(response.body()?.imagePath)
                        .placeholder(R.drawable.avatar_1)
                        .into(imgProfile)
                }else {
                    val toast = Toast.makeText(this@DrMainActivity, "current user fail", Toast.LENGTH_LONG)
                    toast.show()
                }
            }
            override fun onFailure(call: Call<InstructorInfoResponse>, t: Throwable) {
                val toast = Toast.makeText(this@DrMainActivity, "header", Toast.LENGTH_LONG)
                toast.show()
            }
        })
    }
    private fun drawerItemsSelected(){
        viewBinding.navView.setNavigationItemSelectedListener{menuItem->
            when(menuItem.itemId){
                R.id.ic_account_info->{
                    viewBinding.drawerLayout.closeDrawer(GravityCompat.START)
                    pushFragment(DrAccountFragment())
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
            }
            true
        }
    }
    private fun showMessage(message:String
                            , posActionName:String?=null
                            , posAction: DialogInterface.OnClickListener?=null
                            , negActionName:String?=null
                            , negAction: DialogInterface.OnClickListener?=null

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
    private fun logout() {
        saveCredentials()
       navigateFromActivity(this,LoginActivity())
    }
    private fun saveCredentials() {
        myPreferencesToken.saveData("email", "")
        myPreferencesToken.saveData("password", "")
    }
    private fun openDrawer(){
        viewBinding.content.icDrawer.setOnClickListener {
            viewBinding.drawerLayout.openDrawer(GravityCompat.START)
        }
    }
}