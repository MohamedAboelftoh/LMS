package com.example.lms.ui.home.fragments.courses_fragment.assignments

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lms.databinding.ActivityAssignmentDeatailsBinding
import com.example.lms.ui.home.fragments.Variables
import com.example.lms.ui.home.fragments.courses_fragment.CourseContent
import com.example.lms.ui.home.navigateFromActivity

class AssignmentDetailsActivity : AppCompatActivity() {
    lateinit var viewBinding : ActivityAssignmentDeatailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityAssignmentDeatailsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        getData()
        viewBinding.btnUpload.setOnClickListener {
            showUploadFragment()
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        navigateFromActivity(this@AssignmentDetailsActivity, AssignmentsActivity())
    }

    private fun getData() {
        val assignmentName = intent.getStringExtra("taskName")
        val endDate = intent.getStringExtra("endDate")
        viewBinding.assignmentName.text = assignmentName
        viewBinding.deadline.text = endDate.toString()
    }
    private fun showUploadFragment() {
        val uploadFragment = UploadFragment()
        uploadFragment.show(supportFragmentManager,null)
    }
}