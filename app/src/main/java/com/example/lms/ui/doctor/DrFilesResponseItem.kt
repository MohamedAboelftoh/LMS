package com.example.lms.ui.doctor

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class DrFilesResponseItem(

	@field:SerializedName("lectureFileId")
	val lectureFileId: Int? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("fileName")
	val fileName: String? = null,

	@field:SerializedName("filePath")
	val filePath: String? = null
) : Parcelable