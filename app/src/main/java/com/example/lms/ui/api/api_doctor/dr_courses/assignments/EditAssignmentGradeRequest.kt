package com.example.lms.ui.api.api_doctor.dr_courses.assignments

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class EditAssignmentGradeRequest(

	@field:SerializedName("studentId")
	val studentId: String? = null,

	@field:SerializedName("grade")
	val grade: Int? = null,

	@field:SerializedName("taskId")
	val taskId: String? = null
) : Parcelable