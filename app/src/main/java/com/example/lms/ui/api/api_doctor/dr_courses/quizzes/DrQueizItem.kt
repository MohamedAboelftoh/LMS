package com.example.lms.ui.api.api_doctor.dr_courses.quizzes

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class DrQueizItem(

    @field:SerializedName("notes")
	val notes: String? = null,

    @field:SerializedName("courseCycleId")
	val courseCycleId: String? = null,

    @field:SerializedName("endDate")
	val endDate: String? = null,

    @field:SerializedName("grade")
	val grade: Int? = null,

    @field:SerializedName("questions")
	val questions: List<DrQuestionsItem?>? = null,

    @field:SerializedName("title")
	val title: String? = null,

    @field:SerializedName("startDate")
	val startDate: String? = null
) : Parcelable