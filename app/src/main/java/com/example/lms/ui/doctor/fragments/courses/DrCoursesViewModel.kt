package com.example.lms.ui.doctor.fragments.courses

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lms.ui.api.api_doctor.dr_courses.DrCoursesResponseItem
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.db.DataBase
import com.example.lms.ui.student.fragments.Variables
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DrCoursesViewModel() : ViewModel() {
    val coursesList  = MutableLiveData<ArrayList<DrCoursesResponseItem>>()
    val toastMessage = MutableLiveData<String>()
     fun uploadCourses(token : String?) {
        ApiManager.getApi().getAllDrCourses(token!!).enqueue(object :
            Callback<ArrayList<DrCoursesResponseItem>> {
            override fun onResponse(
                call: Call<ArrayList<DrCoursesResponseItem>>,
                response: Response<ArrayList<DrCoursesResponseItem>>
            ) {
                if (response.isSuccessful) {
                    coursesList.postValue(response.body())
                }
                else{
                    toastMessage.postValue("something error")
                }
            }
            override fun onFailure(call: Call<ArrayList<DrCoursesResponseItem>>, t: Throwable) {
                toastMessage.postValue(t.localizedMessage)
            }
        })
    }

}