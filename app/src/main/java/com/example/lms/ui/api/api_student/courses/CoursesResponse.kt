package com.example.lms.ui.api.api_student.courses

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class CoursesResponse(

	//@field:SerializedName("coursesData")
	val coursesResponse: List<CoursesResponseItem?>? = null
) : Parcelable