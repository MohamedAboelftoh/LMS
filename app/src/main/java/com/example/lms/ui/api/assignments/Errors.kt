package com.example.lms.ui.api.assignments

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class Errors(

	@field:SerializedName("file")
	val file: List<String?>? = null
) : Parcelable