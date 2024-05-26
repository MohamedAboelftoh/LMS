package com.example.lms.ui.doctor.fragments.courses.assignment

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.OpenableColumns
import android.text.Editable
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import com.example.lms.databinding.ActivityDrAddAssignmentBinding
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.student.fragments.Variables
import com.example.lms.ui.student.navigateFromActivity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.util.Calendar

class DrAddAssignmentActivity : AppCompatActivity() {
    lateinit var viewBinding: ActivityDrAddAssignmentBinding
    private var position = 0
    lateinit var  startDate : String
    lateinit var endDate : String
    private var editableFormattedDateTime: Editable? =null
    private var fileUrl: Uri?=null
    private var fileName : String ?= null
    lateinit var myPreferencesToken: MyPreferencesToken
    private val fileChooserLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val selectedFile = data?.data
                fileUrl=selectedFile!!
                fileName = getFileName(selectedFile)
                viewBinding.attach.setText(fileName ?: "").toString()
                viewBinding.add.setImageURI(selectedFile)
            }
        }
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myPreferencesToken = MyPreferencesToken(this)
        viewBinding=ActivityDrAddAssignmentBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        clicksListener()
        changeStep()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun clicksListener() {
        viewBinding.attach.setOnClickListener {
            openFileChooser()
        }
        viewBinding.btnStartDate.setOnClickListener {
            showDateBicker(viewBinding.tvStartDate)
            startDate = viewBinding.tvStartDate.text.toString()
        }
        viewBinding.btnEndDate.setOnClickListener {
            showDateBicker(viewBinding.tvEndDate)
            endDate = viewBinding.tvEndDate.text.toString()
        }
//        viewBinding.next.setOnClickListener {
//            if(viewBinding.next.text.equals("Submit")){
//                if (!validation()){
//                    return@setOnClickListener
//                }
//                navigateFromActivity(this,DrAssignActivity())
//            }
//        }
    }

    private fun changeStep() {
        viewBinding.stepView.done(false)
        viewBinding.next.setOnClickListener {
            when (position) {
                0 -> {
                    viewBinding.constrainTitle.visibility = View.GONE
                    viewBinding.file.visibility = View.GONE
                    viewBinding.constrainPoints.visibility = View.VISIBLE
                    position = 1
                    viewBinding.stepView.done(false)
                    viewBinding.stepView.go(position, true)
                    viewBinding.back.visibility = View.VISIBLE

                }
                1 -> {
                    viewBinding.constrainTitle.visibility = View.GONE
                    viewBinding.constrainPoints.visibility = View.GONE
                    viewBinding.file.visibility = View.VISIBLE
                    viewBinding.next.text = "Submit"
                    position = 2
                    viewBinding.stepView.done(false)
                    viewBinding.stepView.go(position, true)
                }
                else -> {
                    try {
                        if(fileUrl!=null) {
                            if (!validation()){
                                return@setOnClickListener
                              }
                            uploadFile(fileUrl!!)
                        }
                        else{
                            Toast.makeText(this, "Select File", Toast.LENGTH_LONG).show()
                        }
                    } catch (e: NullPointerException) {
                        Toast.makeText(this, e.localizedMessage, Toast.LENGTH_LONG).show()
                        // Handle the exception and display an error message
                    }
                    // Go to another Activity or Fragment
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
                    viewBinding.file.visibility = View.GONE
                    viewBinding.constrainTitle.visibility = View.VISIBLE
                    viewBinding.back.visibility = View.GONE
                    position = 0
                    viewBinding.stepView.done(false)
                    viewBinding.stepView.go(position, true)
                }
                2 -> {
                    viewBinding.file.visibility = View.GONE
                    viewBinding.constrainTitle.visibility = View.GONE
                    viewBinding.constrainPoints.visibility = View.VISIBLE
                    viewBinding.next.text = "Next"
                    position = 1
                    viewBinding.stepView.done(false)
                    viewBinding.stepView.go(position, true)
                }
            }
        }
    }

    private fun uploadFile(fileUrl: Uri) {
        val taskName = viewBinding.taskName.text.toString()
        val taskGrade = viewBinding.etTaskGrade.text.toString()
        val cycleId = Variables.cycleId
        val token = myPreferencesToken.loadData("token")
        val filesDir = this.filesDir

        // Use the original file name instead of hardcoding "file.pdf"
        val originalFileName = getFileName(fileUrl)
        val file = File(filesDir, originalFileName ?: "file.pdf") // Fall back to "file.pdf" if name can't be determined

        val inputStream = this.contentResolver.openInputStream(fileUrl)
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        inputStream?.close() // It's important to close the streams to avoid memory leaks
        outputStream.close()

        val requestBody = file.asRequestBody("*/*".toMediaTypeOrNull())
        val part = MultipartBody.Part.createFormData("file", file.name, requestBody)
        ApiManager.getApi().drUploadAssignment(taskName ,taskGrade,startDate,endDate ,cycleId!!,part,token!!)
            .enqueue(object : Callback<ResponseBody>{
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful){
                        onAssignmentAddedListener?.onAssignmentAdded()
                        Toast.makeText(this@DrAddAssignmentActivity,"added",Toast.LENGTH_LONG).show()
                        navigateFromActivity(this@DrAddAssignmentActivity,DrAssignActivity())
                    }
                    else{
                        Toast.makeText(this@DrAddAssignmentActivity,"error",Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(this@DrAddAssignmentActivity,t.localizedMessage,Toast.LENGTH_LONG).show()
                }

            })

    }

    private fun getFileName(uri: Uri): String? {
        return this?.contentResolver?.query(uri, null, null, null, null)?.use { cursor ->
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
    @RequiresApi(Build.VERSION_CODES.N)
    private fun showDateBicker(date : TextView) {
        val dateBicker =  DatePickerDialog(this)
        dateBicker?.setOnDateSetListener { datePicker, year, month, day ->
            val calendar = Calendar.getInstance()
            val timePickerDialog = TimePickerDialog(
                this@DrAddAssignmentActivity,
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
     var onAssignmentAddedListener: OnAssignmentAddedListener ?= null
    interface OnAssignmentAddedListener{
        fun onAssignmentAdded(
        )
    }
    private fun validation() : Boolean{
        var isValid = true
        if(viewBinding.taskName.text.isNullOrBlank()){
            viewBinding.taskName.error = "Required"
            isValid = false
        }
        else{
            viewBinding.taskName.error = null
        }
        if (viewBinding.etTaskGrade.text.isNullOrBlank()){
            viewBinding.taskPoint.error = "Required"
            isValid = false
        }
        else{
            viewBinding.taskPoint.error = null
        }
        if (viewBinding.tvStartDate.text.isNullOrBlank()){
            viewBinding.tvStartDate.error = "Required"
            isValid = false
        }
        else{
            viewBinding.tvStartDate.error = null
        }
        if (viewBinding.tvEndDate.text.isNullOrBlank()){
            viewBinding.tvEndDate.error = "Required"
            isValid = false
        }
        else{
            viewBinding.tvEndDate.error = null
        }
    return isValid
    }
}