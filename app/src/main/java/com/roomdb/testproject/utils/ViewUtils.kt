package com.roomdb.testproject.utils

import android.content.Context
import android.os.Environment
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.ColorInt
import com.google.android.material.snackbar.Snackbar
import java.io.File

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
object ViewUtils {

    fun createFolder( context: Context, folderName: String) {
        val name = folderName.trim()
        val file = File("${Environment.getExternalStorageDirectory()}/$name")
        val folderCreate = file.mkdirs()

        if (folderCreate) {
            context.toast("Folder Created at ${file.absoluteFile}")
        } else {
            context.toast("Folder creation failed")
        }

        if (!file.exists()) {

        }
    }
}