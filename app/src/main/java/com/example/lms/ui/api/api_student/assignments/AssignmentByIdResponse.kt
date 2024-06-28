package com.example.lms.ui.api.api_student.assignments

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class AssignmentByIdResponse(

	@field:SerializedName("startDate")
	val startDate: String? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("filePath")
	val filePath: String? = null,

	@field:SerializedName("taskName")
	val taskName: String? = null,

	@field:SerializedName("taskGrade")
	val taskGrade: Int? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("endDate")
	val endDate: String? = null
) : Parcelable