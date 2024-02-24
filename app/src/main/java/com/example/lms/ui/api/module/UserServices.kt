package com.example.lms.ui.api.module

import com.example.lms.ui.api.courses.CoursesResponse
import com.example.lms.ui.api.login.LoginRequest
import com.example.lms.ui.api.login.LoginResponse
import com.example.lms.ui.api.material.CourseMaterialResponse
import com.example.lms.ui.api.news.NewsResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface UserServices {
    @POST("api/Account/login")
    fun userLogin(@Body loginRequest: LoginRequest) : Call<LoginResponse>
    @GET("api/News")
    fun getNews(): Call<NewsResponse>
    @GET("api/Account/GetCurrentUser")
    fun getCurrentUser(@Header("Authorization") token : String): Call<LoginResponse>

    @GET("api/Students/CurrentCourcesInfo")
    fun getAllCourses(@Header("Authorization")token:String): Call<CoursesResponse>

    @GET("api/Students/CurrentCourseMaterial")
    fun getCourseMaterial(@Header("Authorization")token:String
                          ,@Query("cycleId")cycleId:String
    ):Call<CourseMaterialResponse>
}