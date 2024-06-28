package com.example.lms.ui.student.fragments.courses_fragment.material

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.lms.databinding.FragmentLabsBinding
import com.example.lms.ui.NotConnectedActivity
import com.example.lms.ui.api.api_student.material.CourseMaterialResponseItem
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.db.DataBase
import com.example.lms.ui.student.checkForInternet
import com.example.lms.ui.student.fragments.Variables
import com.example.lms.ui.student.navigateFromFragment
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LabsFragment : Fragment() {
    lateinit var adapter:LabsAdapter
    lateinit var viewBinding:FragmentLabsBinding
    lateinit var myPreferencesToken: MyPreferencesToken
    private lateinit var fragmentContext: Context // Store the context here

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentContext = context // Assign the context when the fragment is attached
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding=FragmentLabsBinding.inflate(layoutInflater)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myPreferencesToken= MyPreferencesToken(requireContext())
        adapter=LabsAdapter()
        viewBinding.labRecycler.adapter=adapter
        if(checkForInternet(fragmentContext)){
            getLabs()
        }
        else{
            adapter.bindLabs(DataBase.getInstance(fragmentContext).stuFoldersDao().getFoldersFromLocal())
        }
        onLabClick()

//        getLabs()
    }


    private fun getLabs() {
        val cycleId  = Variables.cycleId
        val token = myPreferencesToken.loadData("token")
        ApiManager.getApi().getCourseMaterial(token!!, cycleId!!)
            .enqueue(object : Callback<ArrayList<CourseMaterialResponseItem>> {
                override fun onResponse(
                    call: Call<ArrayList<CourseMaterialResponseItem>>,
                    response: Response<ArrayList<CourseMaterialResponseItem>>
                ) {
                    if (response.isSuccessful) {
                        cashFoldersInLocal(response.body())
                        adapter.bindLabs(response.body())
                    } else {
                        Toast.makeText(fragmentContext, "failed to get the lectures", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(
                    call: Call<ArrayList<CourseMaterialResponseItem>>,
                    t: Throwable
                ) {
                    Toast.makeText(fragmentContext, "onFailure " + t.localizedMessage, Toast.LENGTH_LONG).show()                }
            })
    }
    fun onLabClick(){
        adapter.onItemClickListener=
            LabsAdapter.OnItemClickListener { position, item ->
                if (checkForInternet(fragmentContext)) {
                    val intent = Intent(fragmentContext, MaterialFiles::class.java)
                    intent.putExtra("lectureId", item.lectureId)
                    startActivity(intent)
                }
                else{
                    navigateFromFragment(fragmentContext,NotConnectedActivity())
                }
            }
    }
    private fun cashFoldersInLocal(body: ArrayList<CourseMaterialResponseItem>?) {
        DataBase.getInstance(fragmentContext).stuFoldersDao().deleteAllFolders()
        DataBase.getInstance(fragmentContext).stuFoldersDao().insertFolders(body!!)
    }}