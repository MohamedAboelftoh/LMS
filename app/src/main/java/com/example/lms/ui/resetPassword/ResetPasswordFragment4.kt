package com.example.lms.ui.resetPassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lms.R
import com.example.lms.databinding.FragmentResetPassword4Binding

class ResetPasswordFragment4 : Fragment() {
private lateinit var viewBinding : FragmentResetPassword4Binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentResetPassword4Binding.inflate(inflater,container,false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       viewBinding.btnSubmit.setOnClickListener {
           pushFragment(ResetPasswordFragment5())
       }
    }

    private fun pushFragment(fragment: Fragment) {
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.resetPassword_container,fragment)
            .commit()
    }
}