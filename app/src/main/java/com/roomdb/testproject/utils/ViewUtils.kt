package com.roomdb.testproject.utils

import android.content.Context
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.ColorInt
import com.google.android.material.snackbar.Snackbar

fun Context.toast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun View.snackBar(message: String, @ColorInt colorInt: Int? = null, action: (() -> Unit)? = null) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).run {
        colorInt?.let {
            setBackgroundTint(it)
        }
        action?.let {
            setAction("Retry") {
                it
            }
        }
        show()
    }
}