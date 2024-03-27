package com.example.lms.ui.doctor.fragments.courses.assignment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.example.lms.R
import com.example.lms.databinding.FragmentStudentNamesBinding
import com.example.lms.ui.api.api_doctor.dr_courses.assignments.StudentsUploadedTheTaskResponseItem
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.student.fragments.Variables
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StudentNamesFragment : DialogFragment() {
    lateinit var viewBinding : FragmentStudentNamesBinding
    lateinit var myPreferencesToken: MyPreferencesToken

    lateinit var adapter: StudentsNameAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentStudentNamesBinding.inflate(inflater,container,false)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.backgound_dialog_fragment)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myPreferencesToken= MyPreferencesToken(requireContext())
        adapter= StudentsNameAdapter()
        viewBinding.studentsRecycler.adapter=adapter
        getStudents()
    }

    private fun getStudents() {
        val token=myPreferencesToken.loadData("token")
        val taskId=Variables.taskId
        ApiManager.getApi().drGetStudentsWhoUploadTheTas(token!!,taskId!!).enqueue(object
            :Callback<ArrayList<StudentsUploadedTheTaskResponseItem>>{
            override fun onResponse(
                call: Call<ArrayList<StudentsUploadedTheTaskResponseItem>>,
                response: Response<ArrayList<StudentsUploadedTheTaskResponseItem>>
            ) {
                if(response.isSuccessful){
                    adapter.bindStudentsList(response.body())
                    Toast.makeText(requireContext(),"successful", Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(requireContext(),"failed to get Students Name", Toast.LENGTH_SHORT).show()

                }
            }

            override fun onFailure(
                call: Call<ArrayList<StudentsUploadedTheTaskResponseItem>>,
                t: Throwable
            ) {
                Toast.makeText(requireContext(),"${t.localizedMessage}",Toast.LENGTH_SHORT).show()

            }
        })
    }


}