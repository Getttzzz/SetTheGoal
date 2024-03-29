package com.getz.setthegoal.presentationpart.util

import android.os.SystemClock
import android.view.View

class OnSingleClickListener(
    var interval: Int = 600,
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