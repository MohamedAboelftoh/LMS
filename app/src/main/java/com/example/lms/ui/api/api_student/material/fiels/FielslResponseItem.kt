package com.example.lms.ui.api.api_student.material.fiels

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class FielslResponseItem(

	@field:SerializedName("CreatedAt")
	val createdAt: String? = null,

	@field:SerializedName("FileName")
	val fileName: String? = null,

	@field:SerializedName("FilePath")
	val filePath: String? = null
) : Parcelable