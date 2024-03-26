package com.example.lms.ui.api.module

import com.example.lms.ui.api.api_doctor.DrUpdateFolderNameResponse
import com.example.lms.ui.api.api_doctor.DrUploadFileResponse
import com.example.lms.ui.api.api_doctor.DrUploadLectureResponse
import com.example.lms.ui.api.api_doctor.InstructorInfoResponse
import com.example.lms.ui.api.api_doctor.dr_courses.DrCoursesResponseItem
import com.example.lms.ui.api.api_doctor.dr_courses.assignments.DrAllAssignmentsResponseItem
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
import com.example.lms.ui.api.api_student.material.fiels.FielslResponseItem
import com.example.lms.ui.api.api_student.news.NewsResponseItem
import com.example.lms.ui.api.api_student.quizes.CourseQuizzesResponseItem
import com.example.lms.ui.api.api_student.quizes.QuizQuestionsResponse
import com.example.lms.ui.api.api_student.quizes.submit.SubmitQuizRequest
import com.example.lms.ui.api.api_doctor.dr_courses.material.DrFilesResponseItem
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
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
                                 , @Query("cycleId")cycleId:String) : Call<MutableList<AssignmentResponseItem>>
    @GET("api/Students/CurrentCourseQuizzes")
    fun getCourseQuizzes(@Header("Authorization")token:String
                         , @Query("cycleId")cycleId:String
    ):Call<ArrayList<CourseQuizzesResponseItem>>

    @GET("api/Students/Quiz")
    fun getQuizQuestions(@Header("Authorization")token:String
                         ,@Query("quizId")quizId: String
    ):Call<QuizQuestionsResponse>

    @POST("api/Students/quiz/submit")
    fun submitQuiz(@Body submitQuizRequest: SubmitQuizRequest, @Query("quizId")quizId: String,
                   @Header("Authorization")token:String) : Call< List<Map<String?,Boolean?>>>

    @GET("api/Students/GetStudentInfo")
    fun getAccountInfo(@Header("Authorization")token:String):Call<AccountInfoResponse>
    @GET("api/Students/Getfilesoflecture")
    fun getFiles(@Header("Authorization")token:String?,
                 @Query("lectureId")lectureId:String?):Call<MutableList<FielslResponseItem>>
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
        @Query("title") title: String,
        @Query("CycleId") cycleId: String,
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
}