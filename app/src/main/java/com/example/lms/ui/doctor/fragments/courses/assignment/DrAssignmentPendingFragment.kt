package com.example.lms.ui.doctor.fragments.courses.assignment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.lms.R
import com.example.lms.databinding.FragmentDrAssignmentPendingBinding
import com.example.lms.ui.api.api_doctor.dr_courses.assignments.DrAllAssignmentsResponseItem
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.student.fragments.Variables
import com.example.lms.ui.student.navigateFromFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DrAssignmentPendingFragment : Fragment() {
    lateinit var viewBinding:FragmentDrAssignmentPendingBinding
    lateinit var myPreferencesToken: MyPreferencesToken
    private lateinit var adapter: DrAssignPendingAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding=FragmentDrAssignmentPendingBinding.inflate(inflater,container,false)
        return viewBinding.root

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myPreferencesToken= MyPreferencesToken(requireContext())
        adapter = DrAssignPendingAdapter()
        viewBinding.drRecyclerAssignPending .adapter = adapter
        getAssignments()
        navigateToDetailsActivity()

    }

    private fun navigateToDetailsActivity() {
        adapter.onButtonMoreClickListener=object :DrAssignPendingAdapter.OnButtonMoreClickListener{
            override fun buttonMoreClick(item: DrAllAssignmentsResponseItem, position: Int)
            {
                Variables.taskId=item.taskId
                val intent=Intent(requireContext(),DrAssignPendingDetailsActivity::class.java )
                intent.putExtra("position",position)
                startActivity(intent)
                // navigateFromFragment(requireContext(),DrAssignPendingDetailsActivity())

            }
        }

    }

    fun getAssignments(){
        val token=myPreferencesToken.loadData("token")
        val cycleId= Variables.cycleId
        ApiManager.getApi().drGetAllAssignment(token!!,cycleId!!).enqueue(object :
            Callback<ArrayList<DrAllAssignmentsResponseItem>> {
            override fun onResponse(
                call: Call<ArrayList<DrAllAssignmentsResponseItem>>,
                response: Response<ArrayList<DrAllAssignmentsResponseItem>>
            ) {
                if(response.isSuccessful){
                    adapter.bindAssignments(response.body())
                    Toast.makeText(requireContext(),"successful", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(requireContext(),"failed to get assignments", Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(
                call: Call<ArrayList<DrAllAssignmentsResponseItem>>,
                t: Throwable
            ) {
                Toast.makeText(requireContext(),"${t.localizedMessage}", Toast.LENGTH_SHORT).show()

            }
        })
    }

}