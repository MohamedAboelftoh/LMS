package com.example.lms.ui.api.api_student.calender

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class CalenderRequest(

	@field:SerializedName("endDate")
	val endDate: String? = null,

	@field:SerializedName("body")
	val body: String? = null,

	@field:SerializedName("startDate")
	val startDate: String? = null
) : Parcelable