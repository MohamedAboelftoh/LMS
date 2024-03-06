package com.example.lms.ui.api.assignments

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class UploadFileResponse(

	@field:SerializedName("traceId")
	val traceId: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("errors")
	val errors: Errors? = null,

	@field:SerializedName("status")
	val status: Int? = null
) : Parcelable