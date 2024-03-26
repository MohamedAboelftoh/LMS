package com.example.lms.ui.doctor.fragments.courses.assignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lms.R
import com.example.lms.databinding.ActivityDrAssignPendingDetailsBinding
import com.example.lms.databinding.FragmentDrAssignmentPendingBinding

class DrAssignPendingDetailsActivity : AppCompatActivity() {
        lateinit var viewBinding:ActivityDrAssignPendingDetailsBinding
        val editAssignmentFragment=EditAssignmentFragment()
    val studentsNameFragment=StudentNamesFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding=ActivityDrAssignPendingDetailsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.btnEdit.setOnClickListener{
            editAssignmentFragment.show(supportFragmentManager,"")
        }
        viewBinding.number.setOnClickListener{
            studentsNameFragment.show(supportFragmentManager,"")
        }
    }
}