package com.example.lms.ui.api.module

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiManager {
    companion object{
        private var retrofit : Retrofit?= null
        private fun getInstance() : Retrofit {
            if(retrofit == null)
            {
                retrofit = Retrofit.Builder()
                    .baseUrl("https://nabilramadan.bsite.net")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!
        }
        fun getApi():UserServices{
            return getInstance().create(UserServices::class.java)
        }
    }
}