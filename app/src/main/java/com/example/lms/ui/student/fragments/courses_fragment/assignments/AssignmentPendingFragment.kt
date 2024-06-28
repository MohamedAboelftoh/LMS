package com.example.lms.ui.student.fragments.courses_fragment.assignments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lms.databinding.FragmentAssignmentPendingBinding
import com.example.lms.ui.NotConnectedActivity
import com.example.lms.ui.api.api_student.assignments.AssignmentResponseItem
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.db.DataBase
import com.example.lms.ui.student.checkForInternet
import com.example.lms.ui.student.fragments.Variables
import com.example.lms.ui.student.navigateFromFragment
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
        assignmentAdapter = AssignmentPendingAdapter()
        viewBinding.recyclerAssignmentsPending.adapter = assignmentAdapter
        if(checkForInternet(requireContext())){
            initAssignmentsList()
        }
        else{
            assignmentAdapter.bindAssignments(DataBase.getInstance(requireContext()).stuAssignmentsDao().getAssignmentsFromLocal())
        }
      //  initAssignmentsList()
        onBtnMoreClick()
    }

    private fun initAssignmentsList() {
        myPreferencesToken = MyPreferencesToken(requireContext())
        val token = myPreferencesToken.loadData("token")
        val cycleId = Variables.cycleId
        ApiManager.getApi().getAllAssignmentOfCourse(token!!,cycleId!!).enqueue(object : Callback<ArrayList<AssignmentResponseItem>>{
            override fun onResponse(
                call: Call<ArrayList<AssignmentResponseItem>>,
                response: Response<ArrayList<AssignmentResponseItem>>
            ) {
                if(response.isSuccessful){
                    cashAssignmentsInLocal(response.body())
                    response.body()?.let { assignmentAdapter.bindAssignments(it) }
                }
            }

            override fun onFailure(call: Call<ArrayList<AssignmentResponseItem>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

    }
    private fun onBtnMoreClick(){
        assignmentAdapter.onBtnMoreClickListener = object : AssignmentPendingAdapter.OnBtnMoreClickListener{
            override fun onClick(position: Int, item: AssignmentResponseItem) {
                Variables.taskId=item.taskId
                if (checkForInternet(requireContext())) {
                    navigateToAssignmentDetail(item.taskName, item.endDate)
                }
                else{
                    navigateFromFragment(requireContext(),NotConnectedActivity())
                }
            }
        }
    }

    private fun navigateToAssignmentDetail(taskName: String?, endDate: String?) {
        val intent = Intent(requireActivity() , AssignmentDetailsActivity::class.java)
        intent.putExtra("taskName",taskName)
        intent.putExtra("endDate",endDate)
        startActivity(intent)
        requireActivity().finish()
    }
    private fun cashAssignmentsInLocal(body: MutableList<AssignmentResponseItem>?) {
        DataBase.getInstance(requireContext()).stuAssignmentsDao().deleteAllAssignments()
        DataBase.getInstance(requireContext()).stuAssignmentsDao().insertAssignments(body!!)

    }
}