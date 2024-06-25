package com.example.lms.ui.views_adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.lms.R
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("app:url")
fun bindImageWithUrl(image : ImageView , url : String?){
    Glide.with(image)
        .load(url)
        .placeholder(R.drawable.avatar_1)
        .into(image)
}
@BindingAdapter("app:Error")
fun bindErrorInTextInputLayout(textInputLayout: TextInputLayout , errorMessage : String?){
    textInputLayout.error = errorMessage
}