package com.getz.setthegoal.presentationpart.util

import android.content.Context
import android.content.Intent
import android.net.Uri

fun openLink(url: String, context: Context) {
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        context.startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}