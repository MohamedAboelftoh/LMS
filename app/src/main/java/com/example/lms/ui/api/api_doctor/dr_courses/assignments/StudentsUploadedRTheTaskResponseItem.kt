package com.example.lms.ui.api.api_doctor.dr_courses.assignments

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class StudentsUploadedTheTaskResponseItem(

	@field:SerializedName("studentId")
	val studentId: String? = null,

	@field:SerializedName("studentName")
	val studentName: String? = null,

	@field:SerializedName("filePath")
	val filePath: String? = null,

	@field:SerializedName("timeUploaded")
	val timeUploaded: String? = null
) : Parcelable