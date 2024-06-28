package com.example.lms.ui.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lms.ui.api.api_doctor.InstructorInfoResponse
import com.example.lms.ui.api.api_doctor.dr_courses.DrCoursesResponseItem
import com.example.lms.ui.api.api_doctor.dr_courses.material.DrLecturesResponseItem
import com.example.lms.ui.api.api_student.account.AccountInfoResponse
import com.example.lms.ui.api.api_student.assignments.AssignmentResponseItem
import com.example.lms.ui.api.api_student.course_tasks__grades.CourseTasksGradesResponseItem
import com.example.lms.ui.api.api_student.courses.CoursesResponseItem
import com.example.lms.ui.api.api_student.material.CourseMaterialResponseItem
import com.example.lms.ui.api.api_student.news.NewsResponseItem
import com.example.lms.ui.api.api_student.quizes.CourseQuizzesResponseItem

@Database(entities = [NewsResponseItem::class , DrCoursesResponseItem::class , InstructorInfoResponse::class
    , DrLecturesResponseItem::class,AccountInfoResponse::class, CoursesResponseItem::class
    ,CourseMaterialResponseItem::class, CourseQuizzesResponseItem::class, AssignmentResponseItem::class,CourseTasksGradesResponseItem::class] , version = 1, exportSchema = true)
abstract class DataBase : RoomDatabase() {
    abstract fun newsDao():Dao
    abstract fun coursesDao():CoursesDao
    abstract fun instructorDao():InstructorInfoDao
    abstract fun foldersDao():FoldersDao
    abstract fun studentInfoDao():StudentInfoDao
    abstract fun studentCourses():StuCoursesDao
    abstract fun stuAssignmentsDao():StuAssignmentsDao

    abstract fun stuFoldersDao():StuFoldersDao
    abstract fun stuQuizzesDao():StuQuizzesDao
    abstract fun stuCourseGradesDao():StuCourseGradesDao

    companion object{
        private var instance : DataBase ?= null
        fun getInstance(context: Context):DataBase{
            if(instance == null){
                instance = Room.databaseBuilder(context,DataBase::class.java,"Local_db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!
        }

    }
}