package com.example.lms.ui.api.api_student.quizes

data class Result(
    val questionId: String,
    val isCorrect: Boolean,
    val grade: Int
)
