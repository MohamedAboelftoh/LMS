package com.example.lms.ui.api.api_student.quizes

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class CourseQuizzesResponse(

	@field:SerializedName("CourseQuizzesResponse")
	val courseQuizzesResponse: List<com.example.lms.ui.api.api_student.quizes.CourseQuizzesResponseItem?>? = null
) : Parcelable