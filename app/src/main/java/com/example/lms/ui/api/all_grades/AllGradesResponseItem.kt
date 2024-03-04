package com.example.lms.ui.api.all_grades

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class AllGradesResponseItem(

	@field:SerializedName("courseName")
	val courseName: String? = null,

	@field:SerializedName("taskGrade")
	val taskGrade: Int? = null,

	@field:SerializedName("taskTitle")
	val taskTitle: String? = null
) : Parcelable