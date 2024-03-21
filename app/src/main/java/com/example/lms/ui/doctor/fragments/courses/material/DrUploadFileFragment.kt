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
import androidx.activity.result.contract.ActivityResultContracts
import com.example.lms.R
import com.example.lms.databinding.FragmentDrUploadFileBinding
import com.example.lms.ui.api.module.MyPreferencesToken
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DrUploadFileFragment : BottomSheetDialogFragment() {
    lateinit var viewBinding:FragmentDrUploadFileBinding
    lateinit var myPreferencesToken: MyPreferencesToken
    var fileUrl: Uri?=null
    private val fileChooserLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
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
      viewBinding=FragmentDrUploadFileBinding.inflate(inflater,container,false)
        return viewBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myPreferencesToken = MyPreferencesToken(requireContext())
        viewBinding.btnAttach.setOnClickListener { openFileChooser() }

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
    private fun openFileChooser() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "*/*" // Set the correct MIME type to select any type of file
        }
        fileChooserLauncher.launch(Intent.createChooser(intent, "Select a file"))
    }

}