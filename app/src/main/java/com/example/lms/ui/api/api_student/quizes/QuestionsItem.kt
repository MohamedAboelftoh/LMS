package com.example.lms.ui.api.api_student.quizes

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class QuestionsItem(

    @field:SerializedName("createdAt")
	val createdAt: String? = null,

    @field:SerializedName("grade")
	val grade: Int? = null,

    @field:SerializedName("answers")
	val answers: ArrayList<com.example.lms.ui.api.api_student.quizes.AnswersItem?>? = null,

    @field:SerializedName("id")
	val id: String? = null,

    @field:SerializedName("text")
	val text: String? = null,

    @field:SerializedName("type")
	val type: String? = null,

    @field:SerializedName("questionNumber")
	val questionNumber: Int? = null
) : Parcelable