package com.example.lms.ui.home.fragments.home_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lms.R
import com.example.lms.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var homeListItem: MutableList<ItemHomeNews> = mutableListOf()
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
        for(i in 0..5){
            homeListItem.add(
                ItemHomeNews("mohamed",R.drawable.img_user,"3:00","the quiz will start at 5:00 pm","Offline"
                    ,R.drawable.img_send_email))
        }
        homeAdapter = HomeRecyclerViewAdapter(homeListItem)
        viewBinding.homeRecyclerView.adapter = homeAdapter
    }
}