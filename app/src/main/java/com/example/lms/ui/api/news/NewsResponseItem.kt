package com.example.lms.ui.api.news
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import kotlinx.parcelize.RawValue
import com.google.gson.annotations.SerializedName

@Parcelize
data class NewsResponseItem(
	@field:SerializedName("facultyId")
	val facultyId: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("newsId")
	val newsId: String? = null,

	@field:SerializedName("filePath")
	val filePath: String? = null,

	// Use @RawValue for the facultyName property to allow any type
	@field:SerializedName("facultyName")
	val facultyName:String? = null,

	@field:SerializedName("userName")
	val userName: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("content")
	val content: String? = null,

	@field:SerializedName("userImage")
val userImage: String? = null
) : Parcelable
