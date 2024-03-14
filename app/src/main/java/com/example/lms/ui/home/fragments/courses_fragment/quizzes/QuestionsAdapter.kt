package com.example.lms.ui.home.fragments.courses_fragment.quizzes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lms.databinding.QuestionItemBinding
import com.example.lms.ui.api.quizes.QuestionsItem

class QuestionsAdapter (private var questionsList:List<QuestionsItem?>?=null):RecyclerView.Adapter<QuestionsAdapter.QuestionsViewHolder>(){
    class QuestionsViewHolder(val itemBinding: QuestionItemBinding):RecyclerView.ViewHolder(itemBinding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionsViewHolder {
          val itemBinding = QuestionItemBinding.inflate(
              LayoutInflater.from(parent.context), parent, false
          )
         return QuestionsViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: QuestionsViewHolder, position: Int) {
        val item=questionsList!![position]

      //  val answers=item?.answers
        holder.itemBinding.question.text=item?.text
        holder.itemBinding.tvQuestionNumber.text=item?.questionNumber.toString()
        holder.itemBinding.nextBtn.setOnClickListener { onNextClickListener?.onItemClick(position,item) }

        holder.itemBinding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            // Notify the activity about the selected radio button
          onRadioButtonSelect?.onRadioButtonClicked(position, item,checkedId)
        }
        if (position == questionsList!!.size-1){
            holder.itemBinding.nextBtn.text = "Submit"
        }

        handelNumberOfAnswers(holder,position)
    //        holder.itemBinding.radio1.text=answers?.get(0)?.text
//        holder.itemBinding.radio2.text=answers?.get(1)?.text
//        holder.itemBinding.radio3.text=answers?.get(2)?.text
//        holder.itemBinding.radio4.text=answers?.get(3)?.text
//        holder.itemBinding.tvQuestionNumber.text="${position+1}"

    }

    private fun handelNumberOfAnswers(holder: QuestionsAdapter.QuestionsViewHolder, position: Int) {
        val item=questionsList!![position]

        val answers=item?.answers
        holder.itemBinding.radio1.text = answers?.getOrNull(0)?.text
        holder.itemBinding.radio2.text = answers?.getOrNull(1)?.text
        holder.itemBinding.radio3.text = answers?.getOrNull(2)?.text
        holder.itemBinding.radio4.text = answers?.getOrNull(3)?.text
        holder.itemBinding.tvQuestionNumber.text = "${position + 1}"

// Set visibility for RadioButtons based on answer availability
        holder.itemBinding.radio1.visibility = if (answers?.getOrNull(0)?.text != null) View.VISIBLE else View.GONE
        holder.itemBinding.radio2.visibility = if (answers?.getOrNull(1)?.text != null) View.VISIBLE else View.GONE
        holder.itemBinding.radio3.visibility = if (answers?.getOrNull(2)?.text != null) View.VISIBLE else View.GONE
        holder.itemBinding.radio4.visibility = if (answers?.getOrNull(3)?.text != null) View.VISIBLE else View.GONE

    }


    var itemCountValue: Int = 0
    override fun getItemCount(): Int {
        itemCountValue = questionsList?.size ?: 0

        return itemCountValue
    }
    fun incrementItemCount() {
        itemCountValue++
        notifyItemInserted(itemCountValue - 1)
    }


    fun bindQuestions(questions: ArrayList<QuestionsItem?>?) {
        questionsList=questions
        notifyDataSetChanged()
    }

    var onNextClickListener:OnNextClickListener?=null

    fun interface OnNextClickListener{
        fun onItemClick(position:Int,item: QuestionsItem?)
    }
    var onRadioButtonSelect: OnRadioButtonSelect? =null
    fun interface  OnRadioButtonSelect{
        fun onRadioButtonClicked(position: Int, item:QuestionsItem?, checkedRadioButtonId: Int)
    }

}