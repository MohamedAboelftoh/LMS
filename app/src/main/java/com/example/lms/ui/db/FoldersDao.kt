package com.example.lms.ui.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.lms.ui.api.api_doctor.dr_courses.material.DrLecturesResponseItem
@Dao
interface FoldersDao {
    @Insert
    fun insertFolders(list: List<DrLecturesResponseItem>)
    @Query("Select * From folders")
    fun getFoldersFromLocal():List<DrLecturesResponseItem>
    @Query("DELETE FROM folders")
    fun deleteAllFolders()
}