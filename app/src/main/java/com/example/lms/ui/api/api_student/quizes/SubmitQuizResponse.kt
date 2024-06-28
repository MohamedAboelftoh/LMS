package com.example.lms.ui.api.api_student.quizes

data class SubmitQuizResponse(
    val results: List<Result>,
    val totalGrade: Int,
    val totalStudentGrade: Int
)
