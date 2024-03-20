package com.example.lms.ui.api.api_doctor.dr_courses

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class DrCoursesResponseItem(

	@field:SerializedName("hours")
	val hours: Int? = null,

	@field:SerializedName("imagePath")
	val imagePath: String? = null,

	@field:SerializedName("cycleId")
	val cycleId: String? = null,

	@field:SerializedName("name")
	val name: String? = null
) : Parcelable