package com.example.lms.ui.api.api_student.material

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class CourseMaterialResponse(

	@field:SerializedName("CourseMaterialResponse")
	val courseMaterialResponse: List<com.example.lms.ui.api.api_student.material.CourseMaterialResponseItem?>? = null
) : Parcelable