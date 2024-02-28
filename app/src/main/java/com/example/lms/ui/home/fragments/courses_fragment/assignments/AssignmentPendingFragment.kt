package com.example.lms.ui.home.fragments.courses_fragment.assignments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lms.databinding.FragmentAssignmentPendingBinding
import com.example.lms.ui.api.assignments.AssignmentResponseItem
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.home.fragments.courses_fragment.material.Variables
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AssignmentPendingFragment : Fragment() {
    lateinit var viewBinding: FragmentAssignmentPendingBinding
    //private var assignmentsList: MutableList<AssignmentItem> = mutableListOf()
    private lateinit var assignmentAdapter: AssignmentPendingAdapter
    lateinit var myPreferencesToken: MyPreferencesToken
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentAssignmentPendingBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAssignmentsList()
    }

    private fun initAssignmentsList() {
        myPreferencesToken = MyPreferencesToken(requireContext())
        val token = myPreferencesToken.loadData("token")
        val cycleId = Variables.cycleId
        ApiManager.getApi().getAllAssignmentOfCourse(token!!,cycleId!!).enqueue(object : Callback<MutableList<AssignmentResponseItem>>{
            override fun onResponse(
                call: Call<MutableList<AssignmentResponseItem>>,
                response: Response<MutableList<AssignmentResponseItem>>
            ) {
                if(response.isSuccessful){
                    assignmentAdapter.bindAssignments(response.body())
                }
            }

            override fun onFailure(call: Call<MutableList<AssignmentResponseItem>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
        assignmentAdapter = AssignmentPendingAdapter()
        viewBinding.recyclerAssignmentsPending.adapter = assignmentAdapter
        assignmentAdapter.onBtnMoreClickListener = object : AssignmentPendingAdapter.OnBtnMoreClickListener{
            override fun onClick(position: Int, item: AssignmentResponseItem) {
                navigateToAssignmentDetail(item.taskName,item.createdAt)
            }
        }

    }

    private fun navigateToAssignmentDetail(taskName: String?, createdAt: String?) {
        val intent = Intent(requireActivity() , AssignmentDetailsActivity::class.java)
        intent.putExtra("taskName",taskName)
        intent.putExtra("createdAt",createdAt)
        startActivity(intent)
    }
}