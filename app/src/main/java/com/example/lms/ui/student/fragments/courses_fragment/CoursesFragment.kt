package com.example.lms.ui.student.fragments.courses_fragment

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.lms.R
import com.example.lms.databinding.FragmentCoursesBinding
import com.example.lms.ui.NotConnectedActivity
import com.example.lms.ui.api.api_student.courses.CoursesResponseItem
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.db.DataBase
import com.example.lms.ui.student.checkForInternet
import com.example.lms.ui.student.fragments.Variables
import com.example.lms.ui.student.navigateFromFragment
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CoursesFragment : Fragment() {
    lateinit var viewBinding: FragmentCoursesBinding
    lateinit var adapter: CoursesAdapter
    lateinit var myPreferencesToken: MyPreferencesToken
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding=FragmentCoursesBinding.inflate(inflater,container,false)
        return viewBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myPreferencesToken= MyPreferencesToken(requireContext())
        adapter= CoursesAdapter()
        viewBinding.coursesRecView.adapter=adapter
        if(checkForInternet(requireContext())){
            uploadCourses()
            onCourseClick()
        }
        else{
            uploadCoursesFromLocal()
            onCourseClick()
        }
       // uploadCourses()
//        onCourseClick()
       callTextChanges()

    }
    private fun uploadCoursesFromLocal(){
        adapter.bindCourses(DataBase.getInstance(requireContext()).studentCourses().getCoursesFromLocal())
    }

    private fun uploadCourses() {
        val token=myPreferencesToken.loadData("token")
       ApiManager.getApi().getAllCourses(token!!).enqueue(object :Callback<ArrayList<CoursesResponseItem>>{
           override fun onResponse(
               call: Call<ArrayList<CoursesResponseItem>>,
               response: Response<ArrayList<CoursesResponseItem>>
           ) {
               if (response.code()==200) {
                   cacheCoursesInLocal(response.body())

                   adapter.bindCourses(response.body())
               }
               else if(response.code()==204){
                   pushFragment(NotActiveStudentFragment())
               }
               else{
                   Toast.makeText(requireContext(),"Courses not downLoaded",Toast.LENGTH_LONG).show()
               }
           }
           override fun onFailure(call: Call<ArrayList<CoursesResponseItem>>, t: Throwable) {
               Toast.makeText(requireContext(),"onFailure "+t.localizedMessage,Toast.LENGTH_LONG).show()
           }
       })
    }

    private fun pushFragment(notActiveStudentFragment: Fragment) {
        parentFragmentManager.beginTransaction()
            .replace(R.id.home_dr_container,notActiveStudentFragment)
            .addToBackStack(null)
            .commit()
    }

    private fun cacheCoursesInLocal(body: ArrayList<CoursesResponseItem>?) {
        DataBase.getInstance(requireContext()).studentCourses().deleteAllCourses()
        DataBase.getInstance(requireContext()).studentCourses().insertCourses(body!!)
    }

    private fun onCourseClick() {
        adapter.onItemClickListener= CoursesAdapter.OnItemClickListener{ position, course ->
            Variables.cycleId = course?.cycleId
            Variables.courseName = course?.name
            val intent=Intent(requireActivity(),CourseContent::class.java)
            startActivity(intent)
        }

    }
    private fun callTextChanges() {
        val textView = viewBinding.tvEnrolled
        val fullText = "Enrolled 5 Courses"
        val subString = "5"
        val color = R.color.green
        val textSizeRatio = 1.5f

        changeTextColorAndSize(textView, fullText, subString, color, textSizeRatio)
    }
    private fun changeTextColorAndSize(textView: TextView, fullText: String, subString: String, color: Int, textSizeRatio: Float) {
        val spannableStringBuilder = SpannableStringBuilder(fullText)
        val startIndex = fullText.indexOf(subString)

        if (startIndex != -1) {
            val endIndex = startIndex + subString.length
            // Change color of the substring
            val colorSpan = ForegroundColorSpan(color)
            spannableStringBuilder.setSpan(colorSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            // Change text size of the substring
            val relativeSizeSpan = RelativeSizeSpan(textSizeRatio)
            spannableStringBuilder.setSpan(relativeSizeSpan, startIndex, endIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            textView.text = spannableStringBuilder
        }
    }

}