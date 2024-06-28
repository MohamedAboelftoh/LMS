package com.example.lms.ui.doctor.fragments.courses.material

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lms.R
import com.example.lms.databinding.FragmentAddFolderBinding
import com.example.lms.ui.api.api_doctor.DrFolderModel
import com.example.lms.ui.api.api_doctor.DrUploadLectureResponse
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.student.fragments.Variables
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
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

            when (viewBinding.radioGroup.checkedRadioButtonId) {
                R.id.lecture -> {
                    addFolder("Lecture")
                }

                R.id.lab -> {
                    addFolder("Lab")
                }
            }
        }
    }

    private fun addFolder(folderType : String) {
        val title = viewBinding.textFolderName.text.toString()
        val cycleId = Variables.cycleId
        val drFolderModel = DrFolderModel(cycleId!!, title, folderType)
        val token = myPreferencesToken.loadData("token")
        ApiManager.getApi()
            .drUploadLecture(drFolderModel,token!!)
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
                    Snackbar.make(viewBinding.root , "Network fail" , Snackbar.LENGTH_LONG).show()
                }

            })
    }
    var onFolderAddedListener : OnFolderAddedListener ?= null
    interface OnFolderAddedListener{
        fun onFolderAdded()
    }
}