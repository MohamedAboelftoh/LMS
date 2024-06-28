package com.example.lms.ui.api.api_student.account

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Parcelize
@Entity(tableName = "students")
data class AccountInfoResponse(
	@PrimaryKey(autoGenerate = true)
	val id : Int ?= null ,

	@field:SerializedName("departmentName")
	val departmentName: String? = null,

	@field:SerializedName("universityName")
	val universityName: String? = null,

	@field:SerializedName("academicId")
	val academicId: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("level")
	val level: Int? = null,

	@field:SerializedName("imagePath")
	val imagePath: String? = null,

	@field:SerializedName("fullName")
	val fullName: String? = null,

	@field:SerializedName("facultyName")
	val facultyName: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("email")
	val email: String? = null
) : Parcelable