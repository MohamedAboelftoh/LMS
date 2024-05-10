package com.example.lms.ui.api.api_doctor.dr_courses.quizzes

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class DrQuestionsItem(

	@field:SerializedName("grade")
	val grade: Int? = null,

	@field:SerializedName("answers")
	val answers: List<DrAnswersItem?>? = null,

	@field:SerializedName("text")
	val text: String? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("questionNumber")
	val questionNumber: Int? = null
) : Parcelable