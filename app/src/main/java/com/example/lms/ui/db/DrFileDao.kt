package com.example.lms.ui.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.lms.ui.api.api_doctor.dr_courses.material.DrFilesResponseItem

@Dao
interface DrFileDao {
    @Insert
    fun insertFiles(list: List<DrFilesResponseItem>)
    @Query("Select * From doctorFiles")
    fun getFilesFromLocal():List<DrFilesResponseItem>
    @Query("DELETE FROM doctorFiles")
    fun deleteAllFiles()
}