package com.example.lms.ui.home.fragments.courses_fragment

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
import com.example.lms.ui.api.courses.CoursesResponseItem
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.home.fragments.Variables
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
        uploadCourses()
        adapter= CoursesAdapter()
        viewBinding.coursesRecView.adapter=adapter
        onCourseClick()
       callTextChanges()

    }
    private fun uploadCourses() {
        val token=myPreferencesToken.loadData("token")
       ApiManager.getApi().getAllCourses(token!!).enqueue(object :Callback<ArrayList<CoursesResponseItem>>{
           override fun onResponse(
               call: Call<ArrayList<CoursesResponseItem>>,
               response: Response<ArrayList<CoursesResponseItem>>
           ) {
               if (response.isSuccessful) {
                   adapter.bindCourses(response.body())
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