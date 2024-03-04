package com.example.lms.ui.home.fragments.courses_fragment.material

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.lms.R
import com.example.lms.databinding.ActivityMaterialFilesBinding

class MaterialFiles : AppCompatActivity() {
    lateinit var viewBinding:ActivityMaterialFilesBinding
     var fileList:MutableList<FileItem> = mutableListOf()
    lateinit var adapter:FilesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding= ActivityMaterialFilesBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.courseNameTv.text = Variables.courseName
        adapter= FilesAdapter(fileList)
        viewBinding.filesRecycler.adapter=adapter
        initializeData()
        viewBinding.icBack.setOnClickListener{
            finish()
        }
    }

    fun initializeData(){
        for (i in 1.. 10){
            fileList.add(FileItem("Lecture_1 programing c++",".pdf"))
            fileList.add(FileItem("Lecture_2 programing c++",".docx"))
        }
    }
}