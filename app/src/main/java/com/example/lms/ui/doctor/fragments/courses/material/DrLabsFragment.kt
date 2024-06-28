package com.example.lms.ui.doctor.fragments.courses.material

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.lms.databinding.FragmentDrLabsBinding
import com.example.lms.ui.api.api_doctor.dr_courses.material.DrLecturesResponseItem
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.db.DataBase
import com.example.lms.ui.student.checkForInternet
import com.example.lms.ui.student.fragments.Variables
import com.example.lms.ui.student.navigateFromFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DrLabsFragment : Fragment() {
   lateinit var viewBinding:FragmentDrLabsBinding
    lateinit var adapter: DrLabsAdapter
    lateinit var myPreferencesToken: MyPreferencesToken
    private lateinit var fragmentContext: Context // Store the context here
    val addFolderFragment = AddFolderFragment()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentContext = context // Assign the context when the fragment is attached
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding=FragmentDrLabsBinding.inflate(inflater,container,false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myPreferencesToken = MyPreferencesToken(requireContext())
        adapter = DrLabsAdapter()
        viewBinding.labRecycler.adapter = adapter
        if(checkForInternet(fragmentContext)){
            getLabs()
        }else{
            adapter.bindLabs(DataBase.getInstance(fragmentContext).foldersDao().getFoldersFromLocal())
        }
        viewBinding.floatingActionBtn.setOnClickListener {
            addFolderFragment.show(parentFragmentManager,"")
        }
        addFolderFragment.onFolderAddedListener = object :AddFolderFragment.OnFolderAddedListener{
            override fun onFolderAdded() {
                getLabs()
            }
        }
        onLabClick()
    }
    private fun getLabs() {
        val cycleId = Variables.cycleId
        val token = myPreferencesToken.loadData("token")
        ApiManager.getApi().getDrCourseMaterial(token!!,cycleId!!).enqueue(object :
            Callback<ArrayList<DrLecturesResponseItem>> {
            override fun onResponse(
                call: Call<ArrayList<DrLecturesResponseItem>>,
                response: Response<ArrayList<DrLecturesResponseItem>>
            ) {
                if (response.isSuccessful) {
                    cacheLabsInLocal(response.body())
                    adapter.bindLabs(response.body())
                } else {
                    Toast.makeText(fragmentContext, "failed to get the lectures", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ArrayList<DrLecturesResponseItem>>, t: Throwable) {
                Toast.makeText(fragmentContext, "onFailure " + t.localizedMessage, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun cacheLabsInLocal(body: java.util.ArrayList<DrLecturesResponseItem>?) {
        DataBase.getInstance(fragmentContext).foldersDao().deleteAllFolders()
        DataBase.getInstance(fragmentContext).foldersDao().insertFolders(body!!)
    }

    private fun onLabClick(){
        adapter.onItemClickListener=
            DrLabsAdapter.OnItemClickListener { position, item ->
                Variables.lecId = item.lectureId
                Variables.lectureName=item.lectureName
                navigateFromFragment(requireContext(),DrFilesActivity())
            }
    }
}