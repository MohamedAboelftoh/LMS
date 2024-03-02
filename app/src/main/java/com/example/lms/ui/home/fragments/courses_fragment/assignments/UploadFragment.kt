package com.example.lms.ui.home.fragments.courses_fragment.assignments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.lms.databinding.FragmentUploadBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class UploadFragment : BottomSheetDialogFragment() {
    private lateinit var viewBinding: FragmentUploadBinding
    private val fileChooserLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data: Intent? = result.data
                val selectedFile = data?.data
                selectedFile?.let { uri ->
                    val fileName = getFileName(uri)
                    viewBinding.fileNameEt.setText(fileName ?: "").toString()
                }
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
        viewBinding.btnSubmit.setOnClickListener { navigateToAssignmentActivity() }
        viewBinding.btnAttach.setOnClickListener { openFileChooser() }
    }

    private fun openFileChooser() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "*/*"
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
}
