package com.example.lms.ui.home.fragments.courses_fragment.assignments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lms.databinding.ActivityAssignmentDeatailsBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

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

    private fun getData() {
        val assignmentName = intent.getStringExtra("taskName")
        val createAt = intent.getStringExtra("createdAt")
        viewBinding.assignmentName.text = assignmentName
        val parts = createAt?.split("T")
        val date = parts!![0]
        val time = parts[1]
        val formatter = SimpleDateFormat("HH:mm:ss.SS", Locale.US)
        val formattedTime = formatter.parse(time)
        val calendar = Calendar.getInstance()
        calendar.time = formattedTime
        val hour = calendar.get(Calendar.HOUR)
        val minute = calendar.get(Calendar.MINUTE)
        val amPm = if (calendar.get(Calendar.AM_PM) == Calendar.AM) "AM" else "PM"
        viewBinding.deadline.text = "$date at $hour:$minute $amPm"
    }
    private fun showUploadFragment() {
        val uploadFragment = UploadFragment()
        uploadFragment.show(supportFragmentManager,null)
    }
}