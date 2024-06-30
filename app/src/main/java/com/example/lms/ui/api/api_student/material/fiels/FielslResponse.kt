package com.example.lms.ui.api.api_student.material.fiels

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class FielslResponse(

	@field:SerializedName("FielslResponse")
	val fielslResponse: List<com.example.lms.ui.api.api_student.material.fiels.FilesResponseItem?>? = null
) : Parcelable