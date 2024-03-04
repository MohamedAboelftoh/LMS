package com.example.lms.ui.home.fragments.courses_fragment.material

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import com.example.lms.databinding.ActivityMaterialFilesBinding
import com.example.lms.ui.api.material.fiels.FielslResponseItem
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MaterialFiles : AppCompatActivity() {
    lateinit var viewBinding:ActivityMaterialFilesBinding
    lateinit var myPreferencesToken: MyPreferencesToken
    lateinit var adapter:FilesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myPreferencesToken= MyPreferencesToken(this )
        viewBinding= ActivityMaterialFilesBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.courseNameTv.text = Variables.courseName
        adapter= FilesAdapter()
        viewBinding.filesRecycler.adapter=adapter
        initializeData()
        viewBinding.icBack.setOnClickListener{
            finish()
        }
    }

    fun initializeData(){
        val lectureId=intent.getStringExtra("lectureId")
        val token=myPreferencesToken.loadData("token")
     ApiManager.getApi().getFiles(token
         ,lectureId).enqueue(object :Callback<MutableList<FielslResponseItem>>{
         override fun onResponse(
             call: Call<MutableList<FielslResponseItem>>,
             response: Response<MutableList<FielslResponseItem>>
         ) {
             if (response.isSuccessful) {
                 adapter.bindFiles(response.body())
                 Toast.makeText(this@MaterialFiles," files loaded successful", Toast.LENGTH_LONG).show()

             }
             else{
                 Toast.makeText(this@MaterialFiles,"response failed to get files", Toast.LENGTH_LONG).show()


             }
         }

         override fun onFailure(call: Call<MutableList<FielslResponseItem>>, t: Throwable) {
             Toast.makeText(this@MaterialFiles,"OnFailure"+t.localizedMessage, Toast.LENGTH_LONG).show()
         }

     })
    }
}