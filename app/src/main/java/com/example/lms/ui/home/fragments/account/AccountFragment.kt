package com.example.lms.ui.home.fragments.account

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lms.R
import com.example.lms.databinding.FragmentAccountBinding
import com.example.lms.ui.login.LoginActivity

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
        btnLogoutClickListener()
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
    private fun logout() {
        saveCredentials("","")
        navigateToLoginActivity()
    }
    private fun navigateToLoginActivity() {
        val intent = Intent(requireContext(),LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        activity?.finish()
    }

    private fun saveCredentials(email: String, password: String) {
        val sharedPreferences: SharedPreferences = requireContext().getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("email", email)
        editor.putString("password", password)
        editor.apply()
    }


    fun showMessage(message:String
                    ,posActionName:String?=null
                    ,posAction:DialogInterface.OnClickListener?=null
                    ,negActionName:String?=null
                    ,negAction: DialogInterface.OnClickListener?=null

    ):AlertDialog{
        val dialogBuilder=AlertDialog.Builder(requireContext())
        dialogBuilder.setMessage(message)
        if (posActionName!=null)
        {
            dialogBuilder.setPositiveButton(posActionName,posAction)
        }
        if(negActionName!=null)
        {
            dialogBuilder.setNegativeButton(negActionName,negAction)

        }
        return dialogBuilder.show()
    }
    fun btnLogoutClickListener(){
        viewBinding.logoutBtn.setOnClickListener {
            showMessage("Do you Sure To Logout "
                ,posActionName = "OK",
                posAction = { dialogInterface,i->
                    dialogInterface.dismiss()
                    logout()
                          },

                negActionName = "Cansel"
                , negAction = { dialogInterface, i ->
                    dialogInterface.dismiss()

                }
            )

        }
    }
}