package com.example.lms.ui.api.api_student.news

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class NewsResponse(

	//@field:SerializedName("newsData")
	val newsResponse: List<com.example.lms.ui.api.api_student.news.NewsResponseItem?>? = null
) : Parcelable