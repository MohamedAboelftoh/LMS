package com.example.lms.ui.api.api_doctor.dr_courses.material

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
@Entity("doctorFiles")
@Parcelize
data class DrFilesResponseItem(
	@PrimaryKey(autoGenerate = true)
	val id : Int ?= null ,

	@field:SerializedName("lectureFileId")
	val lectureFileId: Int? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("fileName")
	val fileName: String? = null,

	@field:SerializedName("filePath")
	val filePath: String? = null
) : Parcelable