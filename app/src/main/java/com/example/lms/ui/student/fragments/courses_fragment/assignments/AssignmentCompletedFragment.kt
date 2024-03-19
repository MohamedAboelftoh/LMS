package com.example.lms.ui.student.fragments.courses_fragment.assignments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lms.databinding.FragmentAssignmentCompletedBinding
import com.example.lms.ui.api.assignments.AssignmentResponseItem
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.student.fragments.Variables
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AssignmentCompletedFragment : Fragment() {
    lateinit var viewBinding: FragmentAssignmentCompletedBinding
    lateinit var myPreferencesToken: MyPreferencesToken
    private lateinit var adapter: AssignmentCompletedAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentAssignmentCompletedBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myPreferencesToken= MyPreferencesToken(requireContext())
        adapter = AssignmentCompletedAdapter()
        viewBinding.recyclerAssignmentsCompleted.adapter = adapter
        initAssignmentsList()
    }
    private fun initAssignmentsList() {
        myPreferencesToken = MyPreferencesToken(requireContext())
        val token = myPreferencesToken.loadData("token")
        val cycleId = Variables.cycleId
        ApiManager.getApi().getAllAssignmentOfCourse(token!!,cycleId!!).enqueue(object :
            Callback<MutableList<AssignmentResponseItem>> {
            override fun onResponse(
                call: Call<MutableList<AssignmentResponseItem>>,
                response: Response<MutableList<AssignmentResponseItem>>
            ) {
                if(response.isSuccessful){
                    adapter.bindAssignments(response.body())

                }
            }

            override fun onFailure(call: Call<MutableList<AssignmentResponseItem>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

    }

}