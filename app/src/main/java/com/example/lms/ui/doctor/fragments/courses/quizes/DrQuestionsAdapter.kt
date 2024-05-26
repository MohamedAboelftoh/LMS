package com.example.lms.ui.doctor.fragments.courses.quizes

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.lms.databinding.ItemDrQuestionBinding
import com.example.lms.ui.api.api_doctor.dr_courses.quizzes.DrAnswersItem
import com.example.lms.ui.api.api_doctor.dr_courses.quizzes.DrQuestionsItem

class DrQuestionsAdapter(private var questionsList: MutableList<DrQuestionsItem>? = null) :
    RecyclerView.Adapter<DrQuestionsAdapter.DrQuestionsViewHolder>() {

    class DrQuestionsViewHolder(val itemBinding: ItemDrQuestionBinding) : RecyclerView.ViewHolder(itemBinding.root)

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
        val question = questionsList?.get(position)
        val answers = mutableListOf(DrAnswersItem(), DrAnswersItem())

        for (i in answers.indices) {
            val answer = answers[i]
            setAnswerListeners(holder, answer, i)
            answer.answerNumber = i + 1
        }

        holder.itemBinding.btnAddAnswer.setOnClickListener {
            when {
                holder.itemBinding.linear3.visibility == View.GONE -> {
                    holder.itemBinding.linear3.visibility = View.VISIBLE
                    addAnswer(holder, answers, 2)
                }
                holder.itemBinding.linear4.visibility == View.GONE -> {
                    holder.itemBinding.linear4.visibility = View.VISIBLE
                    addAnswer(holder, answers, 3)
                }
                else -> {
                    Toast.makeText(holder.itemView.context, "The largest number is 4", Toast.LENGTH_SHORT).show()
                }
            }
        }

        holder.itemBinding.btnRemoveAns.setOnClickListener {
            when {
                holder.itemBinding.linear4.visibility == View.VISIBLE -> {
                    holder.itemBinding.linear4.visibility = View.GONE
                    removeAnswer(holder, answers, 3)
                }
                holder.itemBinding.linear3.visibility == View.VISIBLE -> {
                    holder.itemBinding.linear3.visibility = View.GONE
                    removeAnswer(holder, answers, 2)
                }
                else -> {
                    Toast.makeText(holder.itemView.context, "The least number is 2", Toast.LENGTH_SHORT).show()
                }
            }
        }

        question?.answers = answers
        question?.text = holder.itemBinding.questionEt.text.toString()
        question?.questionNumber = position + 1
        question?.type = "choice"
        question?.grade = 1

        holder.itemBinding.questionEt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                question?.text = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun setAnswerListeners(holder: DrQuestionsViewHolder, answer: DrAnswersItem, index: Int) {
        answer.answerNumber = index+1
        val editText = when (index) {
            0 ->{
                holder.itemBinding.answerEt1
            }
            1 ->{
                holder.itemBinding.answerEt2
            }
            2 ->{
                holder.itemBinding.answerEt3
            }
            3 -> {
                holder.itemBinding.answerEt4
            }
            else -> throw IllegalArgumentException("Invalid index")
        }

        val radioButton = when (index) {
            0 -> holder.itemBinding.radio1
            1 -> holder.itemBinding.radio2
            2 -> holder.itemBinding.radio3
            3 -> holder.itemBinding.radio4
            else -> throw IllegalArgumentException("Invalid index")
        }

        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                answer.text = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        radioButton.setOnCheckedChangeListener { _, isChecked ->
            answer.isCorrect = isChecked
        }
    }

    private fun addAnswer(holder: DrQuestionsViewHolder, answers: MutableList<DrAnswersItem>, index: Int) {
        val newAnswer = DrAnswersItem()
        answers.add(newAnswer)
        setAnswerListeners(holder, newAnswer, index)
    }

    private fun removeAnswer(holder: DrQuestionsViewHolder, answers: MutableList<DrAnswersItem>, index: Int) {
        answers.removeAt(index)
        clearAnswer(holder, index)
    }

    private fun clearAnswer(holder: DrQuestionsViewHolder, index: Int) {
        when (index) {
            2 -> {
                holder.itemBinding.answerEt3.setText("")
                holder.itemBinding.radio3.isChecked = false
            }
            3 -> {
                holder.itemBinding.answerEt4.setText("")
                holder.itemBinding.radio4.isChecked = false
            }
        }
    }

    fun addItem(item: DrQuestionsItem) {
        if (questionsList == null) {
            questionsList = mutableListOf()
        }
        questionsList?.add(item)
        notifyItemInserted(questionsList!!.size - 1)
    }

    fun getQuestions(): MutableList<DrQuestionsItem> {
        return this.questionsList!!
    }
}
