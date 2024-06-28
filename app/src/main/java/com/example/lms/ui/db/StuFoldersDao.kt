package com.example.lms.ui.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.lms.ui.api.api_student.material.CourseMaterialResponseItem

@Dao
interface StuFoldersDao {
    @Insert
    fun insertFolders(list: List<CourseMaterialResponseItem>)
    @Query("Select * From stuFolders")
    fun getFoldersFromLocal():List<CourseMaterialResponseItem>
    @Query("DELETE FROM stuFolders")
    fun deleteAllFolders()
}