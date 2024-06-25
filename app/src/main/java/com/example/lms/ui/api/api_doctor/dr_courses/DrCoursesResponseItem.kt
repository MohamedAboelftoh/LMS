package com.example.lms.ui.api.api_doctor.dr_courses

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Parcelize
@Entity("courses")
data class DrCoursesResponseItem(
	@PrimaryKey(autoGenerate = true)
	val id : Int ?= null ,
	@field:SerializedName("hours")
	val hours: Int? = null,

	@field:SerializedName("imagePath")
	val imagePath: String? = null,

	@field:SerializedName("cycleId")
	val cycleId: String? = null,

	@field:SerializedName("name")
	val name: String? = null
) : Parcelable