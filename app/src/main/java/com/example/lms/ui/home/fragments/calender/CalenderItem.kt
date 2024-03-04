package com.example.lms.ui.home.fragments.calender

data class CalenderItem(
    val courseName : String ?= null ,
    val quiz : String ?= null ,
    val time : String ?= null,
    val color : Int ?= null,
    val colorView : Int ?= null
)
