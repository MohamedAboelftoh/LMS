package com.example.lms.ui.api.api_student.news
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Parcelize
@Entity(tableName = "news")
data class NewsResponseItem(
	@PrimaryKey(autoGenerate = true)
	val id : Int ?= null ,
	@field:SerializedName("facultyId")
	val facultyId: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("newsId")
	val newsId: String? = null,

	@field:SerializedName("filePath")
	val filePath: String? = null,

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
