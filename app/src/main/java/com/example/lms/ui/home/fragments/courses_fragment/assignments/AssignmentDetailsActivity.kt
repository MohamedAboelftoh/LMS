package com.example.lms.ui.home.fragments.courses_fragment.assignments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lms.R
import com.example.lms.databinding.ActivityAssignmentDeatailsBinding

class AssignmentDetailsActivity : AppCompatActivity() {
    lateinit var viewBinding : ActivityAssignmentDeatailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityAssignmentDeatailsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.btnUpload.setOnClickListener {
            showUploadFragment()
        }
    }

    private fun showUploadFragment() {
        val uploadFragment = UploadFragment()
        uploadFragment.show(supportFragmentManager,null)
    }
}