package com.example.lms.ui.api.password

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class ChangePasswordResponse(

	@field:SerializedName("newPassword")
	val newPassword: String,

	@field:SerializedName("currentPassword")
	val currentPassword: String
) : Parcelable
