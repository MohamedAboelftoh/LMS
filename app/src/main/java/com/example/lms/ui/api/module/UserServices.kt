package com.example.lms.ui.api.module

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserServices {
    @POST("api/Account/login")
    fun userLogin(@Body loginRequest: LoginRequest) : Call<LoginResponse>
}