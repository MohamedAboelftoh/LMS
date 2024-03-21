package com.example.lms.ui.api.api_doctor.dr_courses.material

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class DrLecturesResponseItem(

	@field:SerializedName("LectureId")
	val lectureId: String? = null,

	@field:SerializedName("Type")
	val type: String? = null,

	@field:SerializedName("CreatedAt")
	val createdAt: String? = null,

	@field:SerializedName("LectureName")
	val lectureName: String? = null
) : Parcelable