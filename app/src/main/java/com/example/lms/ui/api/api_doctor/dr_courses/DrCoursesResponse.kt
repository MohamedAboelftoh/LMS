package com.example.lms.ui.api.api_doctor.dr_courses

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class DrCoursesResponse(

	//@field:SerializedName("DrCoursesResponse")
	val drCoursesResponse: List<DrCoursesResponseItem?>? = null
) : Parcelable