package com.example.lms.ui.doctor.fragments.courses.quizes

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.lms.R
import com.example.lms.databinding.ActivityDrAddQuizBinding
import com.example.lms.ui.student.fragments.Variables
import com.example.lms.ui.student.navigateFromActivity
import java.util.Calendar

class DrAddQuizActivity : AppCompatActivity() {
    lateinit var viewBinding : ActivityDrAddQuizBinding
    private var position = 0
    lateinit var  startDate : String
    lateinit var endDate : String
    private var editableFormattedDateTime: Editable? =null
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityDrAddQuizBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.courseNameTv.text = Variables.courseName
        viewBinding.btnStartDate.setOnClickListener {
            showDateBicker(viewBinding.tvStartDate)
            startDate = viewBinding.tvStartDate.text.toString()
        }
        viewBinding.btnEndDate.setOnClickListener {
            showDateBicker(viewBinding.tvEndDate)
            endDate = viewBinding.tvEndDate.text.toString()
        }
        changeStep()
    }
    @RequiresApi(Build.VERSION_CODES.N)
    private fun showDateBicker(date : EditText) {
        val dateBicker =  DatePickerDialog(this)
        dateBicker?.setOnDateSetListener { datePicker, year, month, day ->
            val calendar = Calendar.getInstance()
            val timePickerDialog = TimePickerDialog(
                this@DrAddQuizActivity,
                { view, hourOfDay, minute ->
                    calendar.set(year, month, day, hourOfDay, minute)
                    val formattedDateTime = String.format(
                        "%d-%02d-%02dT%02d:%02d:%02d",
                        year,
                        month + 1,
                        day,
                        hourOfDay,
                        minute,
                        0
                    )

                    editableFormattedDateTime =
                        Editable.Factory.getInstance().newEditable(formattedDateTime)
                    date.text = editableFormattedDateTime
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                false
            )
            timePickerDialog.show()
        }
        dateBicker?.show()
    }
    private fun changeStep() {
        viewBinding.stepView.done(false)
        viewBinding.next.setOnClickListener {
            when (position) {
                0 -> {
                    viewBinding.constrainNameOfQuiz.visibility = View.GONE
                    viewBinding.question.visibility = View.GONE
                    viewBinding.constrainPoints.visibility = View.VISIBLE
                    position = 1
                    viewBinding.stepView.done(false)
                    viewBinding.stepView.go(position, true)
                    viewBinding.back.visibility = View.VISIBLE

                }
                1 -> {
                    viewBinding.constrainNameOfQuiz.visibility = View.GONE
                    viewBinding.constrainPoints.visibility = View.GONE
                    viewBinding.question.visibility = View.VISIBLE
//                    viewBinding.next.text = "Submit"
                    viewBinding.quizName.text = viewBinding.quizNameEt.text
                    viewBinding.courseName.text = Variables.courseName
                    viewBinding.points.text = viewBinding.etQuizPoints.text
                    position = 2
                    viewBinding.stepView.done(false)
                    viewBinding.stepView.go(position, true)
                }
                else -> {

                    // Go to another Activity or Fragment
                    navigateFromActivity(this@DrAddQuizActivity,DrQuestionsActivity())
                }
            }
        }
        viewBinding.back.setOnClickListener {
            when (position) {
                0 -> {
                    finish()
                }
                1 -> {
                    viewBinding.constrainPoints.visibility = View.GONE
                    viewBinding.question.visibility = View.GONE
                    viewBinding.constrainNameOfQuiz.visibility = View.VISIBLE
                    viewBinding.back.visibility = View.GONE
                    position = 0
                    viewBinding.stepView.done(false)
                    viewBinding.stepView.go(position, true)
                }
                2 -> {
                    viewBinding.question.visibility = View.GONE
                    viewBinding.constrainNameOfQuiz.visibility = View.GONE
                    viewBinding.constrainPoints.visibility = View.VISIBLE
//                    viewBinding.next.text = "Next"
                    position = 1
                    viewBinding.stepView.done(false)
                    viewBinding.stepView.go(position, true)
                }
            }
        }
    }
}