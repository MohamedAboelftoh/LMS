package com.example.lms.ui.api.api_doctor.grades

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class GradesSelectedStuResponseItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("grade")
	val grade: Float? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("title")
	val title: String? = null
) : Parcelable