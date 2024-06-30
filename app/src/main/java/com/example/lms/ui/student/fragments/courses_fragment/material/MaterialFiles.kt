package com.example.lms.ui.student.fragments.courses_fragment.material

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Toast

import com.example.lms.databinding.ActivityMaterialFilesBinding
import com.example.lms.ui.api.api_student.material.fiels.FilesResponseItem
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.db.DataBase
import com.example.lms.ui.student.checkForInternet
import com.example.lms.ui.student.fragments.Variables
import com.example.lms.ui.student.navigateFromActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class MaterialFiles : AppCompatActivity() {
    lateinit var viewBinding:ActivityMaterialFilesBinding
    lateinit var myPreferencesToken: MyPreferencesToken
    lateinit var adapter:FilesAdapter
    var url : String ?= null
    private val STORGE_PERMISSION_CODE : Int = 1000


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myPreferencesToken= MyPreferencesToken(this )
        viewBinding= ActivityMaterialFilesBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        viewBinding.courseNameTv.text = Variables.courseName
        adapter= FilesAdapter()
        viewBinding.filesRecycler.adapter=adapter
        if (checkForInternet(this@MaterialFiles)){
            initializeData()
        }
        else{
            adapter.bindFiles(DataBase.getInstance(this).filesDao()
                .getFilesFromLocal().toMutableList())
        }
        viewBinding.icBack.setOnClickListener{
            finish()
        }
        adapter.onItemClickListener = object : FilesAdapter.OnItemClickListener{
            override fun onItemClick(item: FilesResponseItem, position: Int) {
                Variables.filePath = item.filePath
               openPDFViewer(item.filePath)
//                navigateFromActivity(this@MaterialFiles,FilePdfActivity())  //this line if you want to open the files in the same app
            }
        }
        onIconDownloadClick()
    }
    private fun onIconDownloadClick(){
        adapter.onIconDownloadClickListener=object : FilesAdapter.OnIconDownloadClickListener{
            override fun onIconDownloadClick(
                item:  FilesResponseItem,
                position: Int,
                holder: FilesAdapter.FilesViewHolder
            ) {
                url=item.filePath
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED){
                        requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),STORGE_PERMISSION_CODE)
                    }
                    else{
                        startDownloading()
                    }
                }
                else{
                    startDownloading()
                }
            }
        }
    }
    private fun startDownloading() {
        val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val request = DownloadManager.Request(Uri.parse(url)).apply {
            setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            setTitle("Download")
            setDescription("Downloading file...")
            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            // Saves the downloaded file to this location. Adjust if necessary.
            setDestinationInExternalFilesDir(this@MaterialFiles, Environment.DIRECTORY_DOWNLOADS, "downloaded_file.pdf")
        }
        downloadManager.enqueue(request)
    }

    private fun initializeData(){
        val lectureId=intent.getStringExtra("lectureId")
        val token=myPreferencesToken.loadData("token")
     ApiManager.getApi().getFiles(token
         ,lectureId).enqueue(object :Callback<MutableList<FilesResponseItem>>{
         override fun onResponse(
             call: Call<MutableList<FilesResponseItem>>,
             response: Response<MutableList<FilesResponseItem>>
         ) {
             if (response.isSuccessful) {
                 cashFilesInLocal(response.body())
                 adapter.bindFiles(response.body())
                 Toast.makeText(this@MaterialFiles," files loaded successful", Toast.LENGTH_LONG).show()
             }
             else{
                 Toast.makeText(this@MaterialFiles,"response failed to get files", Toast.LENGTH_LONG).show()


             }
         }

         override fun onFailure(call: Call<MutableList<FilesResponseItem>>, t: Throwable) {
             Toast.makeText(this@MaterialFiles,"OnFailure"+t.localizedMessage, Toast.LENGTH_LONG).show()
         }

     })
    }
    fun openPDFViewer(url: String?) {
        try {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(Uri.parse(url), "application/*")
            intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            val intentChooser = Intent.createChooser(intent, "Open PDF")
            startActivity(intentChooser)
        } catch (e: Exception) {
            // Error...1-basics_if cond task
        }
    }
    private fun cashFilesInLocal(body: MutableList<FilesResponseItem>?) {
        DataBase.getInstance(this).filesDao().deleteAllFiles()
        DataBase.getInstance(this).filesDao().insertFiles(body!!)

    }


}