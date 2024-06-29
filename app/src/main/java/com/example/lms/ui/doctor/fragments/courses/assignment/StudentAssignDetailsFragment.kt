package com.example.lms.ui.doctor.fragments.courses.assignment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.lms.R
import com.example.lms.databinding.FragmentStudentAssignDetailsBinding
import com.example.lms.ui.api.api_doctor.dr_courses.assignments.EditAssignmentGradeRequest
import com.example.lms.ui.api.module.ApiManager
import com.example.lms.ui.api.module.MyPreferencesToken
import com.example.lms.ui.student.fragments.Variables
import com.example.lms.ui.student.navigateFromFragment
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StudentAssignDetailsFragment : DialogFragment() {
    lateinit var viewBinding : FragmentStudentAssignDetailsBinding
    lateinit var myPreferencesToken: MyPreferencesToken
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.AppTheme_Dialog_Custom)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding=FragmentStudentAssignDetailsBinding.inflate(layoutInflater)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.backgound_dialog_fragment)
        return viewBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myPreferencesToken=MyPreferencesToken(requireContext())
        viewBinding.tvStudentName.text=Variables.studentName
        viewBinding.timeSentTv.text=Variables.stuTimeUploaded
        viewBinding.openFIleEt.setOnClickListener{
            navigateFromFragment(requireContext(),DrAssignPdfActivity())
        }
        butSubmitClick()

    }

    private fun butSubmitClick() {
        viewBinding.btnSubmitGrade .setOnClickListener{
            if (validation()){
                confirmChanges()
            }
        }
    }

    private fun validation(): Boolean {
        if (viewBinding.gradeEt.text.isNullOrBlank()){
            viewBinding.gradeEt.error="enter grade"
            return false
        }
            return true
    }

    private fun confirmChanges() {
        val token=myPreferencesToken.loadData("token")

        val model=EditAssignmentGradeRequest(studentId = Variables.studentId,
            grade = viewBinding.gradeEt.text.toString().toIntOrNull(),taskId = Variables.taskId)
            ApiManager.getApi().drUpdateAssignmentGrade(token!!,model).enqueue(object :Callback<ResponseBody>{
                override fun onResponse(p0: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful){
                        Toast.makeText(requireContext(), "Submit Successful", Toast.LENGTH_SHORT).show()
                        dismiss()
                    }
                    else
                    Toast.makeText(requireContext(), "Submit Failed", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(p0: Call<ResponseBody>, p1: Throwable) {
                    Toast.makeText(requireContext(), "${p1.localizedMessage}", Toast.LENGTH_SHORT).show()

                }
            })
        }
}