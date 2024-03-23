package com.example.lms.ui.doctor.fragments.courses.assignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.lms.R
import com.example.lms.databinding.ActivityDrAddAssignmentBinding
import com.example.lms.ui.student.fragments.Variables
import com.example.lms.ui.student.navigateFromActivity

class DrAddAssignmentActivity : AppCompatActivity() {
    lateinit var viewBinding:ActivityDrAddAssignmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding=ActivityDrAddAssignmentBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.courseNameTv.text=Variables.courseName
        viewBinding.icBack.setOnClickListener{
            navigateFromActivity(this@DrAddAssignmentActivity,DrAssignActivity())
        }
    }
}