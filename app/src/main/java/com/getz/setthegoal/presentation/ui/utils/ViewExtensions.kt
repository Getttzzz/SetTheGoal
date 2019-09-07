package com.getz.setthegoal.presentation.ui.utils

import android.view.View

fun View.setSingleClickListener(onClick: (View) -> Unit) {
    val singleClicker = OnSingleClickListener { onClick(it) }
    setOnClickListener(singleClicker)
}