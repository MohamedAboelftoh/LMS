package com.example.lms.ui.doctor.fragments.account

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.lms.R
import com.example.lms.databinding.FragmentDrAccountBinding
import com.example.lms.ui.api.api_doctor.InstructorInfoResponse
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.db.DataBase
import com.example.lms.ui.login.LoginActivity
import com.example.lms.ui.resetPassword.ChangePasswordActivity
import com.example.lms.ui.student.fragments.Variables
import com.example.lms.ui.student.navigateFromFragment
import com.github.dhaval2404.imagepicker.ImagePicker
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Url
import java.io.File
import java.io.FileOutputStream

class DrAccountFragment : Fragment() {
lateinit var viewBinding:FragmentDrAccountBinding
lateinit var myPreferencesToken: MyPreferencesToken


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
            viewBinding=  FragmentDrAccountBinding.inflate(inflater,container,false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        myPreferencesToken= MyPreferencesToken(requireContext())
        viewBinding.btnShowCard.setOnClickListener {
            showCardFragment()
        }
        viewBinding.btnLogout.setOnClickListener {
            btnLogoutClickListener()
        }
        viewBinding.changePassword.setOnClickListener {
            navigateFromFragment(requireContext(),ChangePasswordActivity())
        }
        viewBinding.changeImage.setOnClickListener {
            changeImage()
        }
        viewBinding.icCamera.setOnClickListener{
            changeImage()
        }
        getInstructorInfo()
       // bindData(DataBase.getInstance(requireContext()).instructorDao().getInstructorFromLocal())
    }
    private fun changeImage(){
        ImagePicker.with(this)
            .crop()
            .compress(1024)
            .maxResultSize(1080, 1080)
            .start()
    }
    private fun uploadImageSelected(imageUrl: Uri) {
//        val token = myPreferencesToken.loadData("token")
//        val filesDir = requireContext().filesDir
//        val file = File(filesDir, "image.png") // Change the file extension to .jpg for images
//
//        val inputStream = requireContext().contentResolver.openInputStream(imageUrl)
//        val outputStream = FileOutputStream(file)
//        inputStream?.copyTo(outputStream)
//        inputStream?.close()
//        outputStream.close()
//
//        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull()) // Change the MIME type to image/*
//        val part = MultipartBody.Part.createFormData("file", file.name, requestBody)
        val token = myPreferencesToken.loadData("token")
        val requestBody = RequestBody.create("*/*".toMediaTypeOrNull()
            , requireContext().contentResolver.openInputStream(imageUrl)!!.readBytes())
        val part = MultipartBody.Part.createFormData("file", "profile_image.jpg", requestBody) // Specify a filename

        ApiManager.getApi().changeProfileImage(token!!, part).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(p0: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Image changed", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Error in changing image", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(p0: Call<ResponseBody>, p1: Throwable) {
                Toast.makeText(requireContext(), p1.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun bindData(instructorFromLocal: InstructorInfoResponse) {
        viewBinding.userName.text = instructorFromLocal.fullName
        Glide.with(viewBinding.profile)
            .load(instructorFromLocal.imagePath)
            .placeholder(R.drawable.avatar_1)
            .into(viewBinding.profile)
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        viewBinding.profile.setImageURI(data?.data)
//        uploadImageSelected(data?.data!!)
//    }
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    val imageUrl = data?.data  // Use safe call to avoid NPE if data is null
    if (imageUrl != null) {
        uploadImageSelected(imageUrl)
    } else {
        Toast.makeText(requireContext(), "Error: Image URI is null", Toast.LENGTH_SHORT).show()
    }
}



    private fun getInstructorInfo(){
        val token=myPreferencesToken.loadData("token")
        ApiManager.getApi().getInstructorInfo(token!!).enqueue(object :Callback<InstructorInfoResponse>{
            override fun onResponse(
                call: Call<InstructorInfoResponse>,
                response: Response<InstructorInfoResponse>
            ) {
                if (response.isSuccessful){
                    viewBinding.userName.text=response.body()?.fullName

                    Glide.with(viewBinding.profile)
                        .load(response.body()?.imagePath)
                        .placeholder(R.drawable.avatar_1)
                        .into(viewBinding.profile)
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
    private fun showCardFragment() {
        val cardFragment = DrCardDialogFragment()
        cardFragment.show(childFragmentManager,null)
    }
    private fun btnLogoutClickListener(){
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
    private fun showMessage(message:String
                            , posActionName:String?=null
                            , posAction: DialogInterface.OnClickListener?=null
                            , negActionName:String?=null
                            , negAction: DialogInterface.OnClickListener?=null

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
    private fun logout() {
        saveCredentials("","")
        navigateToLoginActivity()
    }
    private fun navigateToLoginActivity() {
        val intent = Intent(requireContext(), LoginActivity::class.java)
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
}