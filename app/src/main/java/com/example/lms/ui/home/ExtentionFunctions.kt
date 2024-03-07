package com.example.lms.ui.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment

fun Activity.navigateFromActivity(context: Context,activity:Activity){
    val intent=Intent(context,activity::class.java)
    startActivity(intent)
}


fun Fragment.navigateFromFragment(context: Context,activity:Activity){
    val intent=Intent(context,activity::class.java)
    startActivity(intent)
}