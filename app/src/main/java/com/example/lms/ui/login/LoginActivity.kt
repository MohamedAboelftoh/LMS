package com.example.lms.ui.login
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.lms.R
import com.example.lms.databinding.ActivityLoginBinding
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.doctor.DrMainActivity
import com.example.lms.ui.resetPassword.ResetPasswordActivity
import com.example.lms.ui.splashes.SplashActivity
import com.example.lms.ui.student.HomeActivity
import com.example.lms.ui.student.navigateFromActivity

class LoginActivity : AppCompatActivity() {
     private lateinit var viewBinding : ActivityLoginBinding
     lateinit var myPreferencesToken : MyPreferencesToken
     private lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        initViews()
        subscribeObservers()
        viewsClicks()
    }

    private fun initViews() {
        viewModel = ViewModelProvider(this@LoginActivity)[LoginViewModel::class.java]
        myPreferencesToken = MyPreferencesToken(this)
        viewBinding.lifecycleOwner = this
        viewBinding.vm = viewModel
        viewModel.getMyPreferencesToken(myPreferencesToken)
    }

    private fun viewsClicks() {
        viewBinding.forgetPass.setOnClickListener {
            navigateFromActivity(this, ResetPasswordActivity())
        }
        viewBinding.iconBack.setOnClickListener {
            navigateFromActivity(this, SplashActivity())
        }
        viewBinding.btnLogin.setOnClickListener {
            when (viewBinding.radioGroup.checkedRadioButtonId) {
                R.id.student -> {
                    viewBinding.progrssBar.visibility = View.VISIBLE
                    viewModel.studentLogin()
                }

                R.id.doctor -> {
                    viewBinding.progrssBar.visibility = View.VISIBLE
                    viewModel.doctorLogin()
                }
            }
        }
    }

    private fun subscribeObservers() {
        viewModel.events.observe(this){
            if(it.equals(Events.DrNavigateToHome)){
                navigateFromActivity(this,DrMainActivity())
            }
            else if (it.equals(Events.StudentNavigateToHome)){
                navigateFromActivity(this,HomeActivity())
            }
        }
        viewModel.toastMessages.observe(this){
            Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
        }
    }

//    private fun doctorLogin() {
//        if(!validation()){
//            viewBinding.progrssBar.visibility = View.INVISIBLE
//            return
//        }
//        val email =viewBinding.etEmail.text.toString()
//        val password =viewBinding.etPassword.text.toString()
//        val loginRequest = LoginRequest(password,email)
//        val loginResponseCall : Call<LoginResponse> = ApiManager.getApi().userLogin(loginRequest)
//        loginResponseCall.enqueue(object : Callback<LoginResponse>{
//            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
//                if(response.isSuccessful){
//                    viewBinding.progrssBar.visibility = View.INVISIBLE
//                    //save email& password , token in sherdPreferences
//                    val token="Bearer "+response.body()?.token
//                    val role=response.body()?.userRole
//                    saveCredentials(email,password,token,role!!)
//                    if (response.body()?.userRole == "Doctor"){
//                        navigateFromActivity(this@LoginActivity,
//                            DrMainActivity()
//                        )
//                    }
//                    else{
//                        val toast = Toast.makeText(this@LoginActivity, "You Are Student", Toast.LENGTH_LONG)
//                        toast.show()
//                    }
//                }
//                else{
//                    viewBinding.progrssBar.visibility = View.INVISIBLE
//                    val toast = Toast.makeText(this@LoginActivity, "Login failed", Toast.LENGTH_LONG)
//                    toast.show()
//                }
//            }
//            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
//                viewBinding.progrssBar.visibility = View.INVISIBLE
//                val toast = Toast.makeText(this@LoginActivity, "Throwable"+t.localizedMessage, Toast.LENGTH_LONG)
//                toast.show()
//            }
//        })
//    }

//    private fun saveCredentials(email: String, password: String , token: String,role:String) {
//        myPreferencesToken.saveData("email", email)
//        myPreferencesToken.saveData("password", password)
//        myPreferencesToken.saveData("token",token)
//        myPreferencesToken.saveData("role",role)
//    }
//    private fun studentLogin() {
//        if(!validation()){
//            viewBinding.progrssBar.visibility = View.INVISIBLE
//            return
//        }
//        val email =viewBinding.etEmail.text.toString()
//        val password =viewBinding.etPassword.text.toString()
//        val loginRequest = LoginRequest(password,email)
//        val loginResponseCall : Call<LoginResponse> = ApiManager.getApi().userLogin(loginRequest)
//        loginResponseCall.enqueue(object : Callback<LoginResponse>{
//            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
//                if(response.isSuccessful){
//                    viewBinding.progrssBar.visibility = View.INVISIBLE
//                    //save email& password , token in sherdPreferences
//                    val token="Bearer "+response.body()?.token
//                    val role =response.body()?.userRole
//                    saveCredentials(email,password,token,role!!)
//                    if (response.body()?.userRole == "Student"){
//                        navigateFromActivity(this@LoginActivity,
//                            HomeActivity()
//                        )
//                    }
//                    else{
//                        val toast = Toast.makeText(this@LoginActivity, "You Are Doctor", Toast.LENGTH_LONG)
//                        toast.show()
//                    }
//                }
//                else{
//                    viewBinding.progrssBar.visibility = View.INVISIBLE
//                    val toast = Toast.makeText(this@LoginActivity, "Login failed", Toast.LENGTH_LONG)
//                    toast.show()
//                }
//            }
//            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
//                viewBinding.progrssBar.visibility = View.INVISIBLE
//                val toast = Toast.makeText(this@LoginActivity, "Throwable"+t.localizedMessage, Toast.LENGTH_LONG)
//                toast.show()
//            }
//        })
//    }

//    private fun validation(): Boolean {
//        var isvaild : Boolean = true
//        if(viewBinding.etEmail.text.isNullOrBlank())
//        {
//            viewBinding.etEmail.error = "Email is require"
//            isvaild = false
//        }
//        else{
//            viewBinding.etEmail.error = null
//        }
//        if(viewBinding.etPassword.text.isNullOrBlank())
//        {
//            viewBinding.etPassword.error = "Password is require"
//            isvaild = false
//        }
//        else{
//            viewBinding.etPassword.error = null
//        }
//        return isvaild
//    }

}