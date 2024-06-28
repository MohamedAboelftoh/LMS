package com.example.lms.ui.api.api_student.course_tasks__grades

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Parcelize
@Entity(tableName = "stuCourseGrades")
data class CourseTasksGradesResponseItem(
	@PrimaryKey(autoGenerate = true)
	val id : Int ?= null ,
	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("studentGrade")
	val studentGrade: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("fullGrade")
	val fullGrade: Int? = null
) : Parcelable