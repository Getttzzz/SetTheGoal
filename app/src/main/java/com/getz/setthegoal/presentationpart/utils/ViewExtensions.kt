package com.getz.setthegoal.presentationpart.utils

import android.view.View

fun View.setSingleClickListener(onClick: (View) -> Unit) {
    val singleClicker = OnSingleClickListener { onClick(it) }
    setOnClickListener(singleClicker)
}