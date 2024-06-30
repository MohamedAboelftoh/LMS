package com.example.lms.ui.doctor.fragments.courses.material

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
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.Toast
import com.example.lms.R
import com.example.lms.databinding.ActivityDrFilesBinding
import com.example.lms.ui.api.api_doctor.dr_courses.material.DrFilesResponseItem
import com.example.lms.ui.api.api_student.material.fiels.FilesResponseItem
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.db.DataBase
import com.example.lms.ui.student.checkForInternet
import com.example.lms.ui.student.fragments.Variables
import com.example.lms.ui.student.navigateFromActivity
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class DrFilesActivity : AppCompatActivity(), DrUploadFileFragment.DrUploadFileListener {
    lateinit var viewBinding: ActivityDrFilesBinding
    lateinit var myPreferencesToken: MyPreferencesToken
    lateinit var adapter: DrFilesAdapter
    private val STORGE_PERMISSION_CODE : Int = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityDrFilesBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        myPreferencesToken= MyPreferencesToken(this )
        viewBinding.lectureName.text = Variables.lectureName
        adapter= DrFilesAdapter()
        viewBinding.filesRecycler.adapter=adapter
        if (checkForInternet(this@DrFilesActivity)){
            initializeData()
        }
        else{
            adapter.bindFiles(DataBase.getInstance(this).drFilesDao()
                .getFilesFromLocal().toMutableList())
        }
        viewBinding.icBack.setOnClickListener{
            finish()
        }
        adapter.onItemClickListener = object : DrFilesAdapter.OnItemClickListener {
            override fun onItemClick(item: DrFilesResponseItem, position: Int) {
                Variables.filePath = item.filePath
                Variables.fileName=item.fileName
                //navigateFromActivity(this@DrFilesActivity, DrPdfActivity())
                openPDFViewer(item.filePath)
            }
        }

        viewBinding.floatingActionBtn.setOnClickListener {
            val drUploadFileFragment = DrUploadFileFragment()
            drUploadFileFragment.listener = this  // Set the activity as the listener
            drUploadFileFragment.show(supportFragmentManager, "")
        }
        onIconMoreClick()
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
                   cashFilesInLocal(response.body())
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

    private fun onIconMoreClick(){
        adapter.onIconMoreClickListener=object :DrFilesAdapter.OnIconMoreClickListener{
            override fun onIconMoreClick(
                item: DrFilesResponseItem,
                position: Int,
                holder: DrFilesAdapter.FilesViewHolder
            ) {
                val popupMenu = PopupMenu(this@DrFilesActivity,holder.itemBinding.icMore)
                popupMenu.inflate(R.menu.drop_down_menu)
                val filePath=item.filePath
                val fileId=item.lectureFileId
                val fileName=item.fileName
                popupMenu.setOnMenuItemClickListener { item: MenuItem? ->
                    when (item?.itemId) {
                        R.id.download -> {
                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    == PackageManager.PERMISSION_DENIED){
                                    requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),STORGE_PERMISSION_CODE)
                                }
                                else{
                                    downloadData(filePath!!,fileName!!)
                                }
                            }
                            else{
                                downloadData(filePath!!,fileName!!)
                            }
                            true
                        }
                        R.id.delete -> {
                             deleteFile(fileId!!)
                            true
                        }
                        else -> false
                    }
                }

                popupMenu.show()
            }
            }
        }
    private fun deleteFile(fileId:Int){
        val token=myPreferencesToken.loadData("token")
        ApiManager.getApi().drDeleteFile(token!! ,fileId).enqueue(object :Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    initializeData()
                    Toast.makeText(this@DrFilesActivity,"file deleted successful", Toast.LENGTH_SHORT).show()

                }
                else{
                    Toast.makeText(this@DrFilesActivity,"error when file deleted", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@DrFilesActivity,"${t.localizedMessage}", Toast.LENGTH_LONG).show()
            }
        })

    }
    fun downloadData(url: String, fileName: String) {
        val request = DownloadManager.Request(Uri.parse(url))
        request.setTitle("PDF Download")
            .setDescription("Downloading the PDF File")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,fileName)
        val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        manager.enqueue(request)
//        val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
//        val request = DownloadManager.Request(Uri.parse(url)).apply {
//            setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
//            setTitle("Download")
//            setDescription("Downloading ...")
//            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
//            // Set MIME type for images
//            setMimeType("pdf/*")
//            setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
//        }
//        downloadManager.enqueue(request)
    }
    private fun cashFilesInLocal(body: MutableList<DrFilesResponseItem>?) {
        DataBase.getInstance(this).drFilesDao().deleteAllFiles()
        DataBase.getInstance(this).drFilesDao().insertFiles(body!!)

    }
}
