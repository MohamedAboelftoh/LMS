package com.example.lms.ui.doctor.fragments.courses.material

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lms.R
import com.example.lms.databinding.FragmentAddFolderBinding
import com.example.lms.ui.api.api_doctor.DrUploadLectureResponse
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.student.fragments.Variables
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddFolderFragment : BottomSheetDialogFragment() {
    lateinit var myPreferencesToken: MyPreferencesToken
    lateinit var viewBinding : FragmentAddFolderBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentAddFolderBinding.inflate(inflater,container,false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myPreferencesToken = MyPreferencesToken(requireContext())
        viewBinding.addFolder.setOnClickListener {
            addFolder()
        }
    }

    private fun addFolder() {
        val title = viewBinding.textFolderName.text.toString()
        val cycleId = Variables.cycleId
        val token = myPreferencesToken.loadData("token")
        ApiManager.getApi()
            .drUploadLecture(title,cycleId!!,token!!)
            .enqueue(object :Callback<DrUploadLectureResponse>{
                override fun onResponse(
                    call: Call<DrUploadLectureResponse>,
                    response: Response<DrUploadLectureResponse>
                ) {
                    if(response.isSuccessful){
                        onFolderAddedListener?.onFolderAdded()
                        dismiss()
                    }
                }

                override fun onFailure(call: Call<DrUploadLectureResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
    }
    var onFolderAddedListener : OnFolderAddedListener ?= null
    interface OnFolderAddedListener{
        fun onFolderAdded()
    }
}