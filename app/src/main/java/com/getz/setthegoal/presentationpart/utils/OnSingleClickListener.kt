package com.getz.setthegoal.presentationpart.utils

import android.os.SystemClock
import android.view.View

class OnSingleClickListener(
    private var interval: Int = 600,
    private val onSafeClick: (View) -> Unit
) : View.OnClickListener {

    private var lastTimeClicked: Long = 0

    override fun onClick(v: View) {
        val elapsedRealtime = SystemClock.elapsedRealtime()
        if (elapsedRealtime - lastTimeClicked < interval) return
        lastTimeClicked = elapsedRealtime
        onSafeClick(v)
    }
}