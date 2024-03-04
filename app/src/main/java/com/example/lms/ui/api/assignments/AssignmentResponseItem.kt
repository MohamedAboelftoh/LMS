package com.example.lms.ui.api.assignments

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class AssignmentResponseItem(

//	@field:SerializedName("createdAt")
//	val createdAt: String? = null,

//	@field:SerializedName("courseName")
//	val courseName: String? = null,

//	@field:SerializedName("instructorName")
//	val instructorName: String? = null,

	@field:SerializedName("endDate")
	val endDate: String? = null,
//
//	@field:SerializedName("taskGrade")
//	val taskGrade: Int? = null,

//	@field:SerializedName("filePath")
//	val filePath: String? = null,

	@field:SerializedName("taskName")
	val taskName: String? = null,

	@field:SerializedName("taskId")
	val taskId: String? = null,

	@field:SerializedName("startDate")
	val startDate: String? = null,

	@field:SerializedName("status")
	val status: String? = null
) : Parcelable