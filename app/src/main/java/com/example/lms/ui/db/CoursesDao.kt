package com.example.lms.ui.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.lms.ui.api.api_doctor.dr_courses.DrCoursesResponseItem
import com.example.lms.ui.api.api_student.news.NewsResponseItem

@Dao
interface CoursesDao {
    @Insert
    fun insertCourses(list: List<DrCoursesResponseItem>)
    @Query("Select * From courses")
    fun getCoursesFromLocal():List<DrCoursesResponseItem>
    @Query("DELETE FROM courses")
    fun deleteAllCourses()
}