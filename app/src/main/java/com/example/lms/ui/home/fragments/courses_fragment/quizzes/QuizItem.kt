package com.example.lms.ui.home.fragments.courses_fragment.quizzes

data class QuizItem(
    val startTime : String ?= null,
    val endTime : String ?= null,
    val courseName : String ?= null ,
    val questionNumber : Int ?= null

)
