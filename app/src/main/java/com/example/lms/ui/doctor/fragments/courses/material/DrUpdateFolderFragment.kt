package com.example.lms.ui.doctor.fragments.courses.material

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.lms.R
import com.example.lms.databinding.FragmentDrUpdateFolderBinding
import com.example.lms.ui.api.api_doctor.DrUpdateFolderNameResponse
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DrUpdateFolderFragment : DialogFragment() {
    lateinit var viewBinding : FragmentDrUpdateFolderBinding
    lateinit var myPreferencesToken: MyPreferencesToken
    var lectureId : String ?= null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentDrUpdateFolderBinding.inflate(inflater,container,false)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.backgound_dialog_fragment)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myPreferencesToken = MyPreferencesToken(requireContext())
        viewBinding.update.setOnClickListener {
          if(viewBinding.textFolderName.text.isNullOrBlank()){
              viewBinding.folderName.error = "required"
          }
            else{
              viewBinding.folderName.error = null
              updateFolder()
          }
        }
    }

    private fun updateFolder() {
        val token = myPreferencesToken.loadData("token")
        val name = viewBinding.textFolderName.text.toString()
        ApiManager.getApi().drUpdateFolderName(token!!,name,lectureId!!)
            .enqueue(object :Callback<DrUpdateFolderNameResponse>{
                override fun onResponse(
                    call: Call<DrUpdateFolderNameResponse>,
                    response: Response<DrUpdateFolderNameResponse>
                ) {
                    if(response.isSuccessful){
                        onFolderUpdatedListener?.onFolderUpdated()
                        dismiss()
                    }
                }

                override fun onFailure(call: Call<DrUpdateFolderNameResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
    }

    var onFolderUpdatedListener : OnFolderUpdatedListener ?= null
    interface OnFolderUpdatedListener{
        fun onFolderUpdated()
    }
}