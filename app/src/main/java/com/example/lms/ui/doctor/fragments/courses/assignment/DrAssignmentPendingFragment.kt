package com.example.lms.ui.doctor.fragments.courses.assignment

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.lms.databinding.FragmentDrAssignmentPendingBinding
import com.example.lms.ui.api.api_doctor.dr_courses.assignments.DrAllAssignmentsResponseItem
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.student.fragments.Variables
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DrAssignmentPendingFragment : Fragment() {
    lateinit var viewBinding:FragmentDrAssignmentPendingBinding
    lateinit var myPreferencesToken: MyPreferencesToken
    private lateinit var adapter: DrAssignPendingAdapter
    val studentsNameFragment=StudentNamesFragment()
    val editAssignmentFragment=EditAssignmentFragment()
    val drAddAssignmentActivity = DrAddAssignmentActivity()


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
        drAddAssignmentActivity.onAssignmentAddedListener = object : DrAddAssignmentActivity.OnAssignmentAddedListener{
            override fun onAssignmentAdded() {
                getAssignments()
            }

        }
        editAssignmentFragment.onAssignmentEditListener = object : EditAssignmentFragment.OnAssignmentEditListener{
            override fun onAssignmentEdit() {
                getAssignments()
            }
        }
        getAssignments()
        navigateToDetailsActivity()
        seeWhoUploadAssignment()
        editAssignment()
        deleteAssignment()
    }

    private fun deleteAssignment() {
        adapter.onIconDeleteClickListener=object :DrAssignPendingAdapter.OnIconDeleteClickListener{
            override fun iconDeleteClick(item: DrAllAssignmentsResponseItem, position: Int) {
                Variables.taskId=item.taskId
                     showMessage("Do you Sure To Delete "
                ,posActionName = "OK",
                posAction = { dialogInterface,i->
                    dialogInterface.dismiss()
                    deleteAssignFromDB()
                },

                negActionName = "Cansel"
                , negAction = { dialogInterface, i ->
                    dialogInterface.dismiss()

                }
            )
            }
        }
    }
    fun deleteAssignFromDB(){
        val token=myPreferencesToken.loadData("token")
        ApiManager.getApi().drDeleteAssignment(token!!,Variables.taskId!!).enqueue(object :Callback<ResponseBody>{
            override fun onResponse(
                call: Call<ResponseBody>,
                response: Response<ResponseBody>
            ) {
                if(response.isSuccessful){
                    getAssignments()
                    Toast.makeText(requireContext(),"Deleted successful", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(requireContext(),"failed to Deleted", Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(requireContext(),t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
    fun showMessage(message:String
                    ,posActionName:String?=null
                    ,posAction: DialogInterface.OnClickListener?=null
                    ,negActionName:String?=null
                    ,negAction: DialogInterface.OnClickListener?=null

    ): AlertDialog {
        val dialogBuilder= AlertDialog.Builder(requireContext())
        dialogBuilder.setMessage(message)
        if (posActionName!=null)
        {
            dialogBuilder.setPositiveButton(posActionName,posAction)
        }
        if(negActionName!=null)
        {
            dialogBuilder.setNegativeButton(negActionName,negAction)

        }
        return dialogBuilder.show()
    }
    private fun editAssignment() {
        adapter.onIconEditClickListener=object :DrAssignPendingAdapter.OnIconEditClickListener{
            override fun iconEditClick(item: DrAllAssignmentsResponseItem, position: Int) {
                Variables.taskId=item.taskId
                editAssignmentFragment.show(parentFragmentManager,"")

            }
        }
    }

    private fun seeWhoUploadAssignment() {
        adapter.onIconSeeClickListener=object :DrAssignPendingAdapter.OnIconSeeClickListener{
            override fun iconSeeClick(item: DrAllAssignmentsResponseItem, position: Int) {
                Variables.taskId=item.taskId
                studentsNameFragment.show(parentFragmentManager,"")
            }
        }
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