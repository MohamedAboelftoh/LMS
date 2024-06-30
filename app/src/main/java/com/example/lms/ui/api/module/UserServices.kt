package com.example.lms.ui.api.module

import com.example.lms.ui.api.api_doctor.DrFolderModel
import com.example.lms.ui.api.api_doctor.DrUpdateFolderNameResponse
import com.example.lms.ui.api.api_doctor.DrUploadFileResponse
import com.example.lms.ui.api.api_doctor.DrUploadLectureResponse
import com.example.lms.ui.api.api_doctor.InstructorInfoResponse
import com.example.lms.ui.api.api_doctor.dr_courses.DrCoursesResponseItem
import com.example.lms.ui.api.api_doctor.dr_courses.assignments.DrAllAssignmentsResponseItem
import com.example.lms.ui.api.api_doctor.dr_courses.assignments.EditAssignmentGradeRequest
import com.example.lms.ui.api.api_doctor.dr_courses.assignments.StudentsUploadedTheTaskResponseItem
import com.example.lms.ui.api.api_doctor.dr_courses.assignments.UpdateAssignmentModel
import com.example.lms.ui.api.api_doctor.dr_courses.material.DrLecturesResponseItem
import com.example.lms.ui.api.api_student.account.AccountInfoResponse
import com.example.lms.ui.api.api_student.assignments.AssignmentByIdResponse
import com.example.lms.ui.api.api_student.assignments.AssignmentResponseItem
import com.example.lms.ui.api.api_student.calender.CalenderRequest
import com.example.lms.ui.api.api_student.calender.CalenderResponseItem
import com.example.lms.ui.api.api_student.courses.CoursesResponseItem
import com.example.lms.ui.api.login.LoginRequest
import com.example.lms.ui.api.login.LoginResponse
import com.example.lms.ui.api.api_student.material.CourseMaterialResponseItem
import com.example.lms.ui.api.api_student.material.fiels.FilesResponseItem
import com.example.lms.ui.api.api_student.news.NewsResponseItem
import com.example.lms.ui.api.api_student.quizes.CourseQuizzesResponseItem
import com.example.lms.ui.api.api_student.quizes.QuizQuestionsResponse
import com.example.lms.ui.api.api_student.quizes.submit.SubmitQuizRequest
import com.example.lms.ui.api.api_doctor.dr_courses.material.DrFilesResponseItem
import com.example.lms.ui.api.api_doctor.dr_courses.quizzes.DrQuizItem
import com.example.lms.ui.api.api_doctor.dr_courses.quizzes.DrQuizzesResponseItem
import com.example.lms.ui.api.api_doctor.grades.AllStuEnrolledInCourseItem
import com.example.lms.ui.api.api_doctor.grades.GradesSelectedStuResponseItem
import com.example.lms.ui.api.api_student.course_tasks__grades.CourseTasksGradesResponseItem
import com.example.lms.ui.api.api_student.quizes.SubmitQuizResponse
import com.example.lms.ui.api.password.ChangePasswordResponse
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query

interface UserServices {
    @POST("api/Account/login")
    fun userLogin(@Body loginRequest: LoginRequest) : Call<LoginResponse>
    @GET("api/News")
    fun getNews(): Call<ArrayList<NewsResponseItem>>
    @GET("api/Account/GetCurrentUser")
    fun getCurrentUser(@Header("Authorization") token : String): Call<LoginResponse>

    @GET("api/Students/CurrentCourcesInfo")
    fun getAllCourses(@Header("Authorization")token:String): Call<ArrayList<CoursesResponseItem>>
    @GET("api/Instructor/CurrentCoursesInfo")
    fun getAllDrCourses(@Header("Authorization")token:String): Call<ArrayList<DrCoursesResponseItem>>

    @GET("api/Students/CurrentCourseMaterial")
    fun getCourseMaterial(@Header("Authorization")token:String
                          ,@Query("cycleId")cycleId:String
    ):Call<ArrayList<CourseMaterialResponseItem>>

    @GET("api/Students/CurrentCourseTasks")
    fun getAllAssignmentOfCourse(@Header("Authorization")token:String
                                 , @Query("cycleId")cycleId:String) : Call<ArrayList<AssignmentResponseItem>>
    @GET("api/Students/CurrentCourseQuizzes")
    fun getCourseQuizzes(@Header("Authorization")token:String
                         , @Query("cycleId")cycleId:String
    ):Call<ArrayList<CourseQuizzesResponseItem>>

    @GET("api/Students/Quiz")
    fun getQuizQuestions(@Header("Authorization")token:String
                         ,@Query("quizId")quizId: String
    ):Call<QuizQuestionsResponse>

//    @POST("api/Students/quiz/submit")
//    fun submitQuiz(@Body submitQuizRequest: SubmitQuizRequest, @Query("quizId")quizId: String,
//                   @Header("Authorization")token:String) : Call< List<Map<String?,Boolean?>>>
//SubmitQuizResponse
@POST("api/Students/quiz/submit")
fun submitQuiz(@Body submitQuizRequest: SubmitQuizRequest, @Query("quizId")quizId: String,
               @Header("Authorization")token:String) : Call<SubmitQuizResponse>
    @GET("api/Students/GetStudentInfo")
    fun getAccountInfo(@Header("Authorization")token:String):Call<AccountInfoResponse>
    @GET("api/Students/Getfilesoflecture")
    fun getFiles(@Header("Authorization")token:String?,
                 @Query("lectureId")lectureId:String?):Call<MutableList<FilesResponseItem>>
    @GET("api/Students/GetAllGradesForCurrentCourse")
    fun getAllGradesForCurrentCourse(@Header("Authorization")token:String?,
                 @Query("courseId")courseId:String?):Call<ArrayList<CourseTasksGradesResponseItem>>
    @GET("api/Instructor/Getfilesoflecture")
    fun getDrFiles(@Header("Authorization")token:String?,
                 @Query("lectureId")lectureId:String?):Call<MutableList<DrFilesResponseItem>>

    @Multipart
    @POST("api/Students/File/Upload")
    fun uploadFile(
        @Query("taskid") taskId: String,

        @Part file: MultipartBody.Part,

        @Header("Authorization") token: String
    ): Call<ResponseBody>
    @Multipart
    @POST("api/Instructor/UploadLectureFile")
    fun drUploadFile(
        @Query("lectureId") lectureId: String,
        @Query("file_Name") fileName: String,
        @Part file: MultipartBody.Part,
        @Header("Authorization") token: String
    ): Call<DrUploadFileResponse>
    @POST("api/Instructor/UploadLectureFolder")
    fun drUploadLecture(
        @Body drFolderModel: DrFolderModel,
        @Header("Authorization") token: String
    ): Call<DrUploadLectureResponse>

    @POST("api/Calendar")
    fun addNewEvent(@Header("Authorization")token:String
                    , @Body calenderRequest: CalenderRequest
    ) : Call<ResponseBody>

    @GET("api/Calendar/GetByStartAndEnd")
    fun getCalenderEvents(@Header("Authorization") token:String,
                          @Query("start") startDate:String,
                          @Query("end") endDate:String ):Call<ArrayList<CalenderResponseItem>>
    @GET("api/Students/GetAssignment")
    fun getAssignment(@Header("Authorization") token : String , @Query("taskId") taskId : String) : Call<AssignmentByIdResponse>

    @GET("api/Instructor/GetInstructorInfo")
    fun getInstructorInfo(@Header("Authorization") token : String) : Call<InstructorInfoResponse>

    @GET("api/Instructor/CurrentCourseMaterial")
    fun getDrCourseMaterial(@Header("Authorization")token:String
                          ,@Query("CycleId")cycleId:String
    ):Call<ArrayList<DrLecturesResponseItem>>

    @DELETE("api/Instructor/DeleteLectureFile")
    fun drDeleteFile(@Header("Authorization")token:String
                     ,@Query("FileId") fileId: Int
    ):Call<Void>
    @DELETE("api/Instructor/DeleteLectureFolder")
    fun drDeleteFolder(@Header("Authorization")token:String
                     ,@Query("lectureId") lectureId: String
    ):Call<Void>
    @PUT("api/Instructor/UpdateLectureFolderName")
    fun drUpdateFolderName(@Header("Authorization")token:String
                       ,@Query("name") name: String
                       ,@Query("lectureId") lectureId: String
    ):Call<DrUpdateFolderNameResponse>
    @GET("api/Instructor/GetCurrentCourseTasks")
    fun drGetAllAssignment(
        @Header("Authorization")token:String
        ,@Query("cycleId") cycleId:String
    ):Call<ArrayList<DrAllAssignmentsResponseItem>>

    @GET("api/Instructor/GetStudentsWhoUploadThetask")
    fun drGetStudentsWhoUploadTheTas(
        @Header("Authorization")token:String
        ,@Query("taskId") taskId:String
    ):Call<ArrayList<StudentsUploadedTheTaskResponseItem>>
    @Multipart
    @POST("api/Instructor/UploadAssignment")
    fun drUploadAssignment(
        @Query("TaskName") taskName: String,
        @Query("TaskGrade") taskGrade: String,
        @Query("StartDate") startDate: String,
        @Query("EndDate") endDate: String,
        @Query("CourseCycleId") courseCycleId: String,
        @Part file: MultipartBody.Part,
        @Header("Authorization") token: String
    ): Call<ResponseBody>

    @PUT("api/Instructor/UpdateAnAssignment")
    fun updateAssignment(
        @Header("Authorization")token:String
        ,@Query("taskId") taskId:String
        ,@Body updateAssignment:UpdateAssignmentModel
    ):Call<ResponseBody>
    @DELETE("api/Instructor/DeleteAnAssignment")
    fun drDeleteAssignment(
        @Header("Authorization")token:String
        ,@Query("taskId") taskId:String
    ):Call<ResponseBody>
    @GET("api/Instructor/GetAllQuizesForOneCourse")
    fun getAllQuizzesForOneCourse (
        @Query("cycleId") cycleId: String,
        @Header("Authorization") token: String
    ):Call<ArrayList<DrQuizzesResponseItem>>
    @POST("api/Instructor/createQuiz")
    fun createQuiz(@Header("Authorization") token: String,@Body quizItem : DrQuizItem) : Call<ResponseBody>

    @DELETE("api/Instructor/DeleteQuiz")
    fun DrDeleteQuiz(
        @Query("quizId") quizId: String,
        @Header("Authorization") token: String
    ):Call<ResponseBody>

    @POST("api/Account/UpdatePassword")
    fun changePassword(@Body changePasswordResponse: ChangePasswordResponse, @Header("Authorization") token: String):Call<ResponseBody>
    @PUT("api/Instructor/Add Grade For Astudent Task")
    fun drUpdateAssignmentGrade(
        @Header("Authorization")token:String
        ,@Body editAssignmentGradeRequest: EditAssignmentGradeRequest
    ):Call<ResponseBody>

    @Multipart
    @PUT("api/Account/update Photo")
    fun changeProfileImage(
        @Header("Authorization") token: String,
        @Part file: MultipartBody.Part
    ): Call<ResponseBody>

    @GET("api/Instructor/GetAllStudentsEnrolledInAcourse")
    fun drGetStudentsEnrolledInACourse(
        @Header("Authorization")token:String
        ,@Query("CycleId") cycleId:String
    ):Call<ArrayList<AllStuEnrolledInCourseItem>>

    @GET("api/Instructor/GetGradesForCurrentCourseForAstudent")
    fun drGetStuSelectedGradesInACourse(
        @Header("Authorization")token:String
        ,@Query("CycleId") cycleId:String
        ,@Query("studentId") studentId:String
    ):Call<ArrayList<GradesSelectedStuResponseItem>>

}