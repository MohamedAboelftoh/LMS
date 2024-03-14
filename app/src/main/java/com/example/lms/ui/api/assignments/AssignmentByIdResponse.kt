package com.example.lms.ui.api.assignments

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class AssignmentByIdResponse(

	@field:SerializedName("StartDate")
	val startDate: String? = null,

	@field:SerializedName("Status")
	val status: String? = null,

	@field:SerializedName("FilePath")
	val filePath: String? = null,

	@field:SerializedName("TaskName")
	val taskName: String? = null,

	@field:SerializedName("TaskGrade")
	val taskGrade: Int? = null,

	@field:SerializedName("CreatedAt")
	val createdAt: String? = null,

	@field:SerializedName("EndDate")
	val endDate: String? = null
) : Parcelable