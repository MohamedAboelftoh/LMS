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
import com.example.lms.ui.api.api_student.account.AccountInfoResponse
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.db.DataBase
import com.example.lms.ui.student.checkForInternet
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CardDialogFragment : DialogFragment() {
    lateinit var viewBinding:FragmentCardDialogBinding
    lateinit var myPreferencesToken: MyPreferencesToken
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.AppTheme_Dialog_Custom)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        viewBinding=FragmentCardDialogBinding.inflate(inflater
            ,container,false)
        myPreferencesToken= MyPreferencesToken(requireContext())
        dialog?.window?.setBackgroundDrawableResource(R.drawable.backgound_dialog_fragment)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(checkForInternet(requireContext())) {
            loadAccountInfo()
        }
        else {
            bindData(
                DataBase.getInstance(requireContext()).studentInfoDao().getStuInfoFromLocal())
        }
    }
    fun loadAccountInfo(){
        val token=myPreferencesToken.loadData("token")
        ApiManager.getApi().getAccountInfo(token!!).enqueue(object : Callback<AccountInfoResponse> {
            override fun onResponse(
                call: Call<AccountInfoResponse>,
                response: Response<AccountInfoResponse>
            ) {
                if (response.isSuccessful){
                    cashInfoInLocal(response.body())
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

    private fun cashInfoInLocal(body: AccountInfoResponse?) {
        DataBase.getInstance(requireContext()).studentInfoDao().deleteStuFromLocal()
        DataBase.getInstance(requireContext()).studentInfoDao().insertStudentInfo(body!!)

    }

    private fun bindData(body: AccountInfoResponse?) {
        viewBinding.name.text=body?.fullName
        viewBinding.department.text=body?.departmentName
        viewBinding.level.text=body?.level.toString()
        viewBinding.academicId.text=body?.academicId
        viewBinding.universityName.text=body?.universityName
        Glide.with(viewBinding.profile)
            .load(body?.imagePath)
            .placeholder(R.drawable.avatar_1)
            .into(viewBinding.profile)

    }
}