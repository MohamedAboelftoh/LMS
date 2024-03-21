package com.example.lms.ui.doctor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.lms.R
import com.example.lms.databinding.ActivityDrFilesBinding
import com.example.lms.databinding.ActivityMaterialFilesBinding
import com.example.lms.ui.api.api_student.material.fiels.FielslResponseItem
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.student.fragments.Variables
import com.example.lms.ui.student.fragments.courses_fragment.material.FilePdfActivity
import com.example.lms.ui.student.fragments.courses_fragment.material.FilesAdapter
import com.example.lms.ui.student.navigateFromActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DrFilesActivity : AppCompatActivity() {
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
        adapter.onItemClickListener = object : DrFilesAdapter.OnItemClickListener{
            override fun onItemClick(item: DrFilesResponseItem, position: Int) {
                Variables.filePath = item.filePath
                navigateFromActivity(this@DrFilesActivity, DrPdfActivity())
            }
        }
    }
    private fun initializeData(){
        val lectureId= "L001"//intent.getStringExtra("lectureId")
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
}