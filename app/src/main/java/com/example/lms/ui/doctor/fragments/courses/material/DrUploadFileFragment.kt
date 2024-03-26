package com.example.lms.ui.doctor.fragments.courses.material

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.lms.R
import com.example.lms.databinding.FragmentDrUploadFileBinding
import com.example.lms.ui.api.api_doctor.DrUploadFileResponse
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

class DrUploadFileFragment : BottomSheetDialogFragment() {
    lateinit var viewBinding:FragmentDrUploadFileBinding
    lateinit var myPreferencesToken: MyPreferencesToken
    private var fileUrl: Uri?=null
    private var fileName : String ?= null
    private val fileChooserLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val selectedFile = data?.data
                fileUrl=selectedFile!!
                fileName = getFileName(selectedFile)
                viewBinding.fileNameEt.setText(fileName ?: "").toString()
            }
        }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding=FragmentDrUploadFileBinding.inflate(inflater,container,false)
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
                }
                else{
                    Toast.makeText(requireContext(), "Select File", Toast.LENGTH_LONG).show()

                }
            } catch (e: NullPointerException) {
                Toast.makeText(requireContext(), e.localizedMessage, Toast.LENGTH_LONG).show()
                // Handle the exception and display an error message
            }

        }

    }
    private fun uploadFile(fileUri: Uri) {
        val token = myPreferencesToken.loadData("token")
        val lecId = Variables.lecId
        val filesDir = requireContext().filesDir

        // Use the original file name instead of hardcoding "file.pdf"
        val originalFileName = getFileName(fileUri)
        val file = File(filesDir, originalFileName ?: "file.pdf") // Fall back to "file.pdf" if name can't be determined

        val inputStream = requireContext().contentResolver.openInputStream(fileUri)
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        inputStream?.close() // It's important to close the streams to avoid memory leaks
        outputStream.close()

        val requestBody = file.asRequestBody("*/*".toMediaTypeOrNull())
        val part = MultipartBody.Part.createFormData("file", file.name, requestBody)

        ApiManager.getApi().drUploadFile(lecId!!, originalFileName!!, part, token!!).enqueue(object :
            Callback<DrUploadFileResponse> {
            override fun onResponse(call: Call<DrUploadFileResponse>, response: Response<DrUploadFileResponse>) {
                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "Uploaded", Toast.LENGTH_LONG).show()
                    listener?.onFileUploaded()  // Notify the Activity
                    dismiss()  // Dismiss the fragment
                } else {
                    Toast.makeText(requireContext(), "Not uploaded", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<DrUploadFileResponse>, t: Throwable) {
                Toast.makeText(requireContext(), "Failed: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    //    private fun uploadFile(fileUri: Uri) {
//        val token = myPreferencesToken.loadData("token")
//        val lecId = Variables.lecId
//        val filesDir = requireContext().filesDir
//        val file = File(filesDir,"file.pdf")
//
//        val inputStream = requireContext().contentResolver.openInputStream(fileUri)
//        val outputStream = FileOutputStream(file)
//        inputStream?.copyTo(outputStream)
//        val requestBody = file.asRequestBody("*/*".toMediaTypeOrNull())
//        val part = MultipartBody.Part.createFormData("file",file.name,requestBody)
//
//        ApiManager.getApi().drUploadFile(lecId!! ,fileName!!,part,token!!).enqueue(object :
//            Callback<DrUploadFileResponse> {
//            override fun onResponse(call: Call<DrUploadFileResponse>, response: Response<DrUploadFileResponse>) {
//                if (response.isSuccessful) {
//                    Toast.makeText(requireContext(), "Uploaded", Toast.LENGTH_LONG).show()
//                    listener?.onFileUploaded()  // Notify the Activity
//                    dismiss()  // Dismiss the fragment
//                } else {
//                    Toast.makeText(requireContext(), "Not uploaded", Toast.LENGTH_LONG).show()
//                }
//            }
//            override fun onFailure(call: Call<DrUploadFileResponse>, t: Throwable) {
//                Toast.makeText(requireContext(), "Failed: ${t.message}", Toast.LENGTH_LONG).show()
//            }
//        })
//    }
    private fun getFileName(uri: Uri): String? {
        return context?.contentResolver?.query(uri, null, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                cursor.getString(cursor.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
            } else {
                null
            }
        }
    }
    private fun openFileChooser() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "*/*" // Set the correct MIME type to select any type of file
        }
        fileChooserLauncher.launch(Intent.createChooser(intent, "Select a file"))
    }
    interface DrUploadFileListener {
        fun onFileUploaded()
    }
    var listener: DrUploadFileListener? = null
}