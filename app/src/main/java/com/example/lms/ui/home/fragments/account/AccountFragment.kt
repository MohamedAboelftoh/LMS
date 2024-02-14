package com.example.lms.ui.home.fragments.account

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lms.R
import com.example.lms.databinding.FragmentAccountBinding

class AccountFragment : Fragment() {
lateinit var viewBinding :FragmentAccountBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentAccountBinding.inflate(inflater,container,false)
        return viewBinding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.changePassword.setOnClickListener {
           navigateToChangePassword()
        }
        viewBinding.changePhone.setOnClickListener {
            navigateToChangePhone()
        }
        viewBinding.btnShowCard.setOnClickListener {
            showCardFragment()
        }
    }

    private fun showCardFragment() {
        val cardFragment = CardDialogFragment()
        cardFragment.show(childFragmentManager,null)
    }

    private fun navigateToChangePhone() {
        val intent = Intent(requireContext(),ChangePhoneActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToChangePassword() {
        val intent = Intent(requireContext(),ChangePasswordActivity::class.java)
        startActivity(intent)
    }
}