package com.example.lms.ui.api.api_student.all_grades

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class AllGradesResponse(

	@field:SerializedName("AllGradesResponse")
	val allGradesResponse: List<AllGradesResponseItem?>? = null
) : Parcelable