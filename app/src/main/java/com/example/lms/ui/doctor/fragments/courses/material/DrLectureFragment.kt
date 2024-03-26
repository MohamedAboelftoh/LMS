package com.example.lms.ui.doctor.fragments.courses.material

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import com.example.lms.R
import com.example.lms.databinding.FragmentDrLectureBinding
import com.example.lms.ui.api.api_doctor.dr_courses.material.DrFilesResponseItem
import com.example.lms.ui.api.api_doctor.dr_courses.material.DrLecturesResponseItem
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.student.fragments.Variables
import com.example.lms.ui.student.navigateFromFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DrLectureFragment : Fragment() {
    lateinit var viewBinding:FragmentDrLectureBinding
    lateinit var adapter: DrLecturesAdapter
    lateinit var myPreferencesToken: MyPreferencesToken
    private lateinit var fragmentContext: Context // Store the context here
    val addFolderFragment = AddFolderFragment()
    val drUpdateFolderFragment = DrUpdateFolderFragment()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentContext = context // Assign the context when the fragment is attached
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentDrLectureBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myPreferencesToken = MyPreferencesToken(requireContext())
        adapter = DrLecturesAdapter()
        viewBinding.floatingActionBtn.setOnClickListener {
            addFolderFragment.show(parentFragmentManager,"")
        }
        addFolderFragment.onFolderAddedListener = object :AddFolderFragment.OnFolderAddedListener{
            override fun onFolderAdded() {
                getLectures()
            }
        }
        viewBinding.lecRecycler.adapter = adapter
        getLectures()
        onLectureClick()
        onIconMoreClick()
    }
    private fun onIconMoreClick(){
        adapter.onIconMoreClickListener=object :DrLecturesAdapter.OnIconMoreClickListener{
            override fun onIconMoreClick(
                item: DrLecturesResponseItem,
                position: Int,
                holder: DrLecturesAdapter.DrLecturesViewHolder
            ) {
                val popupMenu = PopupMenu(requireContext(),holder.itemBinding.more)
                popupMenu.inflate(R.menu.folder_menu)
                val lectureId=item.lectureId
                popupMenu.setOnMenuItemClickListener { item: MenuItem? ->
                    when (item?.itemId) {
                        R.id.edit -> {
                            editFolder(lectureId)
                            true
                        }
                        R.id.delete_folder -> {
                            deleteFolder(lectureId)
                            true
                        }
                        else -> {
                            false
                        }
                    }
                }
                popupMenu.show()
            }
        }
    }

    private fun deleteFolder(lectureId: String?) {
        val token = myPreferencesToken.loadData("token")
     ApiManager.getApi().drDeleteFolder(token!!,lectureId!!)
         .enqueue(object : Callback<Void>{
             override fun onResponse(call: Call<Void>, response: Response<Void>) {
                 if(response.isSuccessful){
                     getLectures()
                 }
             }

             override fun onFailure(call: Call<Void>, t: Throwable) {
                 Toast.makeText(requireContext(), "onFailure " + t.localizedMessage, Toast.LENGTH_LONG).show()
             }

         })
    }

    private fun editFolder(lectureId: String?) {
      val drUpdateFolderFragment = DrUpdateFolderFragment()
        drUpdateFolderFragment.lectureId = lectureId
        drUpdateFolderFragment.show(parentFragmentManager,"")
        drUpdateFolderFragment.onFolderUpdatedListener = object : DrUpdateFolderFragment.OnFolderUpdatedListener{
            override fun onFolderUpdated() {
                getLectures()
            }

        }
    }

    private fun getLectures() {
        val cycleId = Variables.cycleId
        val token = myPreferencesToken.loadData("token")
        ApiManager.getApi().getDrCourseMaterial(token!!,cycleId!!).enqueue(object :Callback<ArrayList<DrLecturesResponseItem>>{
            override fun onResponse(
                call: Call<ArrayList<DrLecturesResponseItem>>,
                response: Response<ArrayList<DrLecturesResponseItem>>
            ) {
                if (response.isSuccessful) {
                    adapter.bindLectures(response.body())
                } else {
                    Toast.makeText(fragmentContext, "failed to get the lectures", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ArrayList<DrLecturesResponseItem>>, t: Throwable) {
                Toast.makeText(fragmentContext, "onFailure " + t.localizedMessage, Toast.LENGTH_LONG).show()

            }
        })
    }
    private fun onLectureClick(){
        adapter.onItemClickListener=
            DrLecturesAdapter.OnItemClickListener { position, item ->
                Variables.lecId = item.lectureId
                Variables.lectureName=item.lectureName
                navigateFromFragment(requireContext(),DrFilesActivity())
            }
    }

}