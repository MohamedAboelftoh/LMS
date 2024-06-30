package com.example.lms.ui.doctor.fragments.news

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.lms.R
import com.example.lms.databinding.FragmentDrNewsBinding
import com.example.lms.ui.api.api_doctor.InstructorInfoResponse
import com.example.lms.ui.api.api_student.account.AccountInfoResponse
import com.example.lms.ui.api.api_student.news.NewsResponseItem
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.db.DataBase
import com.example.lms.ui.student.checkForInternet
import com.example.lms.ui.student.fragments.home_fragment.HomeRecyclerViewAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DrNewsFragment : Fragment() {
    private lateinit var myPreferencesToken : MyPreferencesToken
    private lateinit var homeAdapter : HomeRecyclerViewAdapter
    lateinit var viewBinding : FragmentDrNewsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentDrNewsBinding.inflate(inflater,container,false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myPreferencesToken = MyPreferencesToken(requireContext())
        homeAdapter = HomeRecyclerViewAdapter()
        viewBinding.homeRecyclerView.adapter = homeAdapter
        if(checkForInternet(requireContext())){
            uploadNews()
            getCurrentUser()
        }else{
            viewBinding.progressBar.visibility = View.GONE
            Snackbar.make(viewBinding.root , "Not Connected" , Snackbar.LENGTH_LONG).show()
            uploadNewsFromLocal()
            getCurrentUserFromLocal()
        }
    }

    private fun getCurrentUserFromLocal() {
        viewBinding.tvName.text = DataBase.getInstance(requireContext()).instructorDao().getInstructorFromLocal().fullName?:"User"
    }

    private fun uploadNewsFromLocal() {
        homeAdapter.bindNews(DataBase.getInstance(requireContext()).newsDao().getNewsFromLocal())
        val imgPath= DataBase.getInstance(requireContext()).instructorDao().getInstructorFromLocal().imagePath

        Glide.with(requireContext())
            .load(imgPath ?: R.drawable.avatar_1)  // Use placeholder if imagePath is null
            .placeholder(R.drawable.avatar_1)
            .into(viewBinding.profile)
    }

    private fun uploadNews() {
        viewBinding.progressBar.visibility = View.VISIBLE
        ApiManager.getApi().getNews().enqueue(object : Callback<ArrayList<NewsResponseItem>> {
            override fun onResponse(
                call: Call<ArrayList<NewsResponseItem>>,
                response: Response<ArrayList<NewsResponseItem>>
            ) {
                viewBinding.progressBar.visibility = View.INVISIBLE
                if(response.isSuccessful) {
                    cacheNewsInLocal(response.body())
                    homeAdapter.bindNews(response.body())
                }
                else{
                    val toast = Toast.makeText(requireContext(), "News Does not Uploaded", Toast.LENGTH_LONG)
                    toast.show()
                }
            }

            override fun onFailure(call: Call<ArrayList<NewsResponseItem>>, t: Throwable) {
                viewBinding.progressBar.visibility = View.INVISIBLE
                val toast = Toast.makeText(requireContext(),"onFailure : news", Toast.LENGTH_LONG)
                toast.show()
            }
        })
    }

    private fun cacheNewsInLocal(body: java.util.ArrayList<NewsResponseItem>?) {
         DataBase.getInstance(requireContext()).newsDao().deleteAllNews()
        DataBase.getInstance(requireContext()).newsDao().insertNews(body!!)
    }

    private fun getCurrentUser() {
        val token=myPreferencesToken.loadData("token")
        ApiManager.getApi().getInstructorInfo(token!!).enqueue(object : Callback<InstructorInfoResponse>{
            override fun onResponse(
                call: Call<InstructorInfoResponse>,
                response: Response<InstructorInfoResponse>
            ) {
                if(response.isSuccessful){
                    insertInstructorInLocal(response.body())
                    viewBinding.tvName.text = response.body()?.fullName
                    Glide.with(requireContext())
                        .load(response?.body()?.imagePath ?: R.drawable.avatar_1)  // Use placeholder if imagePath is null
                        .placeholder(R.drawable.avatar_1)
                        .into(viewBinding.profile)

                }else{
                    val toast = Toast.makeText(requireContext(), "current user fail", Toast.LENGTH_LONG)
                    toast.show()
                }
            }

            override fun onFailure(call: Call<InstructorInfoResponse>, t: Throwable) {
                val toast = Toast.makeText(requireContext(),"onFailure : user", Toast.LENGTH_LONG)
                toast.show()
            }

        }) }

    private fun insertInstructorInLocal(body: InstructorInfoResponse?) {
        DataBase.getInstance(requireContext()).instructorDao().deleteAllInstructors()
        DataBase.getInstance(requireContext()).instructorDao().insertInstructor(body!!)
    }
}