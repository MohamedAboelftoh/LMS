package com.example.lms.ui.api.calender

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class CalenderResponseItem(

//	@field:SerializedName("calendarId")
//	val calendarId: String? = null,

	@field:SerializedName("endDate")
	val endDate: String? = null,

	@field:SerializedName("body")
	val body: String? = null,

//	@field:SerializedName("userId")
//	val userId: String? = null,

	@field:SerializedName("startDate")
	val startDate: String? = null
) : Parcelable