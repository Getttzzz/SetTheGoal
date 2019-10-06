package com.getz.setthegoal.presentationpart.util

import android.content.Context
import com.getz.setthegoal.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun showDeleteDialog(context: Context, positiveAction: () -> Unit) {
    MaterialAlertDialogBuilder(context, R.style.AlertDialogThemeCut)
        .setTitle(context.getString(R.string.do_you_want_to_delete))
        .setPositiveButton(context.getString(R.string.ok)) { dialog, _ ->
            positiveAction()
            dialog.cancel()
        }
        .setNegativeButton(context.getString(R.string.cancel)) { dialog, _ -> dialog.cancel() }
        .show()
}