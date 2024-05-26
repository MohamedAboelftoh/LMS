package com.example.lms.ui.api.api_doctor.dr_courses.material

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class DrLecturesResponse(

	@field:SerializedName("DrLecturesResponse")
	val drLecturesResponse: List<DrLecturesResponseItem?>? = null
) : Parcelable