package com.example.lms.ui.student.fragments.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.lms.R
import com.example.lms.databinding.FragmentCardDialogBinding
import com.example.lms.ui.api.account.AccountInfoResponse
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CardDialogFragment : DialogFragment() {
    lateinit var viewBinding:FragmentCardDialogBinding
    lateinit var myPreferencesToken:MyPreferencesToken
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewBinding=FragmentCardDialogBinding.inflate(inflater
            ,container,false)
        myPreferencesToken=MyPreferencesToken(requireContext())
        getDialog()?.getWindow()?.setBackgroundDrawableResource(R.drawable.backgound_dialog_fragment);


        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadAccountInfo()
    }
    fun loadAccountInfo(){
        val token=myPreferencesToken.loadData("token")
        ApiManager.getApi().getAccountInfo(token!!).enqueue(object : Callback<AccountInfoResponse> {
            override fun onResponse(
                call: Call<AccountInfoResponse>,
                response: Response<AccountInfoResponse>
            ) {
                if (response.isSuccessful){
                    viewBinding.name.text=response.body()?.fullName
                    viewBinding.department.text=response.body()?.departmentName
                    viewBinding.level.text=response.body()?.level.toString()
                    viewBinding.academicId.text=response.body()?.academicId
                    viewBinding.universityName.text=response.body()?.universityName
                    Glide.with(viewBinding.profile)
                        .load(response.body()?.imagePath)
                        .placeholder(R.drawable.avatar_1)
                        .into(viewBinding.profile)
                }
                else{
                    Toast.makeText(requireContext(),"Info not downloaded correctly", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AccountInfoResponse>, t: Throwable) {
                Toast.makeText(requireContext(),"OnFailure"+t.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }
}