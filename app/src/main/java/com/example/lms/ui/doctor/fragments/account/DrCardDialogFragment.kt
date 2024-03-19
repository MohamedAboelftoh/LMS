package com.example.lms.ui.doctor.fragments.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.lms.R
import com.example.lms.databinding.FragmentDrCardDialogBinding
import com.example.lms.ui.api.module.MyPreferencesToken


class DrCardDialogFragment : DialogFragment() {
    lateinit var viewBinding:FragmentDrCardDialogBinding
    lateinit var myPreferencesToken: MyPreferencesToken

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding= FragmentDrCardDialogBinding.inflate(inflater,container,false)
        myPreferencesToken= MyPreferencesToken(requireContext())
        getDialog()?.getWindow()?.setBackgroundDrawableResource(R.drawable.backgound_dialog_fragment)
        return viewBinding.root
    }


}