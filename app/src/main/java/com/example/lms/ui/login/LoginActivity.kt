package com.example.lms.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.lms.databinding.ActivityLoginBinding
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.login.LoginRequest
import com.example.lms.ui.api.login.LoginResponse
import com.example.lms.ui.home.HomeActivity
import com.example.lms.ui.resetPassword.ResetPasswordActivity
import com.example.lms.ui.splashes.SplashActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
     private lateinit var viewBinding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.forgetPass.setOnClickListener {
            navigateToAnotherActivity(ResetPasswordActivity())
        }
        viewBinding.iconBack.setOnClickListener {
            navigateToAnotherActivity(SplashActivity())
        }
        viewBinding.btnLogin.setOnClickListener {
            viewBinding.progrssBar.visibility = View.VISIBLE
            login()
        }
    }

    private fun login() {
        if(!validation()){
            viewBinding.progrssBar.visibility = View.INVISIBLE
            return
        }
        val loginRequest = LoginRequest(viewBinding.etPassword.text.toString(),viewBinding.etEmail.text.toString())
        val loginResponseCall : Call<LoginResponse> = ApiManager.getApi().userLogin(loginRequest)
        loginResponseCall.enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if(response.isSuccessful){
                    viewBinding.progrssBar.visibility = View.INVISIBLE
                    val toast = Toast.makeText(this@LoginActivity, "Login successful", Toast.LENGTH_LONG)
                    toast.show()
                    navigateToAnotherActivity(HomeActivity())
                }
                else{
                    viewBinding.progrssBar.visibility = View.INVISIBLE
                    val toast = Toast.makeText(this@LoginActivity, "Login failed", Toast.LENGTH_LONG)
                    toast.show()
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                viewBinding.progrssBar.visibility = View.INVISIBLE
                val toast = Toast.makeText(this@LoginActivity, "Throwable"+t.localizedMessage, Toast.LENGTH_LONG)
                toast.show()
            }
        })
    }

    private fun navigateToAnotherActivity(activity:AppCompatActivity) {
        val intent = Intent(this,activity::class.java)
         startActivity(intent)
    }
    private fun validation(): Boolean {
        var isvaild : Boolean = true
        if(viewBinding.etEmail.text.isNullOrBlank())
        {
            viewBinding.etEmail.error = "Email is require"
            isvaild = false
        }
        else{
            viewBinding.etEmail.error = null
        }
        if(viewBinding.etPassword.text.isNullOrBlank())
        {
            viewBinding.etPassword.error = "Password is require"
            isvaild = false
        }
        else{
            viewBinding.etPassword.error = null
        }
        return isvaild
    }

}