package com.example.lms.ui.api.api_doctor.dr_courses.quizzes

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class DrAnswersItem(

	@field:SerializedName("text")
	val text: String? = null,

	@field:SerializedName("answerNumber")
	val answerNumber: Int? = null,

	@field:SerializedName("isCorrect")
	val isCorrect: Boolean? = null
) : Parcelable