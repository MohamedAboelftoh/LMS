package com.example.lms.ui.api.material.fiels

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class FielslResponse(

	@field:SerializedName("FielslResponse")
	val fielslResponse: List<FielslResponseItem?>? = null
) : Parcelable