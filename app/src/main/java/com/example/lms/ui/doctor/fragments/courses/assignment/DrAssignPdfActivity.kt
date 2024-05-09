package com.example.lms.ui.doctor.fragments.courses.assignment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import com.bumptech.glide.Glide
import com.example.lms.databinding.ActivityDrPdf2Binding
import com.example.lms.databinding.ActivityDrPdfBinding
import com.example.lms.ui.student.fragments.Variables

class DrAssignPdfActivity : AppCompatActivity() {
    lateinit var viewBinding:ActivityDrPdf2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding=ActivityDrPdf2Binding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.icBack.setOnClickListener {
            finish()
        }
        viewBinding.fileName.text = Variables.taskName
        loadPdf()
    }
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