package com.example.lms.ui.api.api_student.quizes

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.RawValue
import kotlin.time.Duration

@Parcelize
data class QuizQuestionsResponse(
    @field:SerializedName("createdAt")
	val createdAt: String? = null,

    @field:SerializedName("notes")
	val notes: String? = null,

    @field:SerializedName("endDate")
	val endDate: String? = null,

    @field:SerializedName("grade")
	val grade: Int? = null,

    @field:SerializedName("duration")
	val duration: String? = null,

    @field:SerializedName("questions")
	val questions: ArrayList<com.example.lms.ui.api.api_student.quizes.QuestionsItem?>? = null,

    @field:SerializedName("id")
	val id: String? = null,

    @field:SerializedName("title")
	val title: String? = null,

    @field:SerializedName("courseId")
	val courseId: String? = null,

    @field:SerializedName("startDate")
	val startDate: String? = null,

    @field:SerializedName("instructorId")
	val instructorId: String? = null,

    @field:SerializedName("status")
	val status: String? = null
) : Parcelable