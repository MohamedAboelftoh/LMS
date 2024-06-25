package com.example.lms.ui.doctor.fragments.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.lms.R
import com.example.lms.databinding.FragmentDrCardDialogBinding
import com.example.lms.ui.api.api_doctor.InstructorInfoResponse
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DrCardDialogFragment : DialogFragment() {
    lateinit var viewBinding:FragmentDrCardDialogBinding
    lateinit var myPreferencesToken: MyPreferencesToken

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding= FragmentDrCardDialogBinding.inflate(inflater,container,false)
        myPreferencesToken= MyPreferencesToken(requireContext())
        getDialog()?.getWindow()?.setBackgroundDrawableResource(R.drawable.backgound_dialog_fragment)
        return viewBinding.root
    }
    fun getInstructorInfo(){
        val token=myPreferencesToken.loadData("token")
        ApiManager.getApi().getInstructorInfo(token!!).enqueue(object :
            Callback<InstructorInfoResponse> {
            override fun onResponse(
                call: Call<InstructorInfoResponse>,
                response: Response<InstructorInfoResponse>
            ) {
                if (response.isSuccessful){
                    viewBinding.name.text=response.body()?.fullName
                    viewBinding.universityName.text=response.body()?.universityName
                    viewBinding.faculty.text=response.body()?.facultyName

//                    Glide.with(viewBinding.imgPro)
//                        .load(response.body()?.imagePath)
//                        .placeholder(R.drawable.avatar_1)
//                        .into(viewBinding.profile)
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


}