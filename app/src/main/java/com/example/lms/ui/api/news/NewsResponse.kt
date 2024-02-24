package com.example.lms.ui.api.news

import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Parcelize
data class NewsResponse(

	@field:SerializedName("newsData")
	val newsResponse: List<NewsResponseItem?>? = null
) : Parcelable