package com.example.lms.ui.api.quizes.submit

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class AnswersItem(

	@field:SerializedName("answerId")
	val answerId: String? = null,

	@field:SerializedName("questionId")
	val questionId: String? = null
) : Parcelable