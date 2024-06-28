package com.example.lms.ui.doctor.fragments.courses

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lms.ui.api.api_doctor.dr_courses.DrCoursesResponseItem
import com.example.lms.ui.api.module.ApiManager
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