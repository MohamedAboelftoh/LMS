package com.example.lms.ui.api.api_student.courses

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class CoursesResponseItem(

	@field:SerializedName("hours")
	val hours: Int? = null,

	@field:SerializedName("imagePath")
	val imagePath: String? = null,

	@field:SerializedName("cycleId")
	val cycleId: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("instructorFullName")
	val instructorFullName: String? = null
) : Parcelable