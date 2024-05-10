package com.example.lms.ui.doctor.fragments.courses.quizes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.lms.R
import com.example.lms.databinding.ActivityDrQuestionsBinding

class DrQuestionsActivity : AppCompatActivity() {
    lateinit var viewBinding:ActivityDrQuestionsBinding
     lateinit var questionsAdapter:DrQuestionsAdapter
     private val snapHelper : SnapHelper = LinearSnapHelper()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding=ActivityDrQuestionsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
         questionsAdapter= DrQuestionsAdapter()
        viewBinding.drQuestionsRecycler.adapter=questionsAdapter
         snapHelper.attachToRecyclerView(viewBinding.drQuestionsRecycler)
    }
}