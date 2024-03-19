package com.example.lms.ui.student.fragments.drawer_grades

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lms.databinding.ActivityAllCoursesGradesBinding
import com.example.lms.ui.student.HomeActivity

class AllCoursesGrades : AppCompatActivity() {
    lateinit var viewBinding:ActivityAllCoursesGradesBinding
    lateinit var adapter: GradesAdapter
     var gradesList:MutableList<GradesItem> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding= ActivityAllCoursesGradesBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        adapter= GradesAdapter(gradesList)
        viewBinding.gradesRecycler.adapter=adapter
        initializeData()
        viewBinding.imgBack.setOnClickListener{
            navigateToHome()
        }
    }

    private fun initializeData() {
            gradesList.add(GradesItem("Parallel ","9 / 10"))
            gradesList.add(GradesItem("Programing ","8 / 10"))
            gradesList.add(GradesItem("System Analysis ","7 / 10"))
            gradesList.add(GradesItem("Data Structure ","8 / 10"))
            gradesList.add(GradesItem("Opp ","10 / 10"))
    }
    fun navigateToHome(){
        val intent= Intent(this@AllCoursesGrades,HomeActivity::class.java)
        startActivity(intent)
        finish()
    }


}