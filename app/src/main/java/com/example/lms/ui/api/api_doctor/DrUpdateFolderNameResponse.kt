package com.example.lms.ui.api.api_doctor

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class DrUpdateFolderNameResponse(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("lectureFiles")
	val lectureFiles: List<String?>? = null,

	@field:SerializedName("courseCycleId")
	val courseCycleId: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("lectureId")
	val lectureId: String? = null,

	@field:SerializedName("courseCycle")
	val courseCycle: String? = null
) : Parcelable