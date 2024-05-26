package com.example.lms.ui.api.api_doctor.dr_courses.assignments

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class DrAllAssignmentsResponseItem(

	@field:SerializedName("endDate")
	val endDate: String? = null,

	@field:SerializedName("filePath")
	val filePath: String? = null,

	@field:SerializedName("taskName")
	val taskName: String? = null,

	@field:SerializedName("taskId")
	val taskId: String? = null,

	@field:SerializedName("startDate")
	val startDate: String? = null
) : Parcelable