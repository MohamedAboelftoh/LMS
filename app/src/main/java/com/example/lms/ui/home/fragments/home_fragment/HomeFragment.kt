package com.example.lms.ui.home.fragments.home_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.lms.databinding.FragmentHomeBinding
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.news.NewsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
   // private var newsList: List<NewsResponseItem?>?= null
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
        uploadNews()
        homeAdapter = HomeRecyclerViewAdapter()
        viewBinding.homeRecyclerView.adapter = homeAdapter
    }

    private fun uploadNews() {
        viewBinding.progressBar.visibility = View.VISIBLE
        ApiManager.getApi().getNews().enqueue(object : Callback<NewsResponse>{
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                viewBinding.progressBar.visibility = View.INVISIBLE
                if(response.isSuccessful){
                    //newsList = response.body()?.newsResponse
                    homeAdapter.bindNews(response.body()?.newsResponse)
                    val toast = Toast.makeText(requireContext(), "News success", Toast.LENGTH_LONG)
                    toast.show()
                }
                else{
                    val toast = Toast.makeText(requireContext(), "News Does not Uploaded", Toast.LENGTH_LONG)
                    toast.show()
                }
            }
            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                viewBinding.progressBar.visibility = View.INVISIBLE
                val toast = Toast.makeText(requireContext(),t.localizedMessage, Toast.LENGTH_LONG)
                toast.show()
            }
        })
    }
}