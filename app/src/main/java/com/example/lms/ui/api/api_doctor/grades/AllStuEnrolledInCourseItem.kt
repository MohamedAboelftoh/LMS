package com.example.lms.ui.api.api_doctor.grades

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class AllStuEnrolledInCourseItem(

	@field:SerializedName("studentId")
	val studentId: String? = null,

	@field:SerializedName("studentName")
	val studentName: String? = null
) : Parcelable