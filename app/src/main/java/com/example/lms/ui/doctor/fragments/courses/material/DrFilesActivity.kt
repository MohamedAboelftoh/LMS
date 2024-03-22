package com.example.lms.ui.doctor.fragments.courses.material

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.example.lms.databinding.ActivityDrFilesBinding
import com.example.lms.ui.api.api_doctor.dr_courses.material.DrFilesResponseItem
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.student.fragments.Variables
import com.example.lms.ui.student.fragments.calender.AddEventFragment
import com.example.lms.ui.student.navigateFromActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DrFilesActivity : AppCompatActivity(), DrUploadFileFragment.DrUploadFileListener {
    lateinit var viewBinding: ActivityDrFilesBinding
    lateinit var myPreferencesToken: MyPreferencesToken
    lateinit var adapter: DrFilesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityDrFilesBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        myPreferencesToken= MyPreferencesToken(this )
        viewBinding.courseNameTv.text = Variables.courseName
        adapter= DrFilesAdapter()
        viewBinding.filesRecycler.adapter=adapter
        initializeData()
        viewBinding.icBack.setOnClickListener{
            finish()
        }
        adapter.onItemClickListener = object : DrFilesAdapter.OnItemClickListener {
            override fun onItemClick(item: DrFilesResponseItem, position: Int) {
                Variables.filePath = item.filePath
                navigateFromActivity(this@DrFilesActivity, DrPdfActivity())
            }
        }


        viewBinding.floatingActionBtn.setOnClickListener {
            val drUploadFileFragment = DrUploadFileFragment()
            drUploadFileFragment.listener = this  // Set the activity as the listener
            drUploadFileFragment.show(supportFragmentManager, "")
        }
    }
    private fun initializeData(){
        val lectureId = Variables.lecId
        val token=myPreferencesToken.loadData("token")
        ApiManager.getApi().getDrFiles(token
            ,lectureId).enqueue(object : Callback<MutableList<DrFilesResponseItem>> {
            override fun onResponse(
                call: Call<MutableList<DrFilesResponseItem>>,
                response: Response<MutableList<DrFilesResponseItem>>
            ) {
                if (response.isSuccessful) {
                    adapter.bindFiles(response.body())
                }
                else{
                    Toast.makeText(this@DrFilesActivity,"response failed to get files", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<MutableList<DrFilesResponseItem>>, t: Throwable) {
                Toast.makeText(this@DrFilesActivity,"OnFailure"+t.localizedMessage, Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onFileUploaded() {
        initializeData()
    }
}