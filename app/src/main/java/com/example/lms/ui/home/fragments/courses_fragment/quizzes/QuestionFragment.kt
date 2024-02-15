package com.example.lms.ui.home.fragments.courses_fragment.quizzes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lms.R
import com.example.lms.databinding.FragmentQuestionBinding

class QuestionFragment : Fragment() {
lateinit var viewBinding: FragmentQuestionBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewBinding = FragmentQuestionBinding.inflate(inflater,container,false)
        return viewBinding.root
    }
    override fun onViewCreated(view: android.view.View, savedInstanceState: android.os.Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onCheckBoxClicked()
    }
    private fun onCheckBoxClicked(){
        viewBinding.checkBox1.setOnClickListener {
            viewBinding.checkBox1.isChecked = true
            viewBinding.checkBox2.isChecked = false
            viewBinding.checkBox3.isChecked = false
            viewBinding.checkBox4.isChecked = false
        }
        viewBinding.checkBox2.setOnClickListener {
            viewBinding.checkBox2.isChecked = true
            viewBinding.checkBox1.isChecked = false
            viewBinding.checkBox3.isChecked = false
            viewBinding.checkBox4.isChecked = false
        }
        viewBinding.checkBox3.setOnClickListener {
            viewBinding.checkBox3.isChecked = true
            viewBinding.checkBox2.isChecked = false
            viewBinding.checkBox1.isChecked = false
            viewBinding.checkBox4.isChecked = false
        }
        viewBinding.checkBox4.setOnClickListener {
            viewBinding.checkBox4.isChecked = true
            viewBinding.checkBox2.isChecked = false
            viewBinding.checkBox3.isChecked = false
            viewBinding.checkBox1.isChecked = false
        }
    }
}