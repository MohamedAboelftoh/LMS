package com.example.lms.ui.home.fragments.courses_fragment.material

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.lms.databinding.FragmentLectureBinding
import com.example.lms.ui.api.material.CourseMaterialResponse
import com.example.lms.ui.api.material.CourseMaterialResponseItem
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LectureFragment : Fragment() {
    lateinit var viewBinding: FragmentLectureBinding
    lateinit var adapter: LecturesAdapter
    private var lecturesList: List<CourseMaterialResponseItem> = listOf()
    lateinit var myPreferencesToken: MyPreferencesToken
    private lateinit var fragmentContext: Context // Store the context here

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentContext = context // Assign the context when the fragment is attached
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentLectureBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myPreferencesToken = MyPreferencesToken(requireContext())
        adapter = LecturesAdapter()
        viewBinding.lecRecycler.adapter = adapter

        getLectures()
    }

    private fun getLectures() {
        val token = myPreferencesToken.loadData("token")
        ApiManager.getApi().getCourseMaterial(token!!, "CS101FALL2024")
            .enqueue(object : Callback<CourseMaterialResponse> {
                override fun onResponse(
                    call: Call<CourseMaterialResponse>,
                    response: Response<CourseMaterialResponse>
                ) {
                    if (response.isSuccessful) {
                        adapter.bindLectures(response.body()?.courseMaterialResponse)
                    } else {
                        Toast.makeText(fragmentContext, "failed to get the lectures", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<CourseMaterialResponse>, t: Throwable) {
                    Toast.makeText(fragmentContext, "onFailure " + t.localizedMessage, Toast.LENGTH_LONG).show()
                }
            })
    }
}
