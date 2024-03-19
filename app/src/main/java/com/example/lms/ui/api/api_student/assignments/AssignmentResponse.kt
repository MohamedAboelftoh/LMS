package com.example.lms.ui.api.api_student.assignments

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class AssignmentResponse(

	//@field:SerializedName("AssignmentResponse")
	val assignmentResponse: List<com.example.lms.ui.api.api_student.assignments.AssignmentResponseItem?>? = null
) : Parcelable