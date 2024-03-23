package com.example.lms.ui.doctor.fragments.courses.material

import android.Manifest
import android.app.DownloadManager
import android.content.Context
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
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.student.fragments.Variables
import com.example.lms.ui.student.navigateFromActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        initializeData()
        viewBinding.icBack.setOnClickListener{
            finish()
        }
        adapter.onItemClickListener = object : DrFilesAdapter.OnItemClickListener {
            override fun onItemClick(item: DrFilesResponseItem, position: Int) {
                Variables.filePath = item.filePath
                Variables.fileName=item.fileName
                navigateFromActivity(this@DrFilesActivity, DrPdfActivity())
            }
        }

        viewBinding.floatingActionBtn.setOnClickListener {
            val drUploadFileFragment = DrUploadFileFragment()
            drUploadFileFragment.listener = this  // Set the activity as the listener
            drUploadFileFragment.show(supportFragmentManager, "")
        }
        onIconMoreClick()
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
                                    //Toast.makeText(this@DrFilesActivity,"$filePath",Toast.LENGTH_LONG).show()
                                    startDownloading(Uri.parse(filePath),fileName)
                                }
                            }
                            else{
                                startDownloading(Uri.parse(filePath),fileName)
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

    private fun startDownloading(uri: Uri?,fileName:String?) {
        val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val request = DownloadManager.Request(uri).apply {
            setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            setTitle("Download")
            setDescription("Downloading file...")
            setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            // Saves the downloaded file to this location. Adjust if necessary.
            setDestinationInExternalFilesDir(this@DrFilesActivity, Environment.DIRECTORY_DOWNLOADS, "$fileName")
        }
        downloadManager.enqueue(request)
    }
}
