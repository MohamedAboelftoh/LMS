package com.example.lms.ui.student.fragments.courses_fragment.material

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.lms.databinding.ActivityFilePdfBinding
import com.example.lms.ui.student.fragments.Variables
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class FilePdfActivity : AppCompatActivity() {
    lateinit var viewBinding : ActivityFilePdfBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityFilePdfBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.icBack.setOnClickListener {
            finish()
        }
        viewBinding.courseNameTv.text = Variables.courseName
        loadPdf()
    }
    private fun downloadPdfFromUrl(pdfUrl: String) {
        val request = Request.Builder().url(pdfUrl).build()
        OkHttpClient().newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                viewBinding.progressBar.visibility = View.INVISIBLE
                e.printStackTrace()
                // Handle failure
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                viewBinding.progressBar.visibility = View.INVISIBLE
                val inputStream = response.body?.byteStream()
                val file = File(this@FilePdfActivity.cacheDir, "downloaded.pdf")
                val outputStream = FileOutputStream(file)
                inputStream?.copyTo(outputStream)
                inputStream?.close()
                outputStream.close()
                // Load the PDF on the main thread
                (this@FilePdfActivity as Activity).runOnUiThread {
                    loadPdfFromFile(file)
                }
            }
        })
    }

    private fun loadPdfFromFile(file: File) {
        viewBinding.pdfViewer.fromFile(file).load()
    }
    private fun loadPdf() {
        val pdfUrl = Variables.filePath
        downloadPdfFromUrl(pdfUrl!!)
    }
}