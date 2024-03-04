package com.example.lms.ui.api.assignments

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class AssignmentResponse(

	//@field:SerializedName("AssignmentResponse")
	val assignmentResponse: List<AssignmentResponseItem?>? = null
) : Parcelable