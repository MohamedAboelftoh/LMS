package com.example.lms.ui.doctor.fragments.courses.material

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.lms.databinding.ActivityDrPdfBinding
import com.example.lms.ui.student.fragments.Variables


class DrPdfActivity : AppCompatActivity() {
    lateinit var viewBinding : ActivityDrPdfBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityDrPdfBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.icBack.setOnClickListener {
            finish()
        }
        viewBinding.fileName.text = Variables.fileName
        loadPdf()
    }
//    private fun downloadPdfFromUrl(pdfUrl: String) {
//        val request = Request.Builder().url(pdfUrl).build()
//        OkHttpClient().newCall(request).enqueue(object : okhttp3.Callback {
//            override fun onFailure(call: okhttp3.Call, e: IOException) {
//                viewBinding.progressBar.visibility = View.INVISIBLE
//                e.printStackTrace()
//                // Handle failure
//            }
//
//            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
//                viewBinding.progressBar.visibility = View.INVISIBLE
//                val inputStream = response.body?.byteStream()
//                val file = File(this@DrPdfActivity.cacheDir, "downloaded.pdf")
//                val outputStream = FileOutputStream(file)
//                inputStream?.copyTo(outputStream)
//                inputStream?.close()
//                outputStream.close()
//                // Load the PDF on the main thread
//                (this@DrPdfActivity as Activity).runOnUiThread {
//                    loadPdfFromFile(file)
//                }
//            }
//        })
//    }
//    private fun loadPdfFromFile(file: File) {
//        viewBinding.pdfViewer.fromFile(file).load()
//    }
    private fun loadPdf() {
        val imgUrl = Variables.filePath
        val url = "https://docs.google.com/gview?embedded=true&url=${Variables.filePath}"
        val path = imgUrl?.substringAfterLast('/')
       val extension = path?.substringAfterLast('.', "")
    if(extension.equals("png") || extension.equals("jpg")){
        viewBinding.pdfViewer.visibility = View.GONE
        Glide.with(this)
            .load(imgUrl)
            .into(viewBinding.img)
    }
    viewBinding.pdfViewer.getSettings().setJavaScriptEnabled(true)
       viewBinding.pdfViewer.webViewClient = WebViewClient()
       viewBinding.pdfViewer.loadUrl(url)
       // downloadPdfFromUrl(pdfUrl!!)
    }
}