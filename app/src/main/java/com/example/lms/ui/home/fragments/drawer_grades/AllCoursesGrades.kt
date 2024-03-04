package com.example.lms.ui.home.fragments.drawer_grades

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.lms.R
import com.example.lms.databinding.ActivityAllCoursesGradesBinding
import com.example.lms.ui.home.HomeActivity
import com.example.lms.ui.home.fragments.courses_fragment.material.FileItem
import com.example.lms.ui.home.fragments.home_fragment.HomeFragment

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