package com.example.lms.ui.api.material

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class CourseMaterialResponseItem(

	@field:SerializedName("fileName")
	val fileName: String? = null,

	@field:SerializedName("lectureName")
	val lectureName: String? = null,

	@field:SerializedName("filePath")
	val filePath: String? = null,

	@field:SerializedName("semesterName")
	val semesterName: String? = null,

	@field:SerializedName("type")
	val type: String? = null
) : Parcelable