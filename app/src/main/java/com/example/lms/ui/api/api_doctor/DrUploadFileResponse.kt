package com.example.lms.ui.api.api_doctor

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class DrUploadFileResponse(

	@field:SerializedName("lectureFileId")
	val lectureFileId: Int? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("filePath")
	val filePath: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("lecture")
	val lecture: String? = null,

	@field:SerializedName("lectureId")
	val lectureId: String? = null
) : Parcelable