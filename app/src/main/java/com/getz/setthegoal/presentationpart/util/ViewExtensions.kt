package com.getz.setthegoal.presentationpart.util

import android.content.Context
import android.graphics.Rect
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
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

fun View.addKeyboardListener(result: (isOpened: Boolean) -> Unit): () -> Unit {
    val globalSubscriber = {
        val rect = Rect()
        this.getWindowVisibleDisplayFrame(rect)
        val keyboardHeight = rootView.height - rect.bottom
        /**
         * (rootView.height * fifteenPercent) equals around 300-400px on different devices.
         * */
        val fifteenPercent = 0.15f
        val opened = keyboardHeight > rootView.height * fifteenPercent
        result.invoke(opened)
    }
    this.viewTreeObserver.addOnGlobalLayoutListener(globalSubscriber)
    return globalSubscriber
}

fun View.removeKeyboardListener(globalSubscriber: () -> Unit) {
    this.viewTreeObserver.removeOnGlobalLayoutListener(globalSubscriber)
}

fun hideKeyboard(view: TextView) {
    val inputMethodManager = inputMethodManager(view.context)
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.SHOW_FORCED)
}

private fun inputMethodManager(context: Context): InputMethodManager =
    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

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