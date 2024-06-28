package com.example.lms.ui.student.fragments.courses_fragment.assignments

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import com.example.lms.databinding.ActivityAssignmentDeatailsBinding
import com.example.lms.ui.api.api_student.assignments.AssignmentByIdResponse
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.student.fragments.Variables
import com.example.lms.ui.student.navigateFromActivity
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class AssignmentDetailsActivity : AppCompatActivity() {
    lateinit var viewBinding : ActivityAssignmentDeatailsBinding
    lateinit var myPreferencesToken: MyPreferencesToken
    private val STORGE_PERMISSION_CODE : Int = 1000
    var url : String ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityAssignmentDeatailsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        myPreferencesToken = MyPreferencesToken(this)
        getData()
        viewBinding.btnUpload.setOnClickListener {
            showUploadFragment()
        }
        viewBinding.btnDownload.setOnClickListener {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED){
                    requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),STORGE_PERMISSION_CODE)
                }
                else{
                  startDownloading()
                }
            }
            else{
                startDownloading()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            STORGE_PERMISSION_CODE ->{
                if(grantResults.isNotEmpty() && grantResults[0]
                    == PackageManager.PERMISSION_GRANTED){
                    startDownloading()
                }
                else{
                    Toast.makeText(this,"permission denied",Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun startDownloading() {
        val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val request = DownloadManager.Request(Uri.parse(url)).apply {
            setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            setTitle("Download")
            setDescription("Downloading file...")
            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            // Saves the downloaded file to this location. Adjust if necessary.
            setDestinationInExternalFilesDir(this@AssignmentDetailsActivity, Environment.DIRECTORY_DOWNLOADS, "downloaded_file.pdf")
        }
        downloadManager.enqueue(request)
    }


    override fun onBackPressed() {
        super.onBackPressed()
        navigateFromActivity(this@AssignmentDetailsActivity, AssignmentsActivity())
    }

    private fun getData() {
        val token = myPreferencesToken.loadData("token")
        val taskId = Variables.taskId
        ApiManager.getApi().getAssignment(token!!,taskId!!).enqueue(object :Callback<AssignmentByIdResponse>{
            override fun onResponse(
                call: Call<AssignmentByIdResponse>,
                response: Response<AssignmentByIdResponse>
            ) {
                viewBinding.progressBar.visibility = View.INVISIBLE
                if(response.isSuccessful){
                  url = response.body()?.filePath
                    viewBinding.assignmentName.text = response.body()?.taskName
                    viewBinding.startDate.text = response.body()?.startDate
                    viewBinding.endDate.text = response.body()?.endDate
                    loadPdf(response.body()?.filePath!!)
                }
                else{
                    Toast.makeText(this@AssignmentDetailsActivity,"Failed",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AssignmentByIdResponse>, t: Throwable) {
                viewBinding.progressBar.visibility = View.INVISIBLE
                Toast.makeText(this@AssignmentDetailsActivity,t.localizedMessage,Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun showUploadFragment() {
        val uploadFragment = UploadFragment()
        uploadFragment.show(supportFragmentManager,null)
    }
    private fun downloadPdfFromUrl(pdfUrl: String) {
        val request = Request.Builder().url(pdfUrl).build()
        OkHttpClient().newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
               // viewBinding.progressBar.visibility = View.INVISIBLE
                e.printStackTrace()
                // Handle failure
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
              //  viewBinding.progressBar.visibility = View.INVISIBLE
                val inputStream = response.body?.byteStream()
                val file = File(this@AssignmentDetailsActivity.cacheDir, "downloaded.pdf")
                val outputStream = FileOutputStream(file)
                inputStream?.copyTo(outputStream)
                inputStream?.close()
                outputStream.close()
                // Load the PDF on the main thread
                (this@AssignmentDetailsActivity as Activity).runOnUiThread {
                    loadPdfFromFile(file)
                }
            }
        })
    }

    private fun loadPdfFromFile(file: File) {
        viewBinding.pdfViewer.fromFile(file).load()
    }
    private fun loadPdf(pdfUrl : String) {
        downloadPdfFromUrl(pdfUrl)
    }
}