package com.example.lms.ui.api.module

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class LoginResponse(

	@field:SerializedName("displayName")
	val displayName: String? = null,

	@field:SerializedName("userRole")
	val userRole: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("token")
	val token: String? = null

) : Parcelable