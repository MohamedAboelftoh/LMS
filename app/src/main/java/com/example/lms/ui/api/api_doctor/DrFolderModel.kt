package com.example.lms.ui.api.api_doctor

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class DrFolderModel(

	@field:SerializedName("cycleId")
	val cycleId: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("type")
	val type: String
) : Parcelable
