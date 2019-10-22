package com.getz.setthegoal.presentationpart.util

import android.content.Context
import androidx.annotation.StringRes
import com.getz.setthegoal.R
import com.getz.setthegoal.presentationpart.feature.welcome.OkayEnum
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun showOkOrCancelDialog(
    @StringRes title: Int,
    @StringRes description: Int? = null,
    context: Context,
    positiveAction: () -> Unit
) {
    MaterialAlertDialogBuilder(context, R.style.AlertDialogThemeCut)
        .setTitle(context.getString(title))
        .also { if (description != null) it.setMessage(description) }
        .setPositiveButton(OkayEnum.getRandomAgreement().strRes) { dialog, _ ->
            positiveAction()
            dialog.cancel()
        }
        .setNegativeButton(context.getString(R.string.cancel)) { dialog, _ -> dialog.cancel() }
        .show()
}