package com.example.lms.ui.api.quizes.submit

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class SubmitQuizRequest(

	@field:SerializedName("quizId")
	val quizId: String? = null,

	@field:SerializedName("answers")
	val answers: List<AnswersItem?>? = null
) : Parcelable