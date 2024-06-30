package com.example.lms.ui.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.lms.ui.api.api_student.material.fiels.FilesResponseItem

@Dao
interface FilesDao {
    @Insert
    fun insertFiles(list: List<FilesResponseItem>)
    @Query("Select * From files")
    fun getFilesFromLocal():List<FilesResponseItem>
    @Query("DELETE FROM files")
    fun deleteAllFiles()
}