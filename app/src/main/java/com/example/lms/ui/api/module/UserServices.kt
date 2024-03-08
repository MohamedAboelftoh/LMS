package com.example.lms.ui.api.module

import com.example.lms.ui.api.account.AccountInfoResponse
import com.example.lms.ui.api.assignments.AssignmentResponseItem
import com.example.lms.ui.api.calender.CalenderRequest
import com.example.lms.ui.api.courses.CoursesResponseItem
import com.example.lms.ui.api.login.LoginRequest
import com.example.lms.ui.api.login.LoginResponse
import com.example.lms.ui.api.material.CourseMaterialResponseItem
import com.example.lms.ui.api.material.fiels.FielslResponseItem
import com.example.lms.ui.api.news.NewsResponseItem
import com.example.lms.ui.api.quizes.CourseQuizzesResponseItem
import com.example.lms.ui.api.quizes.QuizQuestionsResponse
import com.example.lms.ui.api.quizes.submit.SubmitQuizRequest
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
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

    @GET("api/Students/CurrentCourseMaterial")
    fun getCourseMaterial(@Header("Authorization")token:String
                          ,@Query("cycleId")cycleId:String
    ):Call<ArrayList<CourseMaterialResponseItem>>

    @GET("api/Students/CurrentCourseTasks")
    fun getAllAssignmentOfCourse(@Header("Authorization")token:String
                                 ,@Query("cycleId")cycleId:String) : Call<MutableList<AssignmentResponseItem>>
    @GET("api/Students/CurrentCourseQuizzes")
    fun getCourseQuizzes(@Header("Authorization")token:String
                         ,@Query("cycleId")cycleId:String
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

    @Multipart
    @POST("api/Students/File/Upload")
    fun uploadFile(
        @Query("taskid") taskId: String,

        @Part file: MultipartBody.Part,

        @Header("Authorization") token: String
    ): Call<ResponseBody>

    @POST("api/Calendar")
    fun addNewEvent(@Header("Authorization")token:String , @Body calenderRequest: CalenderRequest) : Call<ResponseBody>
}