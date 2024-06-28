package com.example.lms.ui.resetPassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.lms.databinding.ActivityChangePassword2Binding
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.api.password.ChangePasswordResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePasswordActivity : AppCompatActivity() {
    lateinit var viewBinding : ActivityChangePassword2Binding
    lateinit var myPreferencesToken: MyPreferencesToken
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myPreferencesToken = MyPreferencesToken(this)
        viewBinding = ActivityChangePassword2Binding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.icBack.setOnClickListener {
            finish()
        }
        viewBinding.btnSubmit.setOnClickListener {
            if(!validation())return@setOnClickListener
            changePassword()
        }
    }
    private fun changePassword() {
        val token = myPreferencesToken.loadData("token")
        val oldPassword = viewBinding.etOldPassword.text.toString()
        val newPassword = viewBinding.etNewPassword.text.toString()
        val changePasswordResponse = ChangePasswordResponse(newPassword,oldPassword)
        ApiManager.getApi().changePassword(changePasswordResponse , token!!).enqueue(object : Callback<ResponseBody>{
            override fun onResponse(p0: Call<ResponseBody>, p1: Response<ResponseBody>) {
                if(p1.isSuccessful){
                    Toast.makeText(this@ChangePasswordActivity,"Password Changed" , Toast.LENGTH_LONG).show()
                    finish()
                }else{
                    Toast.makeText(this@ChangePasswordActivity,"Password Mismatch" , Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(p0: Call<ResponseBody>, p1: Throwable) {
                Toast.makeText(this@ChangePasswordActivity,"onFailure" , Toast.LENGTH_LONG).show()
            }
        })
    }
    private fun validation():Boolean{
        var isValid = true
        if(viewBinding.etOldPassword.text.isNullOrBlank()){
            viewBinding.textInputOldPassword.error = "Required"
            isValid = false
        }else{
            viewBinding.textInputOldPassword.error = null
        }
        if(viewBinding.etNewPassword.text.isNullOrBlank()){
            viewBinding.textInputNewPassword.error = "Required"
            isValid = false
        }else{
            viewBinding.textInputNewPassword.error = null
        }
        return isValid
    }
}