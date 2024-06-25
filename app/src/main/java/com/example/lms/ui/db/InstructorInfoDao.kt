package com.example.lms.ui.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.lms.ui.api.api_doctor.InstructorInfoResponse
import com.example.lms.ui.api.api_student.news.NewsResponseItem
@Dao
interface InstructorInfoDao {
    @Insert
    fun insertInstructor(list: InstructorInfoResponse)
    @Query("Select * From instructors")
    fun getInstructorFromLocal():InstructorInfoResponse
    @Query("DELETE FROM instructors")
    fun deleteAllInstructors()
}