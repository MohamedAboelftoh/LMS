package com.example.lms.ui.api.module

import com.example.lms.ui.api.login.LoginRequest
import com.example.lms.ui.api.login.LoginResponse
import com.example.lms.ui.api.news.NewsResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserServices {
    @POST("api/Account/login")
    fun userLogin(@Body loginRequest: LoginRequest) : Call<LoginResponse>
    @GET("api/News")
    fun getNews(): Call<NewsResponse>
    @GET("api/Account/GetCurrentUser")
    fun getCurrentUser(@Header("Authorization") token : String): Call<LoginResponse>
}