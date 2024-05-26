package com.example.lms.ui.student.fragments.courses_fragment.assignments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.lms.databinding.FragmentUploadBinding
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.student.fragments.Variables
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream

class UploadFragment : BottomSheetDialogFragment() {
    private lateinit var viewBinding: FragmentUploadBinding
    lateinit var myPreferencesToken: MyPreferencesToken
     var fileUrl:Uri?=null
    private val fileChooserLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                   val selectedFile = data?.data
                    fileUrl=selectedFile!!
                    val fileName = getFileName(selectedFile!!)
                    viewBinding.fileNameEt.setText(fileName ?: "").toString()

            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentUploadBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myPreferencesToken = MyPreferencesToken(requireContext())
        viewBinding.btnAttach.setOnClickListener { openFileChooser() }

        viewBinding.btnSubmit.setOnClickListener {
            try {
                if(fileUrl!=null) {
                    uploadFile(fileUrl!!)
                    navigateToAssignmentActivity()
                }
                else{
                    Toast.makeText(requireContext(), "Select File",Toast.LENGTH_LONG).show()

                }
            } catch (e: NullPointerException) {
                Toast.makeText(requireContext(), e.localizedMessage,Toast.LENGTH_LONG).show()
                // Handle the exception and display an error message
            }

        }
    }
private fun openFileChooser() {
    val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
        type = "*/*" // Set the correct MIME type to select any type of file
    }
    fileChooserLauncher.launch(Intent.createChooser(intent, "Select a file"))
}


    private fun getFileName(uri: Uri): String? {
        return context?.contentResolver?.query(uri, null, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
            } else {
                null
            }
        }
    }

    private fun navigateToAssignmentActivity() {
        startActivity(Intent(requireContext(), AssignmentsActivity::class.java))
    }
    private fun uploadFile(fileUri: Uri) {
        val token = myPreferencesToken.loadData("token")
        val taskId = Variables.taskId

        val filesDir = requireContext().filesDir
        val file = File(filesDir,"file.pdf")

        val inputStream = requireContext().contentResolver.openInputStream(fileUri)
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        val requestBody = file.asRequestBody("*/*".toMediaTypeOrNull())
        val part = MultipartBody.Part.createFormData("file",file.name,requestBody)

        ApiManager.getApi().uploadFile(taskId!! , part,token!!).enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Uploaded", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(requireContext(), "Not uploaded", Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(requireContext(), "Failed: ${t.message}", Toast.LENGTH_LONG).show()
            }

        })
    }



}