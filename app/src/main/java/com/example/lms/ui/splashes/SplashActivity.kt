package com.example.lms.ui.splashes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.lms.R
import com.example.lms.databinding.ActivitySplashBinding
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
            if(index ==  0)
                pushFragment(SplashFragment2())
            if(index == 1)
                navigateToLogin()
            index = 1
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this , LoginActivity::class.java)
        startActivity(intent)
    }

    private fun pushFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.splash_container,fragment)
            .addToBackStack(null)
            .commit()
    }

}