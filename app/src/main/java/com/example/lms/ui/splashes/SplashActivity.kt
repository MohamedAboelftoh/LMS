package com.example.lms.ui.splashes

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.lms.R
import com.example.lms.databinding.ActivitySplashBinding
import com.example.lms.ui.home.HomeActivity
import com.example.lms.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    private var index : Int = 0
    private lateinit var viewBinding : ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        pushFragment(SplashFragment1())
        viewBinding.btnNext.setOnClickListener {
            if(index ==  0) {
                changeColors(0)
                pushFragment(SplashFragment2())
                index = 1
                return@setOnClickListener
            }
            if(index ==  1) {
                changeColors(1)
                pushFragment(SplashFragment3())
                index = 2
                return@setOnClickListener
            }
            if(index == 2) {
                if (isLoggedIn()) {
                    navigateToHome()
                } else {
                    navigateToLogin()
                }
            }
        }
    }

    private fun changeColors(index: Int) {
        if(index == 0){
            viewBinding.iv2.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.colorPrimary))
            viewBinding.iv1.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.gray))
            viewBinding.iv3.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.gray))
        }else if(index == 1){
            viewBinding.iv3.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.colorPrimary))
            viewBinding.iv1.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.gray))
            viewBinding.iv2.setBackgroundColor(ContextCompat.getColor(applicationContext, R.color.gray))
        }
    }


    private fun navigateToLogin() {
        val intent = Intent(this , LoginActivity::class.java)
        startActivity(intent)
    }

    private fun pushFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
            .replace(R.id.splash_container,fragment)
            .commit()
    }
    private fun isLoggedIn(): Boolean {
        val sharedPreferences: SharedPreferences = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("email", null)
        val password = sharedPreferences.getString("password", null)

        // Check if both email and password are present
        return !email.isNullOrEmpty() && !password.isNullOrEmpty()
    }
    private fun navigateToHome() {
        val intent = Intent(this , HomeActivity::class.java)
        startActivity(intent)
    }
}