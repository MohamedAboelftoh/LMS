package com.example.lms.ui.student.fragments.home_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.lms.R
import com.example.lms.databinding.FragmentHomeBinding
import com.example.lms.ui.api.api_student.account.AccountInfoResponse
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.api.api_student.news.NewsResponseItem
import com.example.lms.ui.db.DataBase
import com.example.lms.ui.student.checkForInternet
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
   // private var newsList: List<NewsResponseItem?>?= null
   private lateinit var myPreferencesToken : MyPreferencesToken
    private lateinit var homeAdapter : HomeRecyclerViewAdapter
    lateinit var viewBinding:FragmentHomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentHomeBinding.inflate(inflater,container,false)
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
        }
        else{
            viewBinding.progressBar.visibility = View.GONE
            Snackbar.make(viewBinding.root , "Not Connected" , Snackbar.LENGTH_SHORT).show()
            uploadNewsFromLocal()
            getCurrentUserFromLocal()

        }
       // uploadNews()
        //getCurrentUser()
    }
    private fun getCurrentUserFromLocal() {
        viewBinding.tvName.text = DataBase.getInstance(requireContext()).studentInfoDao().getStuInfoFromLocal().fullName
    }

    private fun uploadNews() {
        viewBinding.progressBar.visibility = View.VISIBLE
        ApiManager.getApi().getNews().enqueue(object : Callback<ArrayList<NewsResponseItem>>{
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
                val toast = Toast.makeText(requireContext(),t.localizedMessage, Toast.LENGTH_LONG)
                toast.show()
            }
        })
    }
    private fun uploadNewsFromLocal() {
        homeAdapter.bindNews(DataBase.getInstance(requireContext()).newsDao().getNewsFromLocal())
    }

    private fun getCurrentUser() {
        viewBinding.progressBar.visibility = View.VISIBLE
        val token=myPreferencesToken.loadData("token")
        ApiManager.getApi().getAccountInfo(token!!).enqueue(object : Callback<AccountInfoResponse>{
            override fun onResponse(
                call: Call<AccountInfoResponse>,
                response: Response<AccountInfoResponse>
            ) {
                if(response.isSuccessful){
                    insertStudentInLocal(response.body())
                    viewBinding.progressBar.visibility = View.INVISIBLE
                   viewBinding.tvName.text = response.body()?.fullName
                    Glide.with(viewBinding.profile)
                        .load(response.body()?.imagePath)
                        .placeholder(R.drawable.avatar_1)
                        .into(viewBinding.profile)
                }else{
                    val toast = Toast.makeText(requireContext(), "current user fail", Toast.LENGTH_LONG)
                    toast.show()
                }
            }

            override fun onFailure(call: Call<AccountInfoResponse>, t: Throwable) {
                viewBinding.progressBar.visibility = View.INVISIBLE
                val toast = Toast.makeText(requireContext(), t.localizedMessage, Toast.LENGTH_LONG)
                toast.show()
            }

        })
    }
    private fun cacheNewsInLocal(body: java.util.ArrayList<NewsResponseItem>?) {
        DataBase.getInstance(requireContext()).newsDao().deleteAllNews()
        DataBase.getInstance(requireContext()).newsDao().insertNews(body!!)
    }
    private fun insertStudentInLocal(body: AccountInfoResponse?) {
        DataBase.getInstance(requireContext()).studentInfoDao().deleteStuFromLocal()
        DataBase.getInstance(requireContext()).studentInfoDao().insertStudentInfo(body!!)
    }}