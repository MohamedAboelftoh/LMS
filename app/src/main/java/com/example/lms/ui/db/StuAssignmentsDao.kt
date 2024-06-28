package com.example.lms.ui.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.lms.ui.api.api_student.assignments.AssignmentResponseItem

@Dao
interface StuAssignmentsDao {
    @Insert
    fun insertAssignments(list: List<AssignmentResponseItem>)
    @Query("Select * From studentAssignments")
    fun getAssignmentsFromLocal():List<AssignmentResponseItem>
    @Query("DELETE FROM studentAssignments")
    fun deleteAllAssignments()

}