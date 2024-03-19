package com.example.lms.ui.api.api_student.material

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class CourseMaterialResponseItem(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("lectureName")
	val lectureName: String? = null,

	@field:SerializedName("semesterName")
	val semesterName: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("lectureId")
	val lectureId: String? = null
) : Parcelable