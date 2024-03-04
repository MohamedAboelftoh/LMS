package com.example.lms.ui.api.material.fiels

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class FielslResponseItem(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("fileName")
	val fileName: String? = null,

	@field:SerializedName("filePath")
	val filePath: String? = null
) : Parcelable