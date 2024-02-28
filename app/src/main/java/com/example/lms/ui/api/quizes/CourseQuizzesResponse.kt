package com.example.lms.ui.api.quizes

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class CourseQuizzesResponse(

	@field:SerializedName("CourseQuizzesResponse")
	val courseQuizzesResponse: List<CourseQuizzesResponseItem?>? = null
) : Parcelable