package com.example.lms.ui.doctor.fragments.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.lms.R
import com.example.lms.databinding.FragmentDrCardDialogBinding
import com.example.lms.ui.api.api_doctor.InstructorInfoResponse
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.db.DataBase
import com.example.lms.ui.student.fragments.Variables
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DrCardDialogFragment : DialogFragment() {
    lateinit var viewBinding:FragmentDrCardDialogBinding
    lateinit var myPreferencesToken: MyPreferencesToken

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.AppTheme_Dialog_Custom)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding= FragmentDrCardDialogBinding.inflate(inflater,container,false)
        myPreferencesToken= MyPreferencesToken(requireContext())
        dialog?.window?.setBackgroundDrawableResource(R.drawable.backgound_dialog_fragment)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindData(DataBase.getInstance(requireContext()).instructorDao().getInstructorFromLocal())
    }
    private fun getInstructorInfo(){
        val token=myPreferencesToken.loadData("token")
        ApiManager.getApi().getInstructorInfo(token!!).enqueue(object :
            Callback<InstructorInfoResponse> {
            override fun onResponse(
                call: Call<InstructorInfoResponse>,
                response: Response<InstructorInfoResponse>
            ) {
                if (response.isSuccessful){
                    bindData(response.body())
                }
                else{
                    Toast.makeText(requireContext(),"Info not downloaded correctly", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<InstructorInfoResponse>, t: Throwable) {
                Toast.makeText(requireContext(),"Info not downloaded", Toast.LENGTH_SHORT).show()

            }

        })
    }

    private fun bindData(body: InstructorInfoResponse?) {
        viewBinding.name.text = body?.fullName
        viewBinding.email.text = body?.email
        viewBinding.number.text = body?.phone
        viewBinding.universityName.text = body?.universityName
        viewBinding.role.text = myPreferencesToken.loadData("role")
        Glide.with(viewBinding.profile)
            .load(body?.imagePath)
            .placeholder(R.drawable.avatar_1)
            .into(viewBinding.profile)
    }


}