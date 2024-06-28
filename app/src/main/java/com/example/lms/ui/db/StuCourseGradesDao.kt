package com.example.lms.ui.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.lms.ui.api.api_student.course_tasks__grades.CourseTasksGradesResponseItem

@Dao
interface StuCourseGradesDao {
    @Query("SELECT * FROM stuCourseGrades")
    fun getCourseGradesFromLocal(): List<CourseTasksGradesResponseItem>

    @Insert
    fun insertCourseGrades(grades: List<CourseTasksGradesResponseItem>)

    @Query("DELETE FROM stuCourseGrades")
    fun deleteCourseGradesFromLocal()
}
