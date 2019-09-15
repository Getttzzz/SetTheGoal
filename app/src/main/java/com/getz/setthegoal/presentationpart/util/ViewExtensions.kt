package com.getz.setthegoal.presentationpart.util

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText

fun View.setSingleClickListener(onClick: (View) -> Unit) {
    val singleClicker = OnSingleClickListener { onClick(it) }
    setOnClickListener(singleClicker)
}

fun EditText.addOnTextChangedListener(onTextChange: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) = Unit

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChange.invoke(text.toString())
        }
    })
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}