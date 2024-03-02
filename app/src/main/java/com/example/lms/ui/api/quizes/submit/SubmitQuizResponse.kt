package com.example.lms.ui.api.quizes.submit

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class SubmitQuizResponse(

	@field:SerializedName("Q002_1")
	val q0021: Boolean? = null,

	@field:SerializedName("Q001_1")
	val q0011: Boolean? = null,

	@field:SerializedName("Q004_1")
	val q0041: Boolean? = null,

	@field:SerializedName("Q003_1")
	val q0031: Boolean? = null
) : Parcelable