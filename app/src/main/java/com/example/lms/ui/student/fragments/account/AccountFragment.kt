package com.example.lms.ui.student.fragments.account

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.lms.R
import com.example.lms.databinding.FragmentAccountBinding
import com.example.lms.ui.api.api_doctor.InstructorInfoResponse
import com.example.lms.ui.api.api_student.account.AccountInfoResponse
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.db.DataBase
import com.example.lms.ui.login.LoginActivity
import com.example.lms.ui.resetPassword.ChangePasswordActivity
import com.example.lms.ui.student.checkForInternet
import com.example.lms.ui.student.navigateFromFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AccountFragment : Fragment() {
lateinit var viewBinding :FragmentAccountBinding
lateinit var myPreferencesToken: MyPreferencesToken
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentAccountBinding.inflate(inflater,container,false)
        return viewBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myPreferencesToken= MyPreferencesToken(requireContext())
        viewBinding.changePassword.setOnClickListener {
            navigateFromFragment(requireContext(), ChangePasswordActivity())
        }
        viewBinding.changeImage.setOnClickListener {
           // navigateToChangePhone()
        }
        viewBinding.btnShowCard.setOnClickListener {
            showCardFragment()
        }
        btnLogoutClickListener()
    if (checkForInternet(requireContext())){
        loadAccountInfo()
    }
        else{
        bindData(DataBase.getInstance(requireContext()).studentInfoDao().getStuInfoFromLocal())
    }

    }

    private fun showCardFragment() {
        val cardFragment = CardDialogFragment()
        cardFragment.show(childFragmentManager,null)
    }

//    private fun navigateToChangePhone() {
//        val intent = Intent(requireContext(),ChangePhoneActivity::class.java)
//        startActivity(intent)
//    }
//
//    private fun navigateToChangePassword() {
//        val intent = Intent(requireContext(),ChangePasswordActivity::class.java)
//        startActivity(intent)
//    }
    private fun logout() {
        saveCredentials("","")
        navigateToLoginActivity()
    }
    private fun navigateToLoginActivity() {
        val intent = Intent(requireContext(),LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        activity?.finish()
    }

    private fun saveCredentials(email: String, password: String) {
        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("email", email)
        editor.putString("password", password)
        editor.apply()
    }


    fun showMessage(message:String
                    ,posActionName:String?=null
                    ,posAction:DialogInterface.OnClickListener?=null
                    ,negActionName:String?=null
                    ,negAction: DialogInterface.OnClickListener?=null

    ):AlertDialog{
        val dialogBuilder=AlertDialog.Builder(requireContext())
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
    fun btnLogoutClickListener(){
        viewBinding.logoutBtn.setOnClickListener {
            showMessage("Do you Sure To Logout "
                ,posActionName = "OK",
                posAction = { dialogInterface,i->
                    dialogInterface.dismiss()
                    logout()
                          },

                negActionName = "Cansel"
                , negAction = { dialogInterface, i ->
                    dialogInterface.dismiss()

                }
            )

        }
    }
    private fun bindData(studentFromLocal: AccountInfoResponse) {
        viewBinding.userName.text = studentFromLocal.fullName
        viewBinding.department.text = studentFromLocal.departmentName
        viewBinding.level.text = studentFromLocal.level.toString()
        viewBinding.userIdNumber.text=studentFromLocal.academicId.toString()

        Glide.with(viewBinding.profile)
            .load(studentFromLocal.imagePath)
            .placeholder(R.drawable.avatar_1)
            .into(viewBinding.profile)
    }

    private fun loadAccountInfo(){
        val token=myPreferencesToken.loadData("token")
        ApiManager.getApi().getAccountInfo(token!!).enqueue(object : Callback<AccountInfoResponse>{
            override fun onResponse(
                call: Call<AccountInfoResponse>,
                response: Response<AccountInfoResponse>
            ) {
                if (response.isSuccessful){
                    cashInfoToLocal(response.body())
                    bindDataFromApi(response.body())
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

    private fun cashInfoToLocal(body: AccountInfoResponse?) {
        DataBase.getInstance(requireContext()).studentInfoDao().deleteStuFromLocal()

        DataBase.getInstance(requireContext()).studentInfoDao().insertStudentInfo(body!!)
    }

    private fun bindDataFromApi(body: AccountInfoResponse?) {
        viewBinding.userName.text=body?.fullName
        viewBinding.department.text=body?.departmentName
        viewBinding.level.text=body?.level.toString()
        viewBinding.userIdNumber.text=body?.academicId
        Glide.with(viewBinding.imgPro)
            .load(body?.imagePath)
            .placeholder(R.drawable.avatar_1)
            .into(viewBinding.profile)
    }
}