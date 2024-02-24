package com.example.lms.ui.home.fragments.courses_fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.lms.databinding.FragmentCoursesBinding
import com.example.lms.ui.api.courses.CoursesResponse
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
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

    }

    private fun uploadCourses() {
        val token=myPreferencesToken.loadData("token")
       ApiManager.getApi().getAllCourses(token!!).enqueue(object :Callback<CoursesResponse>{
           override fun onResponse(
               call: Call<CoursesResponse>,
               response: Response<CoursesResponse>
           ) {
               if (response.isSuccessful) {
                   adapter.bindCourses(response.body()?.coursesResponse)

               }
               else{
                   Toast.makeText(requireContext(),"Courses not downLoaded",Toast.LENGTH_LONG).show()

               }
           }

           override fun onFailure(call: Call<CoursesResponse>, t: Throwable) {
               Toast.makeText(requireContext(),"onFailure "+t.localizedMessage,Toast.LENGTH_LONG).show()

           }
       })
    }


    private fun onCourseClick() {
        adapter.onItemClickListener= CoursesAdapter.OnItemClickListener{ position, course ->
            val intent=Intent(requireActivity(),CourseContent::class.java)
            startActivity(intent)


        }

    }

}