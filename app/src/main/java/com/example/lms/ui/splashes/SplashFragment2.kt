package com.example.lms.ui.splashes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lms.R
import com.example.lms.databinding.FragmentSplash2Binding

class SplashFragment2 : Fragment() {
    lateinit var viewBinding : FragmentSplash2Binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentSplash2Binding.inflate(inflater,container,false)
        return viewBinding.root
    }

   /* override fun onResume() {
        super.onResume()
        viewBinding.navigation2.animate().apply {
            duration =1000
            rotationBy(360f)
        }.start()
    }
    */
}