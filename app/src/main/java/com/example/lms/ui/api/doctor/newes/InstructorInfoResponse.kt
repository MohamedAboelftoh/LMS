package com.example.lms.ui.api.doctor.newes

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class InstructorInfoResponse(

	@field:SerializedName("universityName")
	val universityName: String? = null,

	@field:SerializedName("phone")
	val phone: String? = null,

	@field:SerializedName("imagePath")
	val imagePath: String? = null,

	@field:SerializedName("fullName")
	val fullName: String? = null,

	@field:SerializedName("facultyName")
	val facultyName: String? = null,

	@field:SerializedName("email")
	val email: String? = null
) : Parcelable