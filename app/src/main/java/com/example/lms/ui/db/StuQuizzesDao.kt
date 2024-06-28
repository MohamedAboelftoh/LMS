package com.example.lms.ui.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.lms.ui.api.api_student.quizes.CourseQuizzesResponseItem

@Dao
interface StuQuizzesDao {
    @Insert
    fun insertQuizzes(list: List<CourseQuizzesResponseItem>)
    @Query("Select * From stuCourseQuizzes")
    fun getQuizzesFromLocal():List<CourseQuizzesResponseItem>
    @Query("DELETE FROM stuCourseQuizzes")
    fun deleteAllQuizzes()
}