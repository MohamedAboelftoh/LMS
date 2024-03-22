package com.example.lms.ui.doctor.fragments.courses.material

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.lms.R
import com.example.lms.databinding.FragmentDrLabsBinding
import com.example.lms.databinding.FragmentDrLectureBinding
import com.example.lms.ui.api.api_doctor.dr_courses.material.DrLecturesResponseItem
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.student.fragments.Variables
import com.example.lms.ui.student.fragments.courses_fragment.material.LecturesAdapter
import com.example.lms.ui.student.fragments.courses_fragment.material.MaterialFiles
import com.example.lms.ui.student.navigateFromFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DrLectureFragment : Fragment() {
    lateinit var viewBinding:FragmentDrLectureBinding
    lateinit var adapter: DrLecturesAdapter
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
        viewBinding = FragmentDrLectureBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myPreferencesToken = MyPreferencesToken(requireContext())
        adapter = DrLecturesAdapter()
        viewBinding.lecRecycler.adapter = adapter
        getLectures()
        onLectureClick()
    }
    private fun getLectures() {
        val cycleId = Variables.cycleId
        val token = myPreferencesToken.loadData("token")
        ApiManager.getApi().getDrCourseMaterial(token!!,cycleId!!).enqueue(object :Callback<ArrayList<DrLecturesResponseItem>>{
            override fun onResponse(
                call: Call<ArrayList<DrLecturesResponseItem>>,
                response: Response<ArrayList<DrLecturesResponseItem>>
            ) {
                if (response.isSuccessful) {
                    adapter.bindLectures(response.body())
                } else {
                    Toast.makeText(fragmentContext, "failed to get the lectures", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ArrayList<DrLecturesResponseItem>>, t: Throwable) {
                Toast.makeText(fragmentContext, "onFailure " + t.localizedMessage, Toast.LENGTH_LONG).show()

            }
        })
    }
    private fun onLectureClick(){
        adapter.onItemClickListener=
            DrLecturesAdapter.OnItemClickListener { position, item ->
                Variables.lecId = item.lectureId
                navigateFromFragment(requireContext(),DrFilesActivity())
            }
    }
}