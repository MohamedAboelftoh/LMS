package com.example.lms.ui.api.api_student.assignments

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Parcelize
@Entity(tableName = "studentAssignments")
data class AssignmentResponseItem(
	@PrimaryKey(autoGenerate = true)
	val id : Int ?= null ,

	@field:SerializedName("endDate")
	val endDate: String? = null,

	@field:SerializedName("taskName")
	val taskName: String? = null,

	@field:SerializedName("taskId")
	val taskId: String? = null,

	@field:SerializedName("startDate")
	val startDate: String? = null,

) : Parcelable