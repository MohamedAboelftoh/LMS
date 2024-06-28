package com.example.lms.ui.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.lms.ui.api.api_student.account.AccountInfoResponse

@Dao
interface StudentInfoDao {
    @Insert
    fun insertStudentInfo(list: AccountInfoResponse)
    @Query("Select * From students")
    fun getStuInfoFromLocal():AccountInfoResponse

    @Query("DELETE FROM students")
    fun deleteStuFromLocal()


}