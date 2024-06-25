package com.example.lms.ui.login
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lms.ui.api.login.LoginRequest
import com.example.lms.ui.api.login.LoginResponse
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {
    lateinit var myPreferencesToken : MyPreferencesToken
    var email = MutableLiveData<String?>()
    var password = MutableLiveData<String?>()
    var errorEmail = MutableLiveData<String?>()
    var errorPassword = MutableLiveData<String?>()
    var isLoading = MutableLiveData<Boolean>()
    var events = MutableLiveData<Events>()
    var toastMessages = MutableLiveData<String>()

    fun getMyPreferencesToken(myPreferencesToken : MyPreferencesToken){
         this.myPreferencesToken = myPreferencesToken
    }
     fun studentLogin() {
        if(!validation()){
            isLoading.postValue(false)
            return
        }
        val loginRequest = LoginRequest(password.value,email.value)
        val loginResponseCall : Call<LoginResponse> = ApiManager.getApi().userLogin(loginRequest)
        loginResponseCall.enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if(response.isSuccessful){
                    isLoading.postValue(false)
                    //save email& password , token in sherdPreferences
                    val token="Bearer "+response.body()?.token
                    val role =response.body()?.userRole
                    saveCredentials(email.value?:"",password.value?:"",token,role!!)
                    if (response.body()?.userRole == "Student"){
                        events.postValue(Events.StudentNavigateToHome)
                    }
                    else{
                        toastMessages.postValue("You Are Doctor")
                    }
                }
                else{
                    isLoading.postValue(false)
                    toastMessages.postValue("Login failed")
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                isLoading.postValue(false)
                toastMessages.postValue("Throwable"+t.localizedMessage)
            }
        })
    }
     fun doctorLogin() {
        if(!validation()){
            isLoading.postValue(false)
            return
        }
        val loginRequest = LoginRequest(password.value,email.value)
        val loginResponseCall : Call<LoginResponse> = ApiManager.getApi().userLogin(loginRequest)
        loginResponseCall.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if(response.isSuccessful){
                    isLoading.postValue(false)
                    //save email& password , token in sherdPreferences
                    val token="Bearer "+response.body()?.token
                    val role=response.body()?.userRole
                    saveCredentials(email.value?:"",password.value?:"",token,role!!)
                    if (response.body()?.userRole == "Doctor" && response.isSuccessful){
                        events.postValue(Events.DrNavigateToHome)
                    }
                    else{
                        toastMessages.postValue("You Are Student")
                    }
                }
                else{
                    isLoading.postValue(false)
                    toastMessages.postValue("Login failed")
                }
            }
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                isLoading.postValue(false)
                toastMessages.postValue("Throwable"+t.localizedMessage)
            }
        })
    }
    private fun saveCredentials(email: String, password: String , token: String,role:String) {
        myPreferencesToken.saveData("email", email)
        myPreferencesToken.saveData("password", password)
        myPreferencesToken.saveData("token",token)
        myPreferencesToken.saveData("role",role)
    }
    private fun validation(): Boolean {
        var isvaild : Boolean = true
        if(email.value.isNullOrBlank())
        {
            errorEmail.value= "Email is require"
            isvaild = false
        }
        else{
            errorEmail.value = null
        }
        if(password.value.isNullOrBlank())
        {
            errorPassword.value = "Password is require"
            isvaild = false
        }
        else{
            errorPassword.value = null
        }
        return isvaild
    }
}