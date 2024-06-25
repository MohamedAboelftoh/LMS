package com.example.lms.ui.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lms.ui.api.api_student.news.NewsResponseItem

@Dao
interface Dao {
    @Insert
    fun insertNews(list: List<NewsResponseItem>)
    @Query("Select * From news")
    fun getNewsFromLocal():List<NewsResponseItem>
    @Query("DELETE FROM news")
    fun deleteAllNews()
}