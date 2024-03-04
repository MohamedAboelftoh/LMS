package com.example.lms.ui.api.module

import android.content.Context
import android.content.SharedPreferences

class MyPreferencesToken(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)

    fun saveData(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply() // or use commit() if you need an immediate return value
    }

    fun loadData(key: String): String? {
        return sharedPreferences.getString(key, null)
    }
}