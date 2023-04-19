package com.roomdb.testproject.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import java.io.File

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

    inline fun sdkIntAboveOreo(call: () -> Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            call.invoke()
        }
    }
}

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

fun Context.hasLocationPermission(): Boolean {
    return ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
}