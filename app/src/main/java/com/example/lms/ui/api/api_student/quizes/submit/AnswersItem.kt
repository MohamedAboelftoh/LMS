package com.example.lms.ui.api.api_student.quizes.submit

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class AnswersItem(

	@field:SerializedName("answerId")
	var answerId: String? = null,

	@field:SerializedName("questionId")
	val questionId: String? = null
) : Parcelable