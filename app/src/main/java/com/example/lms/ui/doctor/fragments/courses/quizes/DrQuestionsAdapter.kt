package com.example.lms.ui.doctor.fragments.courses.quizes

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.lms.databinding.ItemDrQuestionBinding
import com.example.lms.ui.api.api_doctor.dr_courses.quizzes.DrQuestionsItem

class DrQuestionsAdapter(private var questionsList: MutableList<DrQuestionsItem>? = null) :
    RecyclerView.Adapter<DrQuestionsAdapter.DrQuestionsViewHolder>() {
        init {
            questionsList = mutableListOf(DrQuestionsItem())
        }
    class DrQuestionsViewHolder(val itemBinding: ItemDrQuestionBinding) :
        ViewHolder(itemBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrQuestionsViewHolder {
        val viewBinding = ItemDrQuestionBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DrQuestionsViewHolder(viewBinding)
    }

    override fun getItemCount(): Int {
        return questionsList?.size ?: 0
    }

    override fun onBindViewHolder(holder: DrQuestionsViewHolder, position: Int) {
        // Bind data to views here if needed
        val question= questionsList?.get(position)
        holder.itemBinding.btnAddAnswer.setOnClickListener{
            onButtonAddAnsClickListener?.onClick(question!!,position,holder)
        }
        holder.itemBinding.btnRemoveAns.setOnClickListener{
            onButtonRemoveAnsClickListener?.onClick(question!!,position,holder)

        }
    }
    var onButtonAddAnsClickListener:OnButtonAddAnsClickListener?=null
     var onButtonRemoveAnsClickListener:OnButtonRemoveAnsClickListener?=null

    interface OnButtonAddAnsClickListener{
        fun onClick(item:DrQuestionsItem,position: Int,holder: DrQuestionsViewHolder){}
    }
    interface OnButtonRemoveAnsClickListener{
        fun onClick(item:DrQuestionsItem,position: Int,holder: DrQuestionsViewHolder){}
    }
    fun addItem(item: DrQuestionsItem) {
        if (questionsList == null) {
            questionsList = mutableListOf()
        }
        questionsList?.add(item)
        notifyItemInserted(questionsList!!.size - 1)
    }
}
