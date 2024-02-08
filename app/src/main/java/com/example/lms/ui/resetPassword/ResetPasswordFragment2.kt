package com.example.lms.ui.resetPassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lms.R
import com.example.lms.databinding.FragmentResetPassword2Binding

class ResetPasswordFragment2 : Fragment() {
    private lateinit var viewBinding : FragmentResetPassword2Binding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = FragmentResetPassword2Binding.inflate(inflater,container,false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.btnNext.setOnClickListener {
            pushFragment(ResetPasswordFragment3())
        }
    }
    private fun pushFragment(fragment : Fragment){
        parentFragmentManager.beginTransaction()
            .replace(R.id.resetPassword_container,fragment)
            .commit()
    }
}