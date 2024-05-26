package com.example.lms.ui.api.api_doctor.dr_courses.quizzes

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class DrQuestionsItem(

	@field:SerializedName("grade")
	var grade: Int? = null,

	@field:SerializedName("answers")
	var answers: List<DrAnswersItem?>? = null,

	@field:SerializedName("text")
	var text: String? = null,

	@field:SerializedName("type")
	var type: String? = null,

	@field:SerializedName("questionNumber")
	var questionNumber: Int? = null
) : Parcelable