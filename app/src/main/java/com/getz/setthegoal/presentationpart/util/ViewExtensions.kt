package com.getz.setthegoal.presentationpart.util

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.core.view.size
import androidx.viewpager.widget.ViewPager

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

fun ViewPager.addOnPageSelectedListener(onPageSelected: (position: Int) -> Unit) {
    this.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
        override fun onPageScrollStateChanged(state: Int) = Unit
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) = Unit

        override fun onPageSelected(position: Int) {
            onPageSelected.invoke(position)
        }
    })
}

fun ViewPager.swipeRight(position: Int) {
    if (position < this.size) {
        this.setCurrentItem(position + 1, true)
    }
}

fun ViewPager.swipeLeft(position: Int) {
    if (position > 0) {
        this.setCurrentItem(position - 1, true)
    }
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}