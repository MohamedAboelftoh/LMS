package com.example.lms.ui.api.api_doctor.dr_courses.quizzes

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class DrQuizzesResponseItem(

	@field:SerializedName("studentQuizGrades")
	val studentQuizGrades: List<String?>? = null,

	@field:SerializedName("notes")
	val notes: String? = null,

	@field:SerializedName("courseCycleId")
	val courseCycleId: String? = null,

	@field:SerializedName("quizId")
	val quizId: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("endDate")
	val endDate: String? = null,

	@field:SerializedName("questions")
	val questions: List<String?>? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("courseCycle")
	val courseCycle: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("instructor")
	val instructor: String? = null,

	@field:SerializedName("grade")
	val grade: Int? = null,

	@field:SerializedName("startDate")
	val startDate: String? = null,

	@field:SerializedName("instructorId")
	val instructorId: String? = null
) : Parcelable