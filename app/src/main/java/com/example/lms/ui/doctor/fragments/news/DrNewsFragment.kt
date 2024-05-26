package com.example.lms.ui.doctor.fragments.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.lms.R
import com.example.lms.databinding.FragmentDrNewsBinding
import com.example.lms.ui.api.api_doctor.InstructorInfoResponse
import com.example.lms.ui.api.api_student.account.AccountInfoResponse
import com.example.lms.ui.api.api_student.news.NewsResponseItem
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.student.fragments.home_fragment.HomeRecyclerViewAdapter
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
    ): View? {
        viewBinding = FragmentDrNewsBinding.inflate(inflater,container,false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myPreferencesToken = MyPreferencesToken(requireContext())
        uploadNews()
        getCurrentUser()
        homeAdapter = HomeRecyclerViewAdapter()
        viewBinding.homeRecyclerView.adapter = homeAdapter
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
    private fun getCurrentUser() {
        val token=myPreferencesToken.loadData("token")
        ApiManager.getApi().getInstructorInfo(token!!).enqueue(object : Callback<InstructorInfoResponse>{
            override fun onResponse(
                call: Call<InstructorInfoResponse>,
                response: Response<InstructorInfoResponse>
            ) {
                if(response.isSuccessful){
                    viewBinding.tvName.text = response.body()?.fullName
//                    Glide.with(viewBinding.profile)
//                        .load(response.body()?.imagePath)
//                        .placeholder(R.drawable.avatar_1)
//                        .into(viewBinding.profile)
                }else{
                    val toast = Toast.makeText(requireContext(), "current user fail", Toast.LENGTH_LONG)
                    toast.show()
                }
            }

            override fun onFailure(call: Call<InstructorInfoResponse>, t: Throwable) {
                val toast = Toast.makeText(requireContext(), t.localizedMessage, Toast.LENGTH_LONG)
                toast.show()
            }

        })
    }
}