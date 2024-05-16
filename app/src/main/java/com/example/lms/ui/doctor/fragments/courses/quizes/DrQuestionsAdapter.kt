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

class DrQuestionsAdapter(var questionsList: ArrayList<DrQuestionsItem>? = null) :
    RecyclerView.Adapter<DrQuestionsAdapter.DrQuestionsViewHolder>() {

    // Initialize the questionsList with three items of initial data
    init {
        questionsList = arrayListOf(
            DrQuestionsItem(),
            DrQuestionsItem()
        )

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

    override fun onBindViewHolder(holder: DrQuestionsViewHolder, position: Int) {
        // Bind data to views here if needed
        val question= questionsList?.get(position)
        holder.itemBinding.btnAddAnswer.setOnClickListener{
            onButtonAddAnsClickListener?.onClick(question!!,position,holder)
        }
        holder.itemBinding.btnRemoveAns.setOnClickListener{
            onButtonRemoveAnsClickListener?.onClick(question!!,position,holder  )

        }
        holder.itemBinding.tvQuestionNumber.text = "${questionsList?.indexOf(question) ?: 0}"
    }
    fun bindData(listOfQuestions: ArrayList<DrQuestionsItem>) {
        this.questionsList = listOfQuestions
        notifyItemInserted(this.questionsList!!.size - 1)
    }
    var onButtonAddAnsClickListener:OnButtonAddAnsClickListener?=null
     var onButtonRemoveAnsClickListener:OnButtonRemoveAnsClickListener?=null

    interface OnButtonAddAnsClickListener{
        fun onClick(item:DrQuestionsItem,position: Int,holder: DrQuestionsViewHolder){}
    }
    interface OnButtonRemoveAnsClickListener{
        fun onClick(item:DrQuestionsItem,position: Int,holder: DrQuestionsViewHolder){}
    }

//    fun addItem(){
//        if (questionsList == null) {
//            questionsList = mutableListOf()
//        }
////        questionsList?.add(DrQuestionsItem())
////        notifyDataSetChanged()
//
//        val newItem = DrQuestionsItem()
//        questionsList?.add(newItem)
//        notifyItemInserted(questionsList?.size ?: 0)
//    }

    override fun getItemCount(): Int {
        return questionsList?.size ?: 0
    }

    // Rest of the code...

    fun addItem() {
        if (questionsList == null) {
            questionsList = arrayListOf()
        }
        val newItem = DrQuestionsItem()
        questionsList?.add(newItem)
        notifyDataSetChanged()
    }

//    fun updateList(newList: ArrayList<DrQuestionsItem>) {
//        questionsList = newList
//        notifyDataSetChanged()
//    }
    
fun updateList(newList: ArrayList<DrQuestionsItem>) {
    val previousSize = questionsList?.size ?: 0
    questionsList = newList
    val currentSize = questionsList?.size ?: 0
    if (currentSize > previousSize) {
        notifyItemRangeInserted(previousSize, currentSize - previousSize)
    } else {
        notifyDataSetChanged()
    }
}
}
