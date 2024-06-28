package com.example.lms.ui.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.lms.ui.api.api_student.courses.CoursesResponseItem

@Dao
interface StuCoursesDao {
    @Insert
    fun insertCourses(list: List<CoursesResponseItem>)
    @Query("Select * From studentCourses")
    fun getCoursesFromLocal():List<CoursesResponseItem>
    @Query("DELETE FROM studentCourses")
    fun deleteAllCourses()
}