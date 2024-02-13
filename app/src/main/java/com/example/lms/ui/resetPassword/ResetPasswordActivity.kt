package com.example.lms.ui.resetPassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.lms.R
import com.example.lms.databinding.ActivityResetPasswordBinding
import com.example.lms.ui.login.LoginActivity

class ResetPasswordActivity : AppCompatActivity() {
    private lateinit var viewBinding : ActivityResetPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        pushFragment(ResetPasswordFragment1())
        viewBinding.iconBack.setOnClickListener {
           navigateToLoginActivity()
        }
    }

    private fun pushFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.resetPassword_container,fragment)
            .commit()
    }
    private fun navigateToLoginActivity() {
        val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}