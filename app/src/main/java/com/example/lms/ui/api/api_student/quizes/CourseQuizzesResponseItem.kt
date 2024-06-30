package com.example.lms.ui.api.api_student.quizes

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Parcelize
@Entity(tableName = "stuCourseQuizzes")
data class CourseQuizzesResponseItem(
	@PrimaryKey(autoGenerate = true)
	val courseLocalId : Int ?= null ,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("notes")
	val notes: String? = null,

	@field:SerializedName("endDate")
	val endDate: String? = null,

	@field:SerializedName("grade")
	val grade: String? = null,

	@field:SerializedName("questions")
	val questions: String? = null,

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
	val status: String? = null,

	@field:SerializedName("numberOfQuestion")
	val numberOfQuestion: Int? = null

) : Parcelable